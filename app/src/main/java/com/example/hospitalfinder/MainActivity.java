package com.example.hospitalfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.andremion.floatingnavigationview.FloatingNavigationView;
import com.example.hospitalfinder.pojos.userInformationPojo;
import com.example.hospitalfinder.utils.NotificationReceiver;
import com.example.hospitalfinder.viewmodel.RegistrationViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends AppCompatActivity {
    private FloatingNavigationView mFloatingNavigationView;
    private NavController navController;
    private NavigationTabBar navigationTabBar;
    private ArrayList<NavigationTabBar.Model> models;
    RegistrationViewModel registrationViewModel;
    private boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        registrationViewModel = ViewModelProviders.of(MainActivity.this).get(RegistrationViewModel.class);
        mFloatingNavigationView = (FloatingNavigationView) findViewById(R.id.floating_navigation_view);
        navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb);
        mFloatingNavigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFloatingNavigationView.open();
                mFloatingNavigationView.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#355c7d")));

                View headerView = mFloatingNavigationView.getHeaderView(0);
                TextView navUsername = headerView.findViewById(R.id.nav_userName);
                TextView navEmail = headerView.findViewById(R.id.nav_email);
                registrationViewModel.getUserInfo().observe(MainActivity.this, new Observer<userInformationPojo>() {
                    @Override
                    public void onChanged(userInformationPojo userInformationPojo) {
                        navUsername.setText(userInformationPojo.getUserName());
                        navEmail.setText(userInformationPojo.getUserEmail());
                    }
                });

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
                        navigationTabBar.setVisibility(View.GONE);
                        mFloatingNavigationView.hide();
                        break;

                    case R.id.registrationFragment:
                        //  isExit = true;
                        toolbar.setVisibility(View.GONE);
                        mFloatingNavigationView.hide();
                        navigationTabBar.setVisibility(View.GONE);
                        break;
                    case R.id.mainDashBoard:
                       isExit = true;
                        navigationTabBar.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.VISIBLE);
                        mFloatingNavigationView.show();
                        break;
                    case R.id.medicineReminderFragment:
                        //  isExit = true;
                        navigationTabBar.setVisibility(View.VISIBLE);
                        toolbar.setVisibility(View.VISIBLE);
                        mFloatingNavigationView.show();
                        break;

                    case R.id.ambulanceFragmnet:
                        //  isExit = true;
                        toolbar.setVisibility(View.VISIBLE);
                        mFloatingNavigationView.show();
                        break;
                    case R.id.NearByLocationFragment:
                          isExit = false;
                        toolbar.setVisibility(View.VISIBLE);
                        mFloatingNavigationView.show();
                        break;

                    case R.id.blogpost:
                          isExit = true;
                        toolbar.setVisibility(View.VISIBLE);
                        mFloatingNavigationView.show();
                        break;


                    default:
                        toolbar.setVisibility(View.GONE);
                        mFloatingNavigationView.hide();
                        isExit = false;
                        break;
                }
            }
        });


        String colors[] = getResources().getStringArray(R.array.colors);


        models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_home_black_24dp),
                        Color.parseColor(colors[0])
                ).title("Home")
                        .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_notifications_active_black_24dp),
                        Color.parseColor(colors[1])
                ).title("Medicine Reminder")
                        .badgeTitle("with")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_web_black_24dp),
                        Color.parseColor(colors[2])
                ).title("Health Calculation")
                        .badgeTitle("state")
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
                        Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.calcultionDashBoard);
                        break;

                    case 2:
                        Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.blogpost);
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


        mFloatingNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.homeID:
                        navigationTabBar.setViewPager(null, 0);
                        navigationTabBar.setModels(models);
                        navigationTabBar.setIsTinted(true);
                        navigationTabBar.setIsSwiped(true);
                        //navigationTabBar.setViewPager(viewPager, 1);
                        navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ACTIVE);

                        Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.mainDashBoard);
                        break;
                    case R.id.logoutID:
                        registrationViewModel.getLogoutUser();
                        Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment).navigate(R.id.loginFramgemt);
                        break;
                }

                //Snackbar.make((View) mFloatingNavigationView.getParent(), item.getTitle() + " Selected!", Snackbar.LENGTH_SHORT).show();
                mFloatingNavigationView.close();
                return true;
            }
        });

    }


    @Override
    public void onBackPressed() {
        if (mFloatingNavigationView.isOpened()) {
            mFloatingNavigationView.close();
        }

        else if (isExit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Exit App");
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }

        else {
            super.onBackPressed();
        }
    }
    }






