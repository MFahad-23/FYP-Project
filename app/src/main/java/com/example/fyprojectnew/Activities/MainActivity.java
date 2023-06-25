package com.example.fyprojectnew.Activities;

import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.fyprojectnew.Fragments.HomeFragment;
import com.example.fyprojectnew.Fragments.SettingFragment;
import com.example.fyprojectnew.R;
import com.example.fyprojectnew.Models.User;
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
    SmoothBottomBar BottomBar;
    NavigationView side_navigation_view;
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
        BottomBar = (SmoothBottomBar) findViewById(R.id.BottomBar);
        drawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);

        side_navigation_view = (NavigationView) findViewById(R.id.side_navigation_view);
        side_navigation_view.bringToFront();
        side_navigation_view.setCheckedItem(R.id.home);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        /*For Side Navigation Menus Action Perform:-*/
        TextView user_name_header= side_navigation_view.getHeaderView(0).findViewById(R.id.username);
        TextView user_mail_header= side_navigation_view.getHeaderView(0).findViewById(R.id.usermail);
        ImageView user_image_header=side_navigation_view.getHeaderView(0).findViewById(R.id.userimage);

        /*Push Data into FireBase DataBase :-*/
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                user_name_header.setText(user.username);
                user_mail_header.setText(user.gmail);
                Glide.with(MainActivity.this).load(user.profile_pic).into(user_image_header);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // If Failed Check Error in Logcat
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(postListener);

        /* Fragments Replacement :- */
        getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, homeFragment).commit();
        BottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
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

        /* Navigation Menu Click Actions :- */
        side_navigation_view.setNavigationItemSelectedListener(this);
    }

     /* For DrawerLayout Open :- */
    public void openDrawer() {
        drawerlayout.open();
    }

     /* Back Press For DrawerLayout :- */
    public void onBackPressed(){
        if (drawerlayout.isDrawerOpen(GravityCompat.START)){
            drawerlayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    /* Action Performs in Side NavigationView Menus :- */
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
                        Intent ProfilePage=new Intent(MainActivity.this, com.example.fyprojectnew.Activities.ProfilePage.class);
                        startActivity(ProfilePage);
                    }
                },1000);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                timer=new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent LogIn=new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(LogIn);
                    }
                },100);
                break;
            case R.id.newaccount:
                timer=new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent CreateNewAccount=new Intent(MainActivity.this, SignUpActivity.class);
                        startActivity(CreateNewAccount);
                    }
                },1000);
                break;
            case R.id.setting:
                timer=new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent PasswordReset=new Intent(MainActivity.this, CreateNewPassword.class);
                        startActivity(PasswordReset);
                    }
                },100);
        }
        drawerlayout.closeDrawer(GravityCompat.START);
        return true;
    }
}