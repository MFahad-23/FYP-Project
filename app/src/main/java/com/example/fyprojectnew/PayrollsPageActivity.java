package com.example.fyprojectnew;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PayrollsPageActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    TextView date1,department_name,programme1,sessionno,date2,department,programme2,teacher_name,subject_name,
            subject,section,semister,qualification;
    ApprovalsModel model;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payrolls_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Approved File");
        date1 = (TextView) findViewById(R.id.date1);
        department_name = (TextView) findViewById(R.id.department_name);
        programme1 = (TextView) findViewById(R.id.programme1);
        sessionno = (TextView) findViewById(R.id.sessionno);
        date2 = (TextView) findViewById(R.id.date2);
        department = (TextView) findViewById(R.id.department);
        programme2 = (TextView) findViewById(R.id.programme2);
        teacher_name = (TextView) findViewById(R.id.teacher_name);
        subject_name = (TextView) findViewById(R.id.subject_name);
        subject = (TextView) findViewById(R.id.subject);
        section = (TextView) findViewById(R.id.section);
        semister = (TextView) findViewById(R.id.semister);
        qualification = (TextView) findViewById(R.id.qualification);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        model = (ApprovalsModel) getIntent().getSerializableExtra("data");
        Log.d("break",model.english_date);
        date1.setText(model.english_date);
        department_name.setText(model.department);
        programme1.setText(model.section);
        sessionno.setText(model.session_spinner);
        date2.setText(model.english_date);
        department.setText(model.department);
        programme2.setText(model.section);
        teacher_name.setText(model.employee_name);
        subject_name.setText(model.teaching_subject);
        subject.setText(model.subject);
        section.setText(model.session);
        semister.setText(model.semister_spinner);
        qualification.setText(model.employee_qualification);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.approvals_menu, menu);
        MenuItem menuItem2 = menu.findItem(R.id.download);
        MenuItem menuItem1 = menu.findItem(R.id.share);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}