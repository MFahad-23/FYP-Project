package com.example.fyprojectnew;

import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

public class CalculationPageActivity extends AppCompatActivity {
    public static String final_key="";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    final Calendar myCalendar = Calendar.getInstance();
    List<ClaculationModel> employeeslist;
    RecyclerView employee_searching;
    LinearLayoutManager layoutManager;
    CalculationAdapter myadapter;
    Button calculate;
    Timer timer;
    TextView name;
    ClaculationModel calculationmodal;
    String selected_department="Select Departments";
    MaterialSpinner departments;
    EditText spinner, employeename, baisic_pay, trade_tax, income_tax,
            senior_post_allowance, house_rent_allowance, conveyance_allowance, qualification_allowance,
            medical_allowance, adhoc_relief_2016, adhoc_relief_2017, adhoc_relief_2018, adhoc_relief_2019,
            adhoc_relief_2021, social_security_benefit, leave_deduction, deduction, total_pay, total_allowances;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Payrolls Calculations");
        spinner = (EditText) findViewById(R.id.spinner);
        calculate = (Button) findViewById(R.id.calculate);
        employeename = (EditText) findViewById(R.id.employeename);
        baisic_pay = (EditText) findViewById(R.id.baisic_pay);
        trade_tax = (EditText) findViewById(R.id.trade_tax);
        income_tax = (EditText) findViewById(R.id.income_tax);
        total_allowances = (EditText) findViewById(R.id.total_allowances);
        senior_post_allowance = (EditText) findViewById(R.id.senior_post_allowance);
        house_rent_allowance = (EditText) findViewById(R.id.house_rent_allowance);
        conveyance_allowance = (EditText) findViewById(R.id.conveyance_allowance);
        qualification_allowance = (EditText) findViewById(R.id.qualification_allowance);
        medical_allowance = (EditText) findViewById(R.id.medical_allowance);
        adhoc_relief_2016 = (EditText) findViewById(R.id.adhoc_relief_2016);
        adhoc_relief_2017 = (EditText) findViewById(R.id.adhoc_relief_2017);
        adhoc_relief_2018 = (EditText) findViewById(R.id.adhoc_relief_2018);
        adhoc_relief_2019 = (EditText) findViewById(R.id.adhoc_relief_2019);
        adhoc_relief_2021 = (EditText) findViewById(R.id.adhoc_relief_2021);
        social_security_benefit = (EditText) findViewById(R.id.social_security_benefit);
        leave_deduction = (EditText) findViewById(R.id.leave_deduction);
        deduction = (EditText) findViewById(R.id.deduction);
        total_pay = (EditText) findViewById(R.id.total_pay);
        employeeslist=new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

       /* TextWatcher Implementation :- */
        employeename.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(selected_department.equals("Select Departments"))
                {
                    Toast.makeText(CalculationPageActivity.this, "Please select a department first", Toast.LENGTH_SHORT).show();
                }
                else if(selected_department.equals("Administration"))
                {
                    /* Read Data From DataBase and set it into TextViews :-*/
                    ValueEventListener postListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            employeeslist.clear();
                            for (DataSnapshot item:dataSnapshot.getChildren()) {
                                ClaculationModel employeesmodal = item.getValue(ClaculationModel.class);
                                employeesmodal.key = item.getKey();
                                employeeslist.add(employeesmodal);
                            }
                            /* RecyclerView Functionalities :- */
                            initRecyclerView();
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // If Fails Check Logcat
                            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                        }
                    };
                    mDatabase.child("Administration").orderByChild("employeename").startAt(charSequence.toString()).addValueEventListener(postListener);

                }else if(selected_department.equals("Miscallenous"))
                {
                    /* Read Data From DataBase and set it into TextViews :-*/
                    ValueEventListener postListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            employeeslist.clear();
                            for (DataSnapshot item:dataSnapshot.getChildren()) {
                                ClaculationModel employeesmodal = item.getValue(ClaculationModel.class);
                                employeesmodal.key = item.getKey();
                                employeeslist.add(employeesmodal);
                            }
                            /* RecyclerView Functionalities :- */
                            initRecyclerView();
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // If Fails Check Logcat
                            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                        }
                    };
                    mDatabase.child("Miscallenous").orderByChild("employeename").startAt(charSequence.toString()).addValueEventListener(postListener);

                }else{
                    /* Read Data From DataBase and set it into TextViews :-*/
                    ValueEventListener postListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            employeeslist.clear();
                            for (DataSnapshot item:dataSnapshot.getChildren()) {
                                ClaculationModel employeesmodal = item.getValue(ClaculationModel.class);
                                employeesmodal.key = item.getKey();
                                employeeslist.add(employeesmodal);
                            }
                            /* RecyclerView Functionalities :- */
                            initRecyclerView();
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // If Fails Check Logcat
                            Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                        }
                    };
                    mDatabase.child("Employees").orderByChild("employeename").startAt(charSequence.toString()).addValueEventListener(postListener);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //  Departments Spinner :-
        departments = (MaterialSpinner) findViewById(R.id.departments);
        departments.setItems("Select Departments","Administration", "Mechanical Department",
                "Mech Technology Department", "Physics Department",
                "Civil Engineering", "Chemistry Department", "Maths Department",
                "CS Department", "IT Department","Buissness Administration Department",
                "Miscallaneous","Electrical Department","Elect Technology Department");
        departments.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                selected_department=item;
            }
        });

        /* Calculate Payroll Formulas :- */
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a = Integer.parseInt(baisic_pay.getText().toString());
                int b = Integer.parseInt(trade_tax.getText().toString());
                int c = Integer.parseInt(income_tax.getText().toString());
                int d = Integer.parseInt(senior_post_allowance.getText().toString());
                int e = Integer.parseInt(house_rent_allowance.getText().toString());
                int f = Integer.parseInt(conveyance_allowance.getText().toString());
                int g = Integer.parseInt(qualification_allowance.getText().toString());
                int h = Integer.parseInt(medical_allowance.getText().toString());
                int i = Integer.parseInt(adhoc_relief_2016.getText().toString());
                int j = Integer.parseInt(adhoc_relief_2017.getText().toString());
                int k = Integer.parseInt(adhoc_relief_2018.getText().toString());
                int l = Integer.parseInt(adhoc_relief_2019.getText().toString());
                int m = Integer.parseInt(adhoc_relief_2021.getText().toString());
                int n = Integer.parseInt(social_security_benefit.getText().toString());
                int o = Integer.parseInt(leave_deduction.getText().toString());
                int p = Integer.parseInt(deduction.getText().toString());

                /* Total Allowances Result :- */
                int allowances;
                allowances = a + d + e + f + g + h + i + j + k + l + m + n;
                total_allowances.setText(allowances + "");

                /* Total Result :- */
                int result;
                result = a - b - c + d + e + f + g + h + i + j + k + l + m + n - o - p;
                total_pay.setText(result + "");

                /*For get Ket to firebase auto :-*/
                String key = mDatabase.child("Payrolls").push().getKey();

                /* Data Push into FireBase Database :- */
                ClaculationModel TempUser = new ClaculationModel(departments.getText().toString(), employeename.getText().toString(),
                        spinner.getText().toString(), Integer.parseInt(baisic_pay.getText().toString()), Integer.parseInt(trade_tax.getText().toString()),
                        Integer.parseInt(income_tax.getText().toString()), Integer.parseInt(senior_post_allowance.getText().toString()), Integer.parseInt(house_rent_allowance.getText().toString()),
                        Integer.parseInt(conveyance_allowance.getText().toString()), Integer.parseInt(qualification_allowance.getText().toString()), Integer.parseInt(medical_allowance.getText().toString()),
                        Integer.parseInt(adhoc_relief_2016.getText().toString()), Integer.parseInt(adhoc_relief_2017.getText().toString()), Integer.parseInt(adhoc_relief_2019.getText().toString()),
                        Integer.parseInt(adhoc_relief_2019.getText().toString()), Integer.parseInt(adhoc_relief_2021.getText().toString()), Integer.parseInt(social_security_benefit.getText().toString()),
                        Integer.parseInt(leave_deduction.getText().toString()), Integer.parseInt(deduction.getText().toString()), result, allowances,key,final_key);

                mDatabase.child("Payrolls").child(key).setValue(TempUser)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                employeename.setText("");
                                spinner.setText("");
                                baisic_pay.setText("");
                                trade_tax.setText("");
                                income_tax.setText("");
                                senior_post_allowance.setText("");
                                house_rent_allowance.setText("");
                                conveyance_allowance.setText("");
                                qualification_allowance.setText("");
                                medical_allowance.setText("");
                                adhoc_relief_2016.setText("");
                                adhoc_relief_2017.setText("");
                                adhoc_relief_2018.setText("");
                                adhoc_relief_2019.setText("");
                                adhoc_relief_2021.setText("");
                                social_security_benefit.setText("");
                                leave_deduction.setText("");
                                deduction.setText("");
                                total_pay.setText("");
                                total_allowances.setText("");
                                Toast.makeText(CalculationPageActivity.this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CalculationPageActivity.this, "Something went wrong.Please try Again!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        /* Date Picker :- */
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
                Log.d("key", "chk");
            }
        };
        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ky", "Error");
                new DatePickerDialog(CalculationPageActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    /* RecyclerView Method :- */
    private void initRecyclerView() {
        employee_searching = findViewById(R.id.employee_searching);
        layoutManager=new LinearLayoutManager(CalculationPageActivity.this);
        layoutManager.setOrientation(layoutManager.VERTICAL);
        myadapter=new CalculationAdapter(this.employeeslist);
        employee_searching.setAdapter(myadapter);
        employee_searching.setLayoutManager(layoutManager);
    }

    private void updateLabel() {
        Log.d("on", "Msg");
        String myFormat = "MM/dd/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        spinner.setText(dateFormat.format(myCalendar.getTime()));
    }

    /* Activity Back Option :- */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
//        Session Spinner :-
//        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);
//        spinner.setItems("Session", "2019-2020", "2019-2020", "2019-2020", "2019-2020", "2019-2020",
//                "2019-2020","2019-2020","2019-2020");
//        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
//
//            @Override
//            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_INDEFINITE).show();
//            }
//        });