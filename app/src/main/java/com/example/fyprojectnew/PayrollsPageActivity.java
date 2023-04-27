package com.example.fyprojectnew;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.gkemon.XMLtoPDF.PdfGenerator;
import com.gkemon.XMLtoPDF.PdfGeneratorListener;
import com.gkemon.XMLtoPDF.model.FailureResponse;
import com.gkemon.XMLtoPDF.model.SuccessResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import sharelayoutbyamit.example.sharelibrary.ShareLayout;

public class PayrollsPageActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    ConstraintLayout approvedfile;
    private PdfGenerator.XmlToPDFLifecycleObserver xmlToPDFLifecycleObserver;
    TextView date1,department_name,programme1,sessionno,date2,department,programme2,teacher_name,subject_name,
            subject,section,semister,qualification;
    ApprovalsModel model;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payrolls_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Approved File");
        date1 = (TextView) findViewById(R.id.date1);
        department_name = (TextView) findViewById(R.id.department_name);
        programme1 = (TextView) findViewById(R.id.programme1);
        sessionno = (TextView) findViewById(R.id.sessionno);
        date2 = (TextView) findViewById(R.id.date2);
        department = (TextView) findViewById(R.id.department);
        programme2 = (TextView) findViewById(R.id.programme2);
        teacher_name = (TextView) findViewById(R.id.teacher_name);
        subject_name = (TextView) findViewById(R.id.subject_name);
        subject = (TextView) findViewById(R.id.subject);
        section = (TextView) findViewById(R.id.section);
        semister = (TextView) findViewById(R.id.semister);
        qualification = (TextView) findViewById(R.id.qualification);
        approvedfile=(ConstraintLayout)findViewById(R.id.approvedfile);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        model = (ApprovalsModel) getIntent().getSerializableExtra("data");
        Log.d("break",model.english_date);
        date1.setText(model.english_date);
        department_name.setText(model.department);
        programme1.setText(model.section);
        sessionno.setText(model.session_spinner);
        date2.setText(model.english_date);
        department.setText(model.department);
        programme2.setText(model.section);
        teacher_name.setText(model.employee_name);
        subject_name.setText(model.teaching_subject);
        subject.setText(model.subject);
        section.setText(model.session);
        semister.setText(model.semister_spinner);
        qualification.setText(model.employee_qualification);

        xmlToPDFLifecycleObserver = new PdfGenerator.XmlToPDFLifecycleObserver(this);
        getLifecycle().addObserver(xmlToPDFLifecycleObserver);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.approvals_menu, menu);
        MenuItem menuItem2 = menu.findItem(R.id.download);
        MenuItem menuItem1 = menu.findItem(R.id.share);
        return super.onCreateOptionsMenu(menu);
    }

    @Override

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.share){
            ShareLayout.simpleLayoutShare(PayrollsPageActivity.this,approvedfile,"");
        }
        else if (item.getItemId()==R.id.download){
            Log.d("Msg", "Error");
            PdfGenerator.getBuilder()
                    .setContext(PayrollsPageActivity.this)
                    .fromLayoutXMLSource()
                    .fromLayoutXML(R.layout.activity_payrolls_page)
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
                            Log.d("error",failureResponse.getErrorMessage());
                            Toast.makeText(PayrollsPageActivity.this,failureResponse.getErrorMessage(),Toast.LENGTH_LONG).show();
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
                            Toast.makeText(PayrollsPageActivity.this,"Success",Toast.LENGTH_LONG).show();
                            /* If PDF is generated successfully then you will find SuccessResponse
                             * which holds the PdfDocument,File and path (where generated pdf is stored)*/

                        }
                    });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}