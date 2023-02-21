package com.example.fyprojectnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MiscallennousStaff extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    ShimmerFrameLayout shimmereffect;
    RelativeLayout miscallenousstaff;
    RecyclerView miscallenouslist;
    ArrayList<MiscallenousModel>miscallenousModelList;
    LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miscallennous_staff);
        shimmereffect=(ShimmerFrameLayout)findViewById(R.id.shimmereffect);
        shimmereffect.startShimmer();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        miscallenousModelList=new ArrayList<>();

    }
}