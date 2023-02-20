package com.example.fyprojectnew;

import static com.example.fyprojectnew.R.color.black;
import static com.example.fyprojectnew.R.drawable.*;
import static com.example.fyprojectnew.R.drawable.custom_dialog_background;
import static com.example.fyprojectnew.R.id.dialogbox;

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
    final Context dialog = this;
    TextInputEditText gmail, password;
    TextView tev1, textveiw;
    String TAG;
    Timer timer;
    ImageView cutimage,close;
    Dialog dialogbox, helpdialog;
    LinearLayout help;
    private FirebaseAuth mauth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        gmail = (TextInputEditText) findViewById(R.id.gmail);
        password = (TextInputEditText) findViewById(R.id.password);
        Button login = (Button) findViewById(R.id.login);
        tev1 = (TextView) findViewById(R.id.tev1);
        textveiw = (TextView) findViewById(R.id.textveiw);
        mauth = FirebaseAuth.getInstance();
        help = (LinearLayout) findViewById(R.id.help);

       /* Create New Account :-*/
        tev1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(next);
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
                    if (gmail.getText().toString() != "Someone@gmail.com") {
                        gmail.setError("*Wrong Format*");
                    } else {
                        gmail.getText().toString();
                    }
                } else if (password.getText().toString().isEmpty()) {
                    password.setError("*Please Complete the Field*");
                    if (password.getText().toString().length() < 9) {
                        password.setError("*Please Enter 9 Digits Password*");
                    }
                    else {
                        password.getText().toString();
                    }
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
                                                startActivity(new Intent(LoginActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                            }
                                        }, 3000);
                                        gmail.setText("");
                                        password.setText("");
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        /*Error Dialog :-*/
                                        Opendialogbox();
                                    }
                                }
                            });
                }
            }
        });

      /*  Help Dialog :-*/
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Openhelpdialog();
            }
        });
    }

/*Error Dialog Method :-*/
    private void Opendialogbox() {
        close=(ImageView)dialogbox.findViewById(R.id.close);
        dialogbox = new Dialog(LoginActivity.this);
        dialogbox.setContentView(R.layout.custom_error_dialog);
        dialogbox.getWindow().setBackgroundDrawable(getDrawable(custom_dialog_background));
        dialogbox.show();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogbox.dismiss();
            }
        });
    }

/*Help Dialog Method :-*/
    private void Openhelpdialog() {
        cutimage=(ImageView)helpdialog.findViewById(R.id.cutimage);
        helpdialog = new Dialog(LoginActivity.this);
        helpdialog.setContentView(R.layout.help_dialog_box);
        helpdialog.getWindow().setBackgroundDrawable(getDrawable(custom_dialog_background));
        helpdialog.show();
        cutimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpdialog.dismiss();
            }
        });
    }
}