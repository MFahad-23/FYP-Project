package com.example.fyprojectnew;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PaySlipsActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
RecyclerView payslips;
LinearLayoutManager layoutManager;
PayslipsAdapter myadpter;
List<PaySlipModal> historySlips;
PaySlipModal paySlipModal;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_slips);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("History Slips");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        historySlips=new ArrayList<>();

        /* Departments Data Post into ArrayList :-*/
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Log.d("tag",":"+dataSnapshot);
                historySlips.clear();
                for (DataSnapshot item:dataSnapshot.getChildren()){
                    PaySlipModal historyslips = item.getValue(PaySlipModal.class);
                    historyslips.key=item.getKey().toString();
                    historySlips.add(historyslips);
                }
                Log.d("list",":"+historySlips.size()+"");
                initRecyclerView();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child("Administration").addValueEventListener(postListener);
    }

    private void initRecyclerView() {
        Log.d("view",":"+payslips);
        payslips=(RecyclerView) findViewById(R.id.payslips);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(layoutManager.VERTICAL);
        payslips.setLayoutManager(layoutManager);
        payslips.setAdapter(myadpter);
    }

    /* Back Option :- */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}