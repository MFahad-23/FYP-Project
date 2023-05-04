package com.example.fyprojectnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Timer;
import java.util.TimerTask;

public class CreateNewPassword extends AppCompatActivity {
    private FirebaseUser user;
    TextInputEditText password,confirm_password;
    Button submit_button;
    FloatingActionButton Back_Button;
    String TAG;
    Timer timer;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_password);
        password=(TextInputEditText) findViewById(R.id.password);
        confirm_password=(TextInputEditText) findViewById(R.id.confirm_password);
        submit_button=(Button) findViewById(R.id.submit_button);
        Back_Button=(FloatingActionButton) findViewById(R.id.Back_Button);
        user = FirebaseAuth.getInstance().getCurrentUser();

        /* Back_Button */
        Back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /* Update Your Password */
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passA=password.getText().toString();
                String passB=confirm_password.getText().toString();
                if (passA.equals(passB)){
                   user.updatePassword(passA).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void unused) {
                           password.setText("");
                           confirm_password.setText("");
                           FirebaseAuth.getInstance().signOut();
                           timer=new Timer();
                           timer.schedule(new TimerTask() {
                               @Override
                               public void run() {
                                   startActivity(new Intent(CreateNewPassword.this,LoginActivity.class));
                               }
                           },2000);
                           Toast.makeText(CreateNewPassword.this,"Password Update Successfully",Toast.LENGTH_SHORT);
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           e.printStackTrace();
                           Toast.makeText(CreateNewPassword.this,e.getMessage(),Toast.LENGTH_SHORT);
                       }
                   });
                }else
                {
                    Toast.makeText(CreateNewPassword.this,"Please Match the Passwords",Toast.LENGTH_SHORT);
                }
            }
        });
    }
}