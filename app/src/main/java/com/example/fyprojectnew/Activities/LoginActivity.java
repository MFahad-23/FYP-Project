package com.example.fyprojectnew.Activities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyprojectnew.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText gmail, password;
    TextView create_account, forget_password;
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
        forget_password = (TextView) findViewById(R.id.forget_password);
        mauth = FirebaseAuth.getInstance();
        helpcard =(LinearLayout)findViewById(R.id.helpcard);

        //Animation Slide_Left :-
        slide_left=AnimationUtils.loadAnimation(LoginActivity.this,R.anim.slide_left);

        /* Create New Account :-*/
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create_account.startAnimation(slide_left);
                Intent next = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(next);
            }
        });

        /* Foreget Password :-*/
        /*Get email from our Field :-*/
        String emailAddress =gmail.getText().toString();

        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mauth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Email sent.");
                                    Toast toast=Toast.makeText(getApplicationContext(), "Check Your Gmail for Password Reset", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.START,50,50);
                                    toast.show();
                                }
                            }
                        });
            }
        });

        // Login Button:-
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*  Check Credentials :- */
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
                                    } else
                                    {
                                        /*Error Dialog :-*/
                                        OpenErrorDialog();
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
                helpcard.startAnimation(slide_left);
                OpenHelpDialog();
            }
        });
    }

    /* Error Dialog :-*/
    private void OpenErrorDialog() {
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

    /* Help Dialog :-*/
    private void OpenHelpDialog() {
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