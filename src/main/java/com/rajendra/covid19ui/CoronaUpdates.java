package com.rajendra.covid19ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.google.android.material.navigation.NavigationView;

import java.util.concurrent.TimeUnit;

public class CoronaUpdates extends AppCompatActivity {

    private DrawerLayout drawer;
    ImageView menu;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawer = findViewById(R.id.drawer_layout);
        menu = findViewById(R.id.imageView);
        navigationView = findViewById(R.id.nav_view);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new CoronaFragment()).commit();
            Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_LONG).show();

        }


        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(MyWorker.class, 20, TimeUnit.MINUTES).build();

        WorkManager.getInstance(this).enqueue(request);


        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new CoronaFragment()).commit();
                        Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.newhome_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new Details()).commit();
                        Toast.makeText(getApplicationContext(), "StateWise Update \n Work Partially Completed", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.newhome_custom:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new SelfAssessment()).commit();
                        Toast.makeText(getApplicationContext(), "Self Assessment \n Work In Progress", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.newhome_helpline:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new Helpline()).commit();
                        Toast.makeText(getApplicationContext(), "State Helpline", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.newhome_Media:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new MediaCorona()).commit();
                        Toast.makeText(getApplicationContext(), "Media", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.newhome_Symptoms:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new Symptoms()).commit();
                        Toast.makeText(getApplicationContext(), "Symptoms \n Work In Progress", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_about_us:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new AboutUs()).commit();
                        Toast.makeText(getApplicationContext(), "About Us", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.nav_Support:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new Support()).commit();
                        Toast.makeText(getApplicationContext(), "Support \n  Work In Progress", Toast.LENGTH_LONG).show();
                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            super.onBackPressed();

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}



