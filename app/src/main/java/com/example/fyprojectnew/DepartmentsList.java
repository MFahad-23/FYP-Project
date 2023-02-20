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
import java.util.Timer;
import java.util.TimerTask;

public class DepartmentsList extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    ArrayList<DepartmentModel> arrayList;
    RecyclerView departrecyclerview;
    DepartListAdapter myadpter;
    LinearLayoutManager layoutManager;
    RelativeLayout departlyout;
    ShimmerFrameLayout simmereffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments);

        simmereffect=(ShimmerFrameLayout)findViewById(R.id.simmereffect);
        simmereffect.startShimmer();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        arrayList=new ArrayList<>();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                for (DataSnapshot item:dataSnapshot.getChildren()){
                     DepartmentModel user = item.getValue(DepartmentModel.class);
                        arrayList.add(user);
                   }
                        initRecyclerView();
                        simmereffect.stopShimmer();
                        simmereffect.setVisibility(View.GONE);
                        departrecyclerview.setVisibility(View.VISIBLE);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child("departments").addListenerForSingleValueEvent(postListener);

    }
    @SuppressLint("WrongViewCast")
    private void initRecyclerView() {
        departrecyclerview = findViewById(R.id.departrecyclerview);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(layoutManager.VERTICAL);
        DepartListAdapter myadpter = new DepartListAdapter(this, arrayList);
        departrecyclerview.setLayoutManager(layoutManager);
        departrecyclerview.setAdapter(myadpter);
    }
}