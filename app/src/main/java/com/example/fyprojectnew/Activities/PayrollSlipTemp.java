package com.example.fyprojectnew.Activities;

import static android.Manifest.permission.MANAGE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.fyprojectnew.Models.ClaculationModel;
import com.example.fyprojectnew.Models.PaySlipModal;
import com.example.fyprojectnew.R;
import com.gkemon.XMLtoPDF.PdfGenerator;
import com.gkemon.XMLtoPDF.PdfGeneratorListener;
import com.gkemon.XMLtoPDF.model.FailureResponse;
import com.gkemon.XMLtoPDF.model.SuccessResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.BreakIterator;
import java.util.Date;

public class PayrollSlipTemp extends AppCompatActivity {
    ScrollView sliplayout;
    private DatabaseReference mDatabase;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    String name, designation, key;
    TextView employee_name, employee_designation;
    ClaculationModel calculationmodal;
    TextView employee_pay, senior_post, house_rent,
            conveyance, qualification, medical,
            Relief_2016, Relief_2017, Relief_2018, Relief_2019,
            Relief_2021, social_security, total_allowances, income,
            trade, total_payroll, date;

    private PdfGenerator.XmlToPDFLifecycleObserver xmlToPDFLifecycleObserver;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payroll_slip_temp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pay Slips");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        employee_pay = (TextView) findViewById(R.id.employee_pay);
        date = (TextView) findViewById(R.id.date);
        sliplayout=(ScrollView)findViewById(R.id.sliplayout);
        senior_post = (TextView) findViewById(R.id.senior_post);
        house_rent = (TextView) findViewById(R.id.house_rent);
        conveyance = (TextView) findViewById(R.id.conveyance);
        qualification = (TextView) findViewById(R.id.qualification);
        medical = (TextView) findViewById(R.id.medical);
        Relief_2016 = (TextView) findViewById(R.id.Relief_2016);
        Relief_2017 = (TextView) findViewById(R.id.Relief_2017);
        Relief_2018 = (TextView) findViewById(R.id.Relief_2018);
        Relief_2019 = (TextView) findViewById(R.id.Relief_2019);
        Relief_2021 = (TextView) findViewById(R.id.Relief_2021);
        social_security = (TextView) findViewById(R.id.social_security);
        total_allowances = (TextView) findViewById(R.id.total_allowances);
        income = (TextView) findViewById(R.id.income);
        trade = (TextView) findViewById(R.id.trade);
        total_payroll = (TextView) findViewById(R.id.total_payroll1);


        employee_name = (TextView) findViewById(R.id.employee_name);
        employee_designation = (TextView) findViewById(R.id.employee_designation);

        xmlToPDFLifecycleObserver = new PdfGenerator.XmlToPDFLifecycleObserver(this);
        getLifecycle().addObserver(xmlToPDFLifecycleObserver);

        designation = getIntent().getStringExtra("designation");
        name = getIntent().getStringExtra("name");
        key = getIntent().getStringExtra("user_id");
        Log.d("id_check", ":" + key);

        employee_designation.setText(designation);
        employee_name.setText(name);

        /* Read Data From DataBase and set it into TextViews :-*/
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        calculationmodal = item.getValue(ClaculationModel.class);
                        employee_pay.setText(calculationmodal.baisic_pay + "");
                        senior_post.setText(calculationmodal.senior_post_allowance + "");
                        house_rent.setText(calculationmodal.house_rent_allowance + "");
                        conveyance.setText(calculationmodal.conveyance_allowance + "");
                        qualification.setText(calculationmodal.qualification_allowance + "");
                        medical.setText(calculationmodal.medical_allowance + "");
                        Relief_2016.setText(calculationmodal.adhoc_relief_2016 + "");
                        Relief_2017.setText(calculationmodal.adhoc_relief_2017 + "");
                        Relief_2018.setText(calculationmodal.adhoc_relief_2018 + "");
                        Relief_2019.setText(calculationmodal.adhoc_relief_2019 + "");
                        Relief_2021.setText(calculationmodal.adhoc_relief_2021 + "");
                        social_security.setText(calculationmodal.social_security_benefit + "");
                        total_allowances.setText(calculationmodal.allowances + "");
                        income.setText(calculationmodal.income_tax + "");
                        trade.setText(calculationmodal.trade_tax + "");
                        total_payroll.setText(calculationmodal.total_pay + "");
                        date.setText(calculationmodal.spinner);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // If Fails Check Logcat
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child("Payrolls").orderByChild("user_id").equalTo(key).addValueEventListener(postListener);


        Log.d("test", designation + " : " + name);

        ActivityCompat.requestPermissions(this,
                new String[]{
                        READ_EXTERNAL_STORAGE,
                        MANAGE_EXTERNAL_STORAGE
                }, 1
        );
        // If you have access to the external storage, do whatever you need
        if (Environment.isExternalStorageManager()) {
            // If you don't have access, launch a new activity to show the user the system's dialog
            // to allow access to the external storage
        } else {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            Uri uri = Uri.fromParts("package", this.getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        }

    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            READ_EXTERNAL_STORAGE,
            WRITE_EXTERNAL_STORAGE
    };

    //    /**
//     * Checks if the app has permission to write to device storage
//     *
//     * If the app does not has permission then the user will be prompted to grant permissions
//     *
//     * @param activity
//     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    /* Toolbar Menu Option :- */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions_toolbar, menu);
        MenuItem menuItem = menu.findItem(R.id.option);
        MenuItem menuItem1 = menu.findItem(R.id.share);
        MenuItem menuItem2 = menu.findItem(R.id.download);
        return super.onCreateOptionsMenu(menu);
    }

    /* Toolbar Menu Option Actions :- */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.option) {
            Intent intent=new Intent(getApplicationContext(), CheckGenerate.class);
            intent.putExtra("name",name);
            intent.putExtra("date", date.getText().toString());
            intent.putExtra("totalPay",total_payroll.getText().toString());
            startActivity(intent);

            /*startActivity(new Intent(PayrollSlipTemp.this, CheckGenerate.class));*/
        } else if (item.getItemId() == R.id.share) {
            //            PDF Generator :-
            PdfGenerator.getBuilder()
                    .setContext(PayrollSlipTemp.this)
                    .fromViewSource()
                    .fromView(findViewById(R.id.sliplayout))
                    /* "fromLayoutXML()" takes array of layout resources.
                     * You can also invoke "fromLayoutXMLList()" method here which takes list of layout resources instead of array. */
                    .setFileName("Test-PDF")
                    /* It is file name */
                    .actionAfterPDFGeneration(PdfGenerator.ActionAfterPDFGeneration.SHARE)
                    .savePDFSharedStorage(xmlToPDFLifecycleObserver)
                    /*If you want to save your pdf in shared storage (where other apps can also see your pdf even after the app is uninstall).
                     * You need to pass an xmt to pdf lifecycle observer by the following method. To get complete overview please see the MainActivity of 'sample' folder */
                    .build(new PdfGeneratorListener() {
                        @Override
                        public void onFailure(FailureResponse failureResponse) {
                            super.onFailure(failureResponse);
                            Log.d("error", failureResponse.getErrorMessage());
                            Toast.makeText(PayrollSlipTemp.this, failureResponse.getErrorMessage(), Toast.LENGTH_LONG).show();
                            /* If pdf is not generated by an error then you will findout the reason behind it
                             * from this FailureResponse. */
                        }

                        @Override
                        public void onStartPDFGeneration() {
                            /*When PDF generation begins to start*/
                        }

                        @Override
                        public void onFinishPDFGeneration() {
                            /*When PDF generation is finished*/
                        }

                        @Override
                        public void showLog(String log) {
                            super.showLog(log);
                            /*It shows logs of events inside the pdf generation process*/
                        }

                        @Override
                        public void onSuccess(SuccessResponse response) {
                            super.onSuccess(response);

                                                    }
                    });
        } else if (item.getItemId() == R.id.download) {
            //            PDF Generator :-
            PdfGenerator.getBuilder()
                    .setContext(PayrollSlipTemp.this)
                    .fromViewSource()
                    .fromView(findViewById(R.id.sliplayout))
                    /* "fromLayoutXML()" takes array of layout resources.
                     * You can also invoke "fromLayoutXMLList()" method here which takes list of layout resources instead of array. */
                    .setFileName("Test-PDF")
                    /* It is file name */
                    .actionAfterPDFGeneration(PdfGenerator.ActionAfterPDFGeneration.NONE)
                    .savePDFSharedStorage(xmlToPDFLifecycleObserver)
                    /*If you want to save your pdf in shared storage (where other apps can also see your pdf even after the app is uninstall).
                     * You need to pass an xmt to pdf lifecycle observer by the following method. To get complete overview please see the MainActivity of 'sample' folder */
                    .build(new PdfGeneratorListener() {
                        @Override
                        public void onFailure(FailureResponse failureResponse) {
                            super.onFailure(failureResponse);
                            Log.d("error", failureResponse.getErrorMessage());
                            Toast.makeText(PayrollSlipTemp.this, failureResponse.getErrorMessage(), Toast.LENGTH_LONG).show();
                            /* If pdf is not generated by an error then you will findout the reason behind it
                             * from this FailureResponse. */
                        }

                        @Override
                        public void onStartPDFGeneration() {
                            /*When PDF generation begins to start*/
                        }

                        @Override
                        public void onFinishPDFGeneration() {
                            /*When PDF generation is finished*/
                        }

                        @Override
                        public void showLog(String log) {
                            super.showLog(log);
                            /*It shows logs of events inside the pdf generation process*/
                        }

                        @Override
                        public void onSuccess(SuccessResponse response) {
                            super.onSuccess(response);
                            ProgressDialog progressDialog =new ProgressDialog(PayrollSlipTemp.this);
                            progressDialog.setMessage("Uploading..");
                            progressDialog.show();

                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            StorageReference storageRef = storage.getReference().child("HistoryPdfs");
                            storageRef.child(response.getFile().getName()).putFile(Uri.fromFile(response.getFile()))
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            taskSnapshot.getStorage().getDownloadUrl().
                                                    addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Uri> task) {
                                                            /*Update Data into the Database :-*/
                                                            Log.d("method","Error");

                                                            progressDialog.hide();
                                                            String generatedFilePath = task.getResult().toString();
                                                            Toast.makeText(PayrollSlipTemp.this, "File Saved Successfully", Toast.LENGTH_SHORT).show();
                                                            String Key = mDatabase.child("Download-Payslips").push().getKey();
                                                            Log.d("key",":"+key);
                                                            /*mDatabase.child("Download-Payslips").child(Key).setValue(generatedFilePath,key);*/

                                                            PaySlipModal approvals = new PaySlipModal(generatedFilePath,name,designation,new Date().toString(),key);

                                                            mDatabase.child("Download-Payslips").child(key).setValue(approvals).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    Toast.makeText(PayrollSlipTemp.this, "Data Saved Successfully!!!!", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(PayrollSlipTemp.this, "Oops! Something Went Wrong.", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                        }
                                                    });
                                        }
                                    });
                        }
                    });
        }
        return false;
    }
    /* Back Option :- */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}