package com.example.fyprojectnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Timer;
import java.util.TimerTask;

public class SignUpActivity extends AppCompatActivity {
    TextInputEditText username,cnic, gmail, contact,city, password;
    ImageView googlelink, facebooklink;
    Button submit;
    String TAG;
    Timer timer;
    Dialog dialogbox;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String passwordpattern = "[^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,30}$]";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        username = (TextInputEditText) findViewById(R.id.username);
        cnic = (TextInputEditText) findViewById(R.id.cnic);
        gmail = (TextInputEditText) findViewById(R.id.gmail);
        contact = (TextInputEditText) findViewById(R.id.contact);
        city = (TextInputEditText) findViewById(R.id.city);
        TextView backoption = (TextView) findViewById(R.id.backoption);
        password = (TextInputEditText) findViewById(R.id.password);
        submit = (Button) findViewById(R.id.submit);
        googlelink = (ImageView) findViewById(R.id.googlelink);
        facebooklink = (ImageView) findViewById(R.id.facebooklink);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        /*Google Link:-*/
        googlelink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotourl("https://www.google.com/");
            }

            private void gotourl(String s) {
                Uri link = Uri.parse(s);
                startActivity(new Intent(Intent.ACTION_VIEW, link));
            }
        });

       /* Facebook Link :-*/
        facebooklink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotourl("https://www.facebook.com/");
            }

            private void gotourl(String s) {
                Uri link = Uri.parse(s);
                startActivity(new Intent(Intent.ACTION_VIEW, link));
            }
        });

        /*Back Option :-*/
        backoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /* Firebase Athentication Validity Checking:-*/
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().isEmpty()){
                    username.setError("*Please complete the field*");
                }
                else if(cnic.getText().toString().isEmpty())
                {
                   cnic.setError("" +
                           "*Please complete the field*");
                }
                else if (gmail.getText().toString().isEmpty()){
                    gmail.setError("*Please complete the field*");
                }
                else if (gmail.getText().toString().matches(emailPattern)){
                    Toast.makeText(SignUpActivity.this,"*Wrong Format*",Toast.LENGTH_LONG).show();
                }
                else if (contact.getText().toString().isEmpty()){
                    contact.setError("*Please Complete the Field*");
                }
                else if (contact.getText().toString().length()<12){
                    Toast.makeText(SignUpActivity.this,"Please Enter Correct Contact",Toast.LENGTH_LONG).show();
                }
                else if (city.getText().toString().isEmpty()){
                    city.setError("*Please Complete the Field*");
                }
                else if (password.getText().toString().isEmpty()){
                    password.setError("*Please Complete the Field*");
                }
                else if (password.getText().toString().matches(passwordpattern)){
                    Toast.makeText(SignUpActivity.this,"Please Enter Password with" +
                            "Min 1 uppercase letter," +
                            "Min 1 lowercase letter," +
                            "Min 1 special character," +
                            "Min 1 number," +
                            "Min 8 characters.",Toast.LENGTH_LONG).show();
                }
                else{
                   /* Firebase Authentication :-*/
                    mAuth.createUserWithEmailAndPassword(gmail.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(SignUpActivity.this, "Registration Done! Please login now.",
                                                Toast.LENGTH_SHORT).show();
                                        timer=new Timer();
                                        timer.schedule(new TimerTask() {
                                            @Override
                                            public void run() {
                                                mAuth.getInstance().signOut();
                                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                            }
                                        },3000);

                                        /*Data save into the Database :-*/
                                        User tempuser = new User(username.getText().toString(),cnic.getText().toString(),
                                                gmail.getText().toString(),contact.getText().toString(),city.getText().toString(),
                                                password.getText().toString(),"");
                                        mDatabase.child("users").child(user.getUid().toString()).setValue(tempuser)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        username.setText("");
                                                        cnic.setText("");
                                                        gmail.setText("");
                                                        contact.setText("");
                                                        city.setText("");
                                                        password.setText("");
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w(TAG, "Something went wrong.Please try Again!", task.getException());
                                                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(),
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "Something went wrong.Please LogIn with correct User!", task.getException());
                                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}