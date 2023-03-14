package com.example.fyprojectnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PayrollsPageActivity extends AppCompatActivity {
TextView backoption;
CardView calculate_payrolls,approvals_calculation,approve_files;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payrolls_page);
        backoption=(TextView) findViewById(R.id.backoption);
        calculate_payrolls=(CardView) findViewById(R.id.calculate_payrolls);
        approvals_calculation=(CardView) findViewById(R.id.approvals_calculation);
        approve_files=(CardView) findViewById(R.id.approve_files);

        backoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        calculate_payrolls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PayrollsPageActivity.this,CalculationPageActivity.class));
            }
        });

        approve_files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PayrollsPageActivity.this,ApprovedFiles.class));
            }
        });

        approvals_calculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(PayrollsPageActivity.this,ApprovalPage.class));
            }
        });
    }
}