package com.example.fyprojectnew;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
        shimmereffect=(ShimmerFrameLayout)findViewById(R.id.shimmereffect);
        shimmereffect.startShimmer();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        arrayList=new ArrayList<>();

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
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child("Designations").addListenerForSingleValueEvent(postListener);

    }

    private void initRecyclerView() {
        designationrecyclerview=findViewById(R.id.designationrecyclerview);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(designationrecyclerview.VERTICAL);
        DesignationViewAdpter adpapter=new DesignationViewAdpter(this,arrayList);
        designationrecyclerview.setAdapter(adpapter);
        designationrecyclerview.setLayoutManager(layoutManager);
    }
}