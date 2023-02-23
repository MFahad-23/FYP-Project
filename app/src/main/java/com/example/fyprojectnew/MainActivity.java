package com.example.fyprojectnew;


import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.drjacky.imagepicker.ImagePicker;
import com.github.drjacky.imagepicker.constant.ImageProvider;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    SmoothBottomBar bottombar;
    NavigationView navigation_view;
    DrawerLayout drawerlayout;
    Timer timer;
    HomeFragment homeFragment = new HomeFragment();
    SettingFragment settingFragment = new SettingFragment();

    @SuppressLint({"ResourceType", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottombar = (SmoothBottomBar) findViewById(R.id.bottombar);
        navigation_view = (NavigationView) findViewById(R.id.navigation_view);
        drawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        navigation_view.bringToFront();
        navigation_view.setCheckedItem(R.id.home);


        /*Fragment Moving :-*/
        getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, homeFragment).commit();

     /*   bottombar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, homeFragment).commit();
                        break;
                    case R.id.setting:
                        getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, settingFragment).commit();
                return (true);
            }
                return (true);
            }
        });*/

    /*    Navigation Menue Click Actions :-*/

        navigation_view.setNavigationItemSelectedListener(this);
    }
    public void openDrawer() {
        drawerlayout.open();
    }
    public void onBackPressed(){
        if (drawerlayout.isDrawerOpen(GravityCompat.START)){
            drawerlayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.home:
                break;
            case R.id.profile:
                timer=new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent profilepage=new Intent(MainActivity.this,ProfilePage.class);
                        startActivity(profilepage);
                    }
                },1000);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                timer=new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent createnewpage=new Intent(MainActivity.this,SplashActivity.class);
                        startActivity(createnewpage);
                    }
                },100);
                break;
            case R.id.newaccount:
                timer=new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent loginepage=new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(loginepage);
                    }
                },1000);
                break;
        }
        drawerlayout.closeDrawer(GravityCompat.START);
        return true;
    }
}