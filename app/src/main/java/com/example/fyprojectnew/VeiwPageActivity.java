package com.example.fyprojectnew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VeiwPageActivity extends AppCompatActivity {
    Button buttSignup,buttlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veiw_page);
        buttlogin=(Button)findViewById(R.id.button1);
        buttSignup=(Button)findViewById(R.id.button2);

        //Button's Area:-
        buttlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(login);
            }
        });
        buttSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(signup);
            }
        });
    }
}