package com.example.fyprojectnew;

import static android.content.ContentValues.TAG;

import static com.example.fyprojectnew.R.drawable.custom_dialog_background;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
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

public class MiscallennousStaff extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    ShimmerFrameLayout shimmereffect;
    RelativeLayout miscallenousstaff;
    RecyclerView miscallenouslist;
    MiscallenousListAdapter myadpter;
    TextInputEditText employeename,employeedesignation;
    Button newlogin;
    Dialog dialog;
    ImageView cut,addmore;
    ArrayList<MiscallenousModel>miscellenouslist;
    LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miscallennous_staff);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Miscallennous Staff");
        addmore=(ImageView) findViewById(R.id.addmore);
        shimmereffect=(ShimmerFrameLayout)findViewById(R.id.shimmereffect);
        shimmereffect.startShimmer();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        miscellenouslist=new ArrayList<>();

       /* Add More Employee Button :-*/
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
                miscellenouslist.clear();
                for (DataSnapshot item:dataSnapshot.getChildren()){
                    MiscallenousModel miscallenousvalues = item.getValue(MiscallenousModel.class);
                    miscallenousvalues.Key=item.getKey().toString();
                    miscellenouslist.add(miscallenousvalues);
                }
                initRecyclerView();
                shimmereffect.stopShimmer();
                shimmereffect.setVisibility(View.GONE);
                miscallenouslist.setVisibility(View.VISIBLE);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child("Miscallenous").addValueEventListener(postListener);
    }
    private void openDialog() {
        dialog =new Dialog(MiscallennousStaff.this);
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
                            String key=  mDatabase.child("Miscallenous").push().getKey();

                            mDatabase.child("Miscallenous").child(key).setValue(tempuser)
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
                                            Toast.makeText(MiscallennousStaff.this,"Something Went Wrong!",Toast.LENGTH_SHORT);
                                        }
                                    });
                        }
                    }
                });
    }

    /*ArrayList Functionalities :-*/
    @SuppressLint("WrongViewCast")
    private void initRecyclerView() {
        miscallenouslist = findViewById(R.id.miscallenouslist);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(layoutManager.VERTICAL);
        myadpter = new MiscallenousListAdapter(this,miscellenouslist);
        miscallenouslist.setLayoutManager(layoutManager);
        miscallenouslist.setAdapter(myadpter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.serachview_option,menu);
        MenuItem menueitems=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) menueitems.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Search Here!");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                myadpter.getFilter().filter(s);

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}