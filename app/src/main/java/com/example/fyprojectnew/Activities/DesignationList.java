package com.example.fyprojectnew.Activities;

import static android.content.ContentValues.TAG;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.fyprojectnew.Models.DesignationModel;
import com.example.fyprojectnew.Adapters.DesignationViewAdpter;
import com.example.fyprojectnew.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class DesignationList extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    RecyclerView designationrecyclerview;
    LinearLayoutManager layoutManager;
    ArrayList<DesignationModel>arrayList;
    List<DesignationModel>designation_listview;
    DesignationViewAdpter adpapter;
    ShimmerFrameLayout shimmereffect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designation_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Designation List");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));

        shimmereffect=(ShimmerFrameLayout)findViewById(R.id.shimmereffect);
        shimmereffect.startShimmer();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        arrayList=new ArrayList<>();

        /* Departments Data Set ArrayList :- */
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                for (DataSnapshot item:dataSnapshot.getChildren()){
                    DesignationModel user = item.getValue(DesignationModel.class);
                    arrayList.add(user);
                }
                initRecyclerView();
                shimmereffect.stopShimmer();
                shimmereffect.setVisibility(View.GONE);
                designationrecyclerview.setVisibility(View.VISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // If Fails check error in Logcat
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child("Designations").addListenerForSingleValueEvent(postListener);
    }

    /*ArrayList Functionalities :-*/
    private void initRecyclerView() {
        designationrecyclerview=findViewById(R.id.designationrecyclerview);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(designationrecyclerview.VERTICAL);
        DesignationViewAdpter adpapter=new DesignationViewAdpter(this,arrayList);
        designationrecyclerview.setAdapter(adpapter);
        designationrecyclerview.setLayoutManager(layoutManager);
    }

   /* Back Option :- */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}