package com.example.fyprojectnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PayrollsPageActivity extends AppCompatActivity {
CardView calculation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payrolls_page);
        calculation=(CardView)findViewById(R.id.calculation);
        calculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next=new Intent(getApplicationContext(),CalculationPageActivity.class);
                startActivity(next);
            }
        });
    }
}