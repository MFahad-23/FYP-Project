package com.example.fyprojectnew.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.fyprojectnew.Models.ApprovalsModel;
import com.example.fyprojectnew.R;
import com.github.drjacky.imagepicker.ImagePicker;
import com.github.drjacky.imagepicker.constant.ImageProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class ApprovalsGenerate extends AppCompatActivity implements com.tsongkha.spinnerdatepicker.
        DatePickerDialog.OnDateSetListener {
    private DatabaseReference mDatabase;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    final Calendar myCalendar = Calendar.getInstance();
    ApprovalsModel approvalsModel;
    CircleImageView employee_image;
    ImageView imagecapture;
    Uri final_uri;
    EditText datepicker, employee_name, employee_designation, employee_qualification,
            department, teaching_subject,english_date;
    MaterialSpinner session_spinner, semister_spinner, class_spinner;
    RadioGroup section,session,subject;
    String section_value="";
    String session_value="";
    String class_value="";

    Button submit_button;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approvals_generate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Generate Approval Slips");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));

        employee_image = (CircleImageView) findViewById(R.id.employee_image);
        imagecapture = (ImageView) findViewById(R.id.imagecapture);
        department = (EditText) findViewById(R.id.department);
        employee_name = (EditText) findViewById(R.id.employee_name);
        english_date = (EditText) findViewById(R.id.english_date);
        teaching_subject = (EditText) findViewById(R.id.teaching_subject);
        employee_qualification = (EditText) findViewById(R.id.employee_qualification);
        employee_designation = (EditText) findViewById(R.id.employee_designation);
        datepicker = (EditText) findViewById(R.id.datepicker);

        section = (RadioGroup) findViewById(R.id.section);
        session = (RadioGroup) findViewById(R.id.session1);
        subject = (RadioGroup) findViewById(R.id.subject);

        submit_button = (Button) findViewById(R.id.submit_button);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        //Section RadioGroup :-
        section.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton selected = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                section_value=selected.getText().toString();
            }
        });
        //Section RadioGroup :-
        session.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton selected = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                session_value=selected.getText().toString();
            }
        });
        //Subject RadioGroup :-
        subject.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton selected = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                class_value=selected.getText().toString();
            }
        });

//        Semister Spinner :-
        semister_spinner = (MaterialSpinner) findViewById(R.id.semister_spinner);
        semister_spinner.setItems("Semister", "1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th");
        semister_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_INDEFINITE).show();
            }
        });

//        Session Spinner :-
        session_spinner = (MaterialSpinner) findViewById(R.id.session_spinner);
        session_spinner.setItems("Session", "Fall-2018", "Summer-2018", "Fall-2019", "Summer-2019", "Fall-2020", "Summer-2020", "Fall-2021", "Summer-2021", "Fall-2022", "Summer-2022", "Fall-2023", "Summer-2023");
        session_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
            }
        });

//        Class Spinner :-
        class_spinner = (MaterialSpinner) findViewById(R.id.class_spinner);
        class_spinner.setItems("Class", "D1", "D2", "B4", "E5", "D6", "F4", "G3", "G4");
        class_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
            }
        });

//        Date Picker :-
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

//        English Datepicker Implemetntaion :-
        english_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SpinnerDatePickerDialogBuilder()
                        .context(ApprovalsGenerate.this)
                        .callback(ApprovalsGenerate.this)
                        .spinnerTheme(R.style.NumberPickerStyle)
                        .showTitle(true)
                        .showDaySpinner(true)
                        .defaultDate(2023, 0, 1)
                        .maxDate(2050, 0, 1)
                        .minDate(1990, 0, 1)
                        .build()
                        .show();
            }
        });

//        Simple Datepicker Implementaion :-
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("ky", "Error");
                new DatePickerDialog(ApprovalsGenerate.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //        Image Capturing :-
        ActivityResultLauncher<Intent> launcher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                    if (result.getResultCode() == RESULT_OK) {
                        final_uri = result.getData().getData();
                        employee_image.setImageURI(final_uri);

                    } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                        // Use ImagePicker.Companion.getError(result.getData()) to show an error
                    }
                });

        imagecapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(ApprovalsGenerate.this)
                        .crop()
                        .cropSquare()
                        .maxResultSize(524, 524, true)
                        .provider(ImageProvider.BOTH) //Or bothCameraGallery()
                        .createIntentFromDialog(
                                (Function1) (new Function1() {
                                    public Object invoke(Object var1) {
                                        this.invoke((Intent) var1);
                                        return Unit.INSTANCE;
                                    }
                                    public final void invoke(@NotNull Intent it) {
                                        Intrinsics.checkNotNullParameter(it, "it");
                                        launcher.launch(it);
                                    }
                                }));
            }
        });

//Data Push into FireBase Database :-
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//            Progress dialog  Auto :-
                ProgressDialog pd = new ProgressDialog(ApprovalsGenerate.this);
                pd.setTitle("Uploading Image to the System.Please Wait for a Second...");
                pd.show();

                File file = new File(String.valueOf(final_uri));
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReference().child("EmployeesApprovals");

                storageRef.child(file.getName()).putFile(final_uri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                pd.dismiss();

                                taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(
                                        new OnCompleteListener<Uri>() {

                                            @Override
                                            public void onComplete(@NonNull Task<Uri> task) {
                                                /*Update Data into the Dtaatbase :-*/
                                                String generatedFilePath = task.getResult().toString();

                                                                Toast.makeText(ApprovalsGenerate.this, "Image Saved Successfully", Toast.LENGTH_SHORT).show();
                                                String key = mDatabase.child("Employee Approvals").push().getKey();
                                                ApprovalsModel approvals=new ApprovalsModel(generatedFilePath,employee_name.getText().toString(),
                                                                        employee_designation.getText().toString(),employee_qualification.getText().toString(),
                                                                        department.getText().toString(),teaching_subject.getText().toString(),datepicker.getText().toString(),
                                                                        session_spinner.getText().toString(),semister_spinner.getText().toString(),class_spinner.getText().toString(),
                                                                        section_value,session_value,class_value,key,english_date.getText().toString());

                                                 mDatabase.child("Employee Approvals").child(key).setValue(approvals).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void unused) {
                                                                    employee_name.setText("");
                                                                    employee_designation.setText("");
                                                                    employee_qualification.setText("");
                                                                    teaching_subject.setText("");
                                                                    datepicker.setText("");
                                                                    department.setText("");
                                                                    english_date.setText("");
                                                                    Toast.makeText(ApprovalsGenerate.this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                                                                    startActivity(new Intent(ApprovalsGenerate.this, ApprovedFiles.class));
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(ApprovalsGenerate.this, "Oops! Something Went Wrong.", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });

                                        }
                            });
                        }
            });
            }
        });
    }
    private void updateLabel() {
        Log.d("on", "Msg");
        String myFormat = "MM/dd/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        datepicker.setText(dateFormat.format(myCalendar.getTime()));
    }

    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, monthOfYear);
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateLabelA();
    }
    private void updateLabelA() {
        Log.d("on", "Msg");
        String myFormat = "MMM/dd/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        english_date.setText(dateFormat.format(myCalendar.getTime()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
    /*Session Spinner Implementation :-*/
//        spinner=(MaterialSpinner) findViewById(R.id.spinner);
//
//        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.session, me.ibrahimsn.lib.R.layout.support_simple_spinner_dropdown_item);
//        adapter.setDropDownViewResource(me.ibrahimsn.lib.R.layout.support_simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(this);
//    }
//
//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        String text=adapterView.getItemAtPosition(i).toString();
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//
//    }