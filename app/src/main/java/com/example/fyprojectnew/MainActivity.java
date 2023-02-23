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

import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    SmoothBottomBar bottombar;
    NavigationView navigation_view;
    DrawerLayout drawerlayout;
    Timer timer;
    User user;
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
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


        TextView user_name_header= navigation_view.getHeaderView(0).findViewById(R.id.username);
        TextView user_mail_header= navigation_view.getHeaderView(0).findViewById(R.id.usermail);

        /*Data push to save into the Database :-*/
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                user_name_header.setText(user.username);
                user_mail_header.setText(user.gmail);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(postListener);

        /*Fragment Moving :-*/
        getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, homeFragment).commit();

        bottombar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                if(i==0)
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, homeFragment).commit();

                }else if (i==1){
                    getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, settingFragment).commit();
                }
                return false;
            }
        });

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