package com.example.hospitalfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.andremion.floatingnavigationview.FloatingNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends AppCompatActivity {
    private FloatingNavigationView mFloatingNavigationView;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        mFloatingNavigationView = (FloatingNavigationView) findViewById(R.id.floating_navigation_view);

        mFloatingNavigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFloatingNavigationView.open();
                mFloatingNavigationView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#009688")));
            }
        });
        mFloatingNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Snackbar.make((View) mFloatingNavigationView.getParent(), item.getTitle() + " Selected!", Snackbar.LENGTH_SHORT).show();
                mFloatingNavigationView.close();
                return true;
            }
        });

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch (destination.getId()) {
                    case R.id.loginFramgemt:
                        // isExit = true;
                        toolbar.setVisibility(View.GONE);
                        mFloatingNavigationView.hide();
                        break;

                    case R.id.registrationFragment:
                        //  isExit = true;
                        toolbar.setVisibility(View.GONE);
                        mFloatingNavigationView.hide();
                        break;
                    case R.id.mainDashBoard:
                        //  isExit = true;
                        toolbar.setVisibility(View.VISIBLE);
                        mFloatingNavigationView.show();
                        break;

                         case R.id.ambulanceFragmnet:
                        //  isExit = true;
                        toolbar.setVisibility(View.VISIBLE);
                        mFloatingNavigationView.show();
                        break;



                    default:
                        toolbar.setVisibility(View.GONE);
                        mFloatingNavigationView.hide();

                        //isExit = false;
                        break;
                }
            }
        });


        String colors[] = getResources().getStringArray(R.array.colors);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.logohospital),
                        Color.parseColor(colors[0])
                ).title("Heart")
                        .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.logodoctor),
                        Color.parseColor(colors[1])
                ).title("Cup")
                        .badgeTitle("with")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.blooddonerlogo),
                        Color.parseColor(colors[2])
                ).title("Diploma")
                        .badgeTitle("state")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.logohospital),
                        Color.parseColor(colors[3])
                ).title("Flag")
                        .badgeTitle("icon")
                        .build()
        );

        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int index) {
                switch (index) {
                    case 0:
                        Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.mainDashBoard);
                        break;
                    case 1:
                        Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.loginFramgemt);
                        break;

                    default:
                        break;
                }

            }

            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {

            }
        });

        navigationTabBar.setViewPager(null, 0);

        navigationTabBar.setModels(models);
        navigationTabBar.setIsTinted(true);
        navigationTabBar.setIsSwiped(true);


        //navigationTabBar.setViewPager(viewPager, 1);
        navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ACTIVE);
       /* navigationTabBar.setBadgeGravity(NavigationTabBar.BadgeGravity.BOTTOM);
        navigationTabBar.setBadgePosition(NavigationTabBar.BadgePosition.CENTER);*/
   /*     navigationTabBar.setTypeface("fonts/custom_font.ttf");
        navigationTabBar.setIsBadged(true);
        navigationTabBar.setIsTitled(true);
        navigationTabBar.setIsTinted(true);
        navigationTabBar.setIsBadgeUseTypeface(true);
        navigationTabBar.setBadgeBgColor(Color.RED);
        navigationTabBar.setBadgeTitleColor(Color.WHITE);
        navigationTabBar.setIsSwiped(true);
        navigationTabBar.setBgColor(Color.BLACK);
        navigationTabBar.setBadgeSize(10);
        navigationTabBar.setTitleSize(10);*/
        //  navigationTabBar.setIconSizeFraction(0.5);

    }


    @Override
    public void onBackPressed() {
        if (mFloatingNavigationView.isOpened()) {
            mFloatingNavigationView.close();
        } else {
            super.onBackPressed();
        }
    }
}
