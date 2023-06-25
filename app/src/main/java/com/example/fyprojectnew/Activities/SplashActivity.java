package com.example.fyprojectnew.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.fyprojectnew.R;
import com.google.firebase.auth.FirebaseAuth;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAuth =FirebaseAuth.getInstance();
    }

   /* Authentication Using Thread :- */
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

                            if(mAuth.getCurrentUser()!=null){
                                Intent backpage = new Intent(SplashActivity.this, VeiwPageActivity.class);
                                startActivity(backpage);
                            }else{
                                Intent nextpage = new Intent(SplashActivity.this, LoginActivity.class);
                                startActivity(nextpage);
                            }
                            Log.d("peak","Free");
                        }
                    }
                };td.start();
        }
    }