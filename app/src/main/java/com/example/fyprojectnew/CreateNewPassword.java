package com.example.fyprojectnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateNewPassword extends AppCompatActivity {
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    TextInputEditText password,confirm_password;
    Button submit_button;
    String TAG;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_password);
        password=(TextInputEditText) findViewById(R.id.password);
        confirm_password=(TextInputEditText) findViewById(R.id.confirm_password);
        submit_button=(Button) findViewById(R.id.submit_button);
        user = FirebaseAuth.getInstance().getCurrentUser();
        mAuth = FirebaseAuth.getInstance();

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
                    Toast.makeText(CreateNewPassword.this,"Something Went Wrong!",Toast.LENGTH_SHORT);
                }

            }
        });
    }
}