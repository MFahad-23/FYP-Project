package com.example.fyprojectnew.Activities;

import static android.content.ContentValues.TAG;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.fyprojectnew.Adapters.DepartListAdapter;
import com.example.fyprojectnew.Models.DepartmentModel;
import com.example.fyprojectnew.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class DepartmentsList extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    ArrayList<DepartmentModel> departlist;
    RecyclerView departrecyclerview;
    DepartListAdapter myadpter;
    LinearLayoutManager layoutManager;
    RelativeLayout departlyout;
    ShimmerFrameLayout simmereffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Department List");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));

        simmereffect=(ShimmerFrameLayout)findViewById(R.id.simmereffect);
        simmereffect.startShimmer();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        departlist=new ArrayList<>();

        /* Departments Data Set in ArrayList :-*/
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                for (DataSnapshot item:dataSnapshot.getChildren()){
                     DepartmentModel user = item.getValue(DepartmentModel.class);
                     if(item.child("departmentimage").exists())
                     {
                         Log.d("test",item.child("departmentimage").getValue().toString());
                         user.departmentimage=item.child("departmentimage").getValue().toString();
                     }
                    departlist.add(user);
                   }
                        initRecyclerView();
                        simmereffect.stopShimmer();
                        simmereffect.setVisibility(View.GONE);
                        departrecyclerview.setVisibility(View.VISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // If Fails Check Error In Logcat
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child("departments").addListenerForSingleValueEvent(postListener);
    }

    /*ArrayList Functionalities :-*/
    @SuppressLint("WrongViewCast")
    private void initRecyclerView() {
        departrecyclerview = findViewById(R.id.departrecyclerview);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(layoutManager.VERTICAL);
        DepartListAdapter myadpter = new DepartListAdapter(this, departlist);
        departrecyclerview.setLayoutManager(layoutManager);
        departrecyclerview.setAdapter(myadpter);
    }

    /* Back  Options :- */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}