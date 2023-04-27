package com.example.fyprojectnew;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VeiwPageActivity extends AppCompatActivity {
    Button buttSignup,buttlogin;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veiw_page);
        buttlogin=(Button)findViewById(R.id.buttlogin);
        buttSignup=(Button)findViewById(R.id.buttSignup);

         /*Login Button for Login :-*/
        buttlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(login);
            }
        });

        /*Signup Button for Signup :-*/
        buttSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(signup);
            }
        });
    }
}