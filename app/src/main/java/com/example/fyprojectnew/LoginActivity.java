package com.example.fyprojectnew;

import static com.example.fyprojectnew.R.color.black;
import static com.example.fyprojectnew.R.drawable.custom_dialog_background;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.BreakIterator;
import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText gmail, password;
    TextView create_account, textveiw;
    String TAG;
    Timer timer;
    ImageView dialog_dissmiss,dissmiss;
    Dialog errordialog,helpdialog;
    LinearLayout helpcard;
    private FirebaseAuth mauth;
    Animation slide_left;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        gmail = (TextInputEditText) findViewById(R.id.gmail);
        password = (TextInputEditText) findViewById(R.id.password);
        Button login = (Button) findViewById(R.id.login);
        create_account = (TextView) findViewById(R.id.create_account);
        textveiw = (TextView) findViewById(R.id.textveiw);
        mauth = FirebaseAuth.getInstance();
        helpcard =(LinearLayout)findViewById(R.id.helpcard);

        slide_left=AnimationUtils.loadAnimation(LoginActivity.this,R.anim.slide_left);

        /* Create New Account :-*/
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(next);
                create_account.startAnimation(slide_left);
            }
        });

        /* Foreget Password :-*/
        textveiw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Data Saved Successfuly", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        // Login Activity Button:-
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*  checkcredencials();*/
                if (gmail.getText().toString().isEmpty()) {
                    gmail.setError("*Please Complete the Fields*");
                } else if (password.getText().toString().isEmpty()) {
                    password.setError("*Please Complete the Field*");
                } else {
                    /*Firebase Authentication :-*/
                    mauth.signInWithEmailAndPassword(gmail.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mauth.getCurrentUser();
                                        Toast.makeText(getApplicationContext(), "Thanks for Login !", Toast.LENGTH_SHORT).show();
                                        timer = new Timer();
                                        timer.schedule(new TimerTask() {
                                            @Override
                                            public void run() {
                                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                            }
                                        }, 2000);
                                        gmail.setText("");
                                        password.setText("");
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        timer = new Timer();
                                        timer.schedule(new TimerTask() {
                                            @Override
                                            public void run() {
                                                startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                                            }
                                        }, 2000);
                                        /*Error Dialog :-*/
                                        Openerrordialog();
                                    }
                                }
                            });
                }
            }
        });
        /*  Help Dialog :-*/
        helpcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Openhelpdialog();
            }
        });
    }

    /*Error Dialog Method :-*/
    private void Openerrordialog() {
        errordialog = new Dialog(LoginActivity.this);
        errordialog.setContentView(R.layout.custom_error_dialog);
        errordialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        dissmiss =errordialog.findViewById(R.id.dissmiss);

        dissmiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errordialog.dismiss();
            }
        });
        errordialog.show();
    }

    /*Help Dialog Method :-*/
    private void Openhelpdialog() {
        helpdialog = new Dialog(LoginActivity.this);
        helpdialog.setContentView(R.layout.help_dialog_box);
        helpdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        dialog_dissmiss =helpdialog.findViewById(R.id.dialog_dissmiss);

        dialog_dissmiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpdialog.dismiss();
            }
        });
        helpdialog.show();
    }
}