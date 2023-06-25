package com.example.fyprojectnew.Activities;

import static android.content.ContentValues.TAG;
import static com.example.fyprojectnew.R.drawable.custom_dialog_background;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.fyprojectnew.Adapters.EmployeListAdpter;
import com.example.fyprojectnew.Models.EmployeeModel;
import com.example.fyprojectnew.Models.NewEmployees;
import com.example.fyprojectnew.R;
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

public class EmployeeList extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    Dialog fielddialog;
    ImageView cut;
    ImageButton addmore;
    Button newlogin;
    ShimmerFrameLayout shimmereffect;
    TextInputEditText employeename,employeedesignation;
    RecyclerView employeerecyclerview;
    LinearLayoutManager layoutmanager;
    ArrayList<EmployeeModel> employee_items_view;
    EmployeListAdpter adapter;
    Animation scale_up_animation;


    @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Employees List");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));

        shimmereffect=(ShimmerFrameLayout)findViewById(R.id.shimmereffect);
        shimmereffect.startShimmer();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        addmore =(ImageButton) findViewById(R.id.addmore);
        employee_items_view = new ArrayList<>();

       /* Animation :- */
        scale_up_animation=AnimationUtils.loadAnimation(EmployeeList.this,R.anim.scale_up_animation);

        /* Create New Employee :- */
        addmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addmore.startAnimation(scale_up_animation);
                showDialog();
            }
        });

        /* Departments Data set into arraylist :- */
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                employee_items_view.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    EmployeeModel employee = item.getValue(EmployeeModel.class);
                    employee.key=item.getKey().toString();
                    employee_items_view.add(employee);
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

    /* Employee Creating Dialog :- */
    public void showDialog()
    {
        fielddialog = new Dialog(EmployeeList.this);
        fielddialog.setContentView(R.layout.employees_creating_dialog);
        fielddialog.getWindow().setBackgroundDrawable(getDrawable(custom_dialog_background));
        employeedesignation=(TextInputEditText)fielddialog.findViewById(R.id.employeedesignation1);
        cut=(ImageView)fielddialog.findViewById(R.id.cut);
        employeename= (TextInputEditText) fielddialog.findViewById(R.id.employeename);
        newlogin=(Button)fielddialog.findViewById(R.id.newlogin);

       /* Dialog Cancel Button :- */
        cut.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        fielddialog.dismiss();
    }
    });

        /* Dialog Cancel Button :- */
        newlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* Check Credentials :- */
                if (employeename.getText().toString().isEmpty()){
                    employeename.setError("Please Complete the fields");
                }else if (employeedesignation.getText().toString().isEmpty()){
                    employeedesignation.setError("Please Complete the fields");
                }else{

                    /*For get Ket to firebase auto :-*/
                    String key=  mDatabase.child("Employees").push().getKey();

                    /* Data Push to  FireBase DataBase :- */
                    NewEmployees TempUser = new NewEmployees(employeename.getText().toString(),employeedesignation.getText().toString());
                    mDatabase.child("Employees").child(key).setValue(TempUser)
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

    /*RecyclerView Functions :-*/
    @SuppressLint("WrongViewCast")
    private void initRecyclerView() {
        employeerecyclerview = findViewById(R.id.employeerecyclerview);
        layoutmanager = new LinearLayoutManager(this);
        layoutmanager.setOrientation(RecyclerView.VERTICAL);
        adapter = new EmployeListAdpter(this, employee_items_view);
        employeerecyclerview.setLayoutManager(layoutmanager);
        employeerecyclerview.setAdapter(adapter);

    }
   /* Toolbar Menu Option :- */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.serachview_option,menu);
        MenuItem menueitems=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) menueitems.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Search Here!");

        /* Toolbar Menu Option  Action :- */
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

   /* Back Option :- */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}