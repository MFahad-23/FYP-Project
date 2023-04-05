package com.example.fyprojectnew;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ApprovedFiles extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    ArrayList<ApprovalsModel> aprovedlist;
    RecyclerView approved_employees;
    ApprovalsAdapter myadpter;
    LinearLayoutManager layoutManager;
    RelativeLayout approvedfiles_layout;
    ShimmerFrameLayout shimmereffect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_files);

        shimmereffect=(ShimmerFrameLayout)findViewById(R.id.shimmereffect);
        shimmereffect.startShimmer();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        aprovedlist=new ArrayList<>();
        /*Departsmnets Data Post into the arraylist :-*/
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                for (DataSnapshot item:dataSnapshot.getChildren()){
                    ApprovalsModel user = item.getValue(ApprovalsModel.class);
//                    if(item.child("employee_image").exists())
//                    {
//                        Log.d("test",item.child("employee_image").getValue().toString());
//                        user.employee_image=item.child("employee_image").getValue().toString();
//                    }
                    aprovedlist.add(user);
                }
                initRecyclerView();
                shimmereffect.stopShimmer();
                shimmereffect.setVisibility(View.GONE);
                approved_employees.setVisibility(View.VISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child("Employee Approvals").addListenerForSingleValueEvent(postListener);

    }

    /*ArrayList Functionalities :-*/
    @SuppressLint("WrongViewCast")
    private void initRecyclerView() {
        approved_employees = findViewById(R.id.approved_employees);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(layoutManager.VERTICAL);
        myadpter=new ApprovalsAdapter(aprovedlist, this);
        approved_employees.setLayoutManager(layoutManager);
        approved_employees.setAdapter(myadpter);
    }
}