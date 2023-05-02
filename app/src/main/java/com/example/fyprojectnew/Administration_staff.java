package com.example.fyprojectnew;

import static android.content.ContentValues.TAG;
import static com.example.fyprojectnew.R.drawable.custom_dialog_background;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Dialog;
import android.os.Build;
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
import android.widget.RelativeLayout;
import android.widget.SearchView;
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
    com.google.android.material.textfield.TextInputEditText employeename,employeedesignation;
    Button newlogin;
    Dialog dialog;
    ImageView cut;
    ImageButton addmore;
    Animation scale_up_animation;

    ArrayList<AdministrationModel>administration_listview;
    LinearLayoutManager layoutManager;
    AdministrtaionAdapter myadpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration_staff);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Administration Staff");
        addmore=(ImageButton) findViewById(R.id.addmore);
        shimmereffect=(ShimmerFrameLayout) findViewById(R.id.shimmereffect);
        shimmereffect.startShimmer();
        adminstaff=(RecyclerView) findViewById(R.id.adminstaff);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        administration_listview=new ArrayList<>();

        scale_up_animation= AnimationUtils.loadAnimation(Administration_staff.this,R.anim.scale_up_animation);

       /* Create New Employee  Dialog :- */
        addmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addmore.startAnimation(scale_up_animation);
                OpenDialog();
            }
        });

        /* Departments Data Post into ArrayList :-*/
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                administration_listview.clear();
                for (DataSnapshot item:dataSnapshot.getChildren()){
                    AdministrationModel adminstrationstaff = item.getValue(AdministrationModel.class);
                    adminstrationstaff.key=item.getKey().toString();
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

    private void OpenDialog() {
        dialog =new Dialog(Administration_staff.this);
        dialog.setContentView(R.layout.employees_creating_dialog);
        dialog.getWindow().setBackgroundDrawable(getDrawable(custom_dialog_background));
        employeename=(com.google.android.material.textfield.TextInputEditText) dialog.findViewById(R.id.employeename);
        employeedesignation=(com.google.android.material.textfield.TextInputEditText)dialog.findViewById(R.id.employeedesignation1);
        cut=(ImageView) dialog.findViewById(R.id.cut);
        newlogin=(Button) dialog.findViewById(R.id.newlogin);
        dialog.show();

        /* Dialog Dismiss :- */
        cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        /* Create New Employee & Post Data into FireBase DataBase :-*/
        newlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Check Credentials :- */
                if (employeename.getText().toString().isEmpty()){
                    employeename.setError("Please Complete the fields");
                }else if (employeedesignation.getText().toString().isEmpty()){
                    employeedesignation.setError("Please Complete the fields");
                }else{
                    /* For Generate Key Auto :- */
                    String key=  mDatabase.child("Administration").push().getKey();

                    /* Data Push to  FireBase DataBase :-*/
                    NewEmployees TempUser = new NewEmployees(employeename.getText().toString(),employeedesignation.getText().toString());
                    mDatabase.child("Administration").child(key).setValue(TempUser)
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
        myadpter=new AdministrtaionAdapter(this,administration_listview);
        adminstaff.setAdapter(myadpter);
        adminstaff.setLayoutManager(layoutManager);
    }

    /* Activity Search Option :- */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.serachview_option,menu);
        MenuItem MenuItems=menu.findItem(R.id.search);
        SearchView searchView=(SearchView) MenuItems.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            searchView.setBackgroundColor(getColor(R.color.lightblue));
            int paddingRight = searchView.getPaddingRight();
        }
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

    /* Activity Back Option :- */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}