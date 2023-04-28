package com.example.fyprojectnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

public class PaySlipsActivity extends AppCompatActivity {
RecyclerView payslips;
LinearLayoutManager layoutManager;
PayslipsAdapter myadpter;
PaySlipModal paySlipModal;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_slips);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("History Slips");

        initRecyclerView();
    }

    private void initRecyclerView() {
        payslips=(RecyclerView) findViewById(R.id.payslips);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(layoutManager.VERTICAL);
        payslips.setLayoutManager(layoutManager);
        payslips.setAdapter(myadpter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}