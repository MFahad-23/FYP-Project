package com.example.fyprojectnew;

import static android.content.ContentValues.TAG;

import static com.example.fyprojectnew.R.drawable.custom_dialog_background;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Administration_staff extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    ShimmerFrameLayout shimmereffect;
    RecyclerView adminstaff;
    RelativeLayout adminstafflist;
    TextInputEditText employeename,employeedesignation;
    Button addmore,newlogin;
    Dialog dialog;
    ImageView cut;
    ArrayList<AdministrationModel>administration_listview;
    LinearLayoutManager layoutManager;
    AdministrtaionAdapter myadpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration_staff);
        addmore=(Button) findViewById(R.id.addmore);
        shimmereffect=(ShimmerFrameLayout) findViewById(R.id.shimmereffect);
        shimmereffect.startShimmer();
        adminstaff=(RecyclerView) findViewById(R.id.adminstaff);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        administration_listview=new ArrayList<>();

        addmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        /*Departsmnets Data Post into the arraylist :-*/
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                administration_listview.clear();
                for (DataSnapshot item:dataSnapshot.getChildren()){
                    AdministrationModel adminstrationstaff = item.getValue(AdministrationModel.class);
                    administration_listview.add(adminstrationstaff);
                }
                initRecyclerView();
                shimmereffect.stopShimmer();
                shimmereffect.setVisibility(View.GONE);
                adminstaff.setVisibility(View.VISIBLE);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child("Administration").addValueEventListener(postListener);
    }

    private void openDialog() {
        dialog =new Dialog(Administration_staff.this);
        dialog.setContentView(R.layout.employees_creating_dialog);
        dialog.getWindow().setBackgroundDrawable(getDrawable(custom_dialog_background));
        employeename= (TextInputEditText) dialog.findViewById(R.id.employeename);
        employeedesignation=(TextInputEditText)dialog.findViewById(R.id.employeedesignation);
        cut=(ImageView) dialog.findViewById(R.id.cut);
        newlogin=(Button) dialog.findViewById(R.id.newlogin);
        dialog.show();

        /* Dialog Dissmiss Function :-*/
        cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        /*Create New Employee Function :-*/
        newlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (employeename.getText().toString().isEmpty()){
                    employeename.setError("Please Complete the fields");
                }else if (employeedesignation.getText().toString().isEmpty()){
                    employeedesignation.setError("Please Complete the fields");
                }else{

                    /*Data pust to DataBase for save :-*/
                    NewEmployees tempuser = new NewEmployees(employeename.getText().toString(),employeedesignation.getText().toString());

                    /*For get Ket to firebase auto :-*/
                    String key=  mDatabase.child("Administration").push().getKey();

                    mDatabase.child("Administration").child(key).setValue(tempuser)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    employeename.setText("");
                                    employeedesignation.setText("");
                                    dialog.dismiss();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Administration_staff.this,"Something Went Wrong!",Toast.LENGTH_SHORT);
                                }
                            });
                }
            }
        });

    }

    private void initRecyclerView() {
        adminstaff = findViewById(R.id.adminstaff);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(layoutManager.VERTICAL);
        AdministrtaionAdapter myadapter=new AdministrtaionAdapter(this,administration_listview);
        adminstaff.setAdapter(myadapter);
        adminstaff.setLayoutManager(layoutManager);
    }
}