package com.example.fyprojectnew;

import static android.content.ContentValues.TAG;

import static com.example.fyprojectnew.R.drawable.custom_dialog_background;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import java.util.List;

public class EmployeeList extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    Dialog fielddialog;
    ImageView cut;
    Button addmore,newlogin;
    ShimmerFrameLayout shimmereffect;
    TextInputEditText employeename,employeedesignation;
    RecyclerView employeerecyclerview;
    LinearLayoutManager layoutmanager;
    ArrayList<EmployeeModel> arrayList;
    List<EmployeeModel> employee_items_view;
    EmployeListAdpter adapter;

    @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);
        shimmereffect=(ShimmerFrameLayout)findViewById(R.id.shimmereffect);
        shimmereffect.startShimmer();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        addmore =(Button)findViewById(R.id.addmore);
        arrayList = new ArrayList<>();
        addmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog();

            }
        });

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                arrayList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    EmployeeModel employee = item.getValue(EmployeeModel.class);
                    arrayList.add(employee);
                }
                initRecyclerView();
                shimmereffect.stopShimmer();
                shimmereffect.setVisibility(View.GONE);
                employeerecyclerview.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child("Employees").addValueEventListener(postListener);
    }
    public void showDialog()
    {
        fielddialog = new Dialog(EmployeeList.this);
        fielddialog.setContentView(R.layout.employees_creating_dialog);
        fielddialog.getWindow().setBackgroundDrawable(getDrawable(custom_dialog_background));
        employeedesignation=(TextInputEditText)fielddialog.findViewById(R.id.employeedesignation);
        cut=(ImageView)fielddialog.findViewById(R.id.cut);
        TextInputEditText employeename= (TextInputEditText) fielddialog.findViewById(R.id.employeename);
        newlogin=(Button)fielddialog.findViewById(R.id.newlogin);
        cut.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        fielddialog.dismiss();
    }
});
        newlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (employeename.getText().toString().isEmpty()){
                    employeename.setError("Please Complete the fields");
                }else if (employeedesignation.getText().toString().isEmpty()){
                    employeedesignation.setError("Please Complete the fields");
                }else{
                    NewEmployees tempuser = new NewEmployees(employeename.getText().toString(),employeedesignation.getText().toString());
                    String key=  mDatabase.child("Employees").push().getKey();
                    mDatabase.child("Employees").child(key).setValue(tempuser)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    employeename.setText("");
                                    employeedesignation.setText("");
                                    fielddialog.dismiss();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EmployeeList.this,"Something Went Wrong!",Toast.LENGTH_SHORT);
                                }
                            });
                }
            }
        });
        fielddialog.show();
    }
    @SuppressLint("WrongViewCast")
    private void initRecyclerView() {
        employeerecyclerview = findViewById(R.id.employeerecyclerview);
        layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(RecyclerView.VERTICAL);
        EmployeListAdpter adapter = new EmployeListAdpter(this, arrayList);
        employeerecyclerview.setLayoutManager(layoutmanager);
        employeerecyclerview.setAdapter(adapter);

    }
}