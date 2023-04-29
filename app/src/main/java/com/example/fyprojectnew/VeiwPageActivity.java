package com.example.fyprojectnew;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VeiwPageActivity extends AppCompatActivity {
    Button LoginButton,SignUpButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veiw_page);
        LoginButton=(Button)findViewById(R.id.LoginButton);
        SignUpButton=(Button)findViewById(R.id.SignUpButton);

         /* Button for Login :-*/
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(login);
            }
        });

        /* Button for Signup :-*/
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(signup);
            }
        });
    }
}