package com.example.fyprojectnew;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
private FirebaseAuth mauth;
private FirebaseDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mauth =FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance();
    }

   /* Splash Activity Running :-*/
    @Override
        protected void onResume(){
                super.onResume();
                Thread td=new Thread(){
                    public void run() {
                        try {
                            sleep(3000);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        } finally {
                            if(mauth.getCurrentUser()!=null){
                                Intent backpage = new Intent(SplashActivity.this,MainActivity.class);
                                startActivity(backpage);
                            }else{
                                Intent nextpage = new Intent(SplashActivity.this,LoginActivity.class);
                                startActivity(nextpage);
                            }
                            Log.d("peak","Free");
                        }
                    }
                };td.start();
        }
    }