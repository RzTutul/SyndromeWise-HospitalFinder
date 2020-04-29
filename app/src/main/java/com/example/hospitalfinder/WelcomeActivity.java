package com.example.hospitalfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hospitalfinder.adapter.MyViewPagerAdapter;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layout;
    private Button btnskip,btnNext;
    private PrefManager prefManager;
    private MyViewPagerAdapter viewPagerAdapter;
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener()
    {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layout.length - 1) {
                // last page. make button text to GOT IT
                btnNext.setText(getString(R.string.start));
                btnskip.setVisibility(View.GONE);
            } else {
                // still pages are left
                btnNext.setText(getString(R.string.next));
                btnNext.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checking for first time launch - before calling setContentView()
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLunch()) {
            lunchMainActivity();
            finish();
        }

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_welcome);
        layout  = new int[]{
                R.layout.welcome_side1,
                R.layout.welcome_side2,
                R.layout.welcome_side3,
                R.layout.welcome_side4,
        };
        viewPager = findViewById(R.id.view_pager);

        dotsLayout = findViewById(R.id.layoutDots);
        btnskip = findViewById(R.id.btn_skip);
        btnNext = findViewById(R.id.btn_next);

        viewPagerAdapter = new MyViewPagerAdapter(this,layout);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        addBottomDots(0);
        ChangeStatusBarColor();
        btnskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lunchMainActivity();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = getIteam(+1);
                if (current<layout.length)
                {
                    viewPager.setCurrentItem(current);
                }
                else
                {
                    lunchMainActivity();

                }


            }
        });





    }

    /**
     * Making notification bar transparent
     */

    private void ChangeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private int getIteam(int i) {
        return viewPager.getCurrentItem()+1;
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layout.length];
        int[] colorActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i =0;i<dots.length;i++)
        {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);

        }
        if(dots.length>0)
        {
            dots[currentPage].setTextColor(colorActive[currentPage]);
        }
    }

    private void lunchMainActivity() {
        prefManager.setIsFirstTimeLunch(false);
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
    }
}
