package com.example.je_attendancesystem.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;


import com.example.je_attendancesystem.R;
import com.example.je_attendancesystem.fragments.FragmentTimesheet;
import com.example.je_attendancesystem.fragments.FragmentProject;
import com.google.android.material.navigation.NavigationView;

public class MainMenu extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navDrawer;
    private FragmentProject fragmentProject;
    private FragmentTimesheet fragmentCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //Button declaration and listeners
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Projects");


        //Fragments
        fragmentProject = new FragmentProject();
        fragmentCalendar = new FragmentTimesheet();

        //set project first fragment
        replaceFragment(fragmentCalendar);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        navDrawer = findViewById(R.id.nav_drawer);

        setSupportActionBar(toolbar);

        //Get Drawer content
        //View headerView = navDrawer.getHeaderView(0);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_menu);
        }

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        navDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });

    }

    // override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

}