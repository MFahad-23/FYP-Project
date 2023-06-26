package com.example.fyprojectnew.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.fyprojectnew.R;
import com.github.drjacky.imagepicker.ImagePicker;
import com.github.drjacky.imagepicker.constant.ImageProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class LoginActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mauth;
    TextInputEditText gmail, password;
    TextView create_account, forget_password;
    String TAG;
    Timer timer;
    ImageView dialog_dissmiss,dissmiss;
    Dialog errordialog,helpdialog;
    LinearLayout helpcard;
    Animation slide_left;
    CircleImageView circleimage;
    ImageButton imageButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        circleimage=(CircleImageView)findViewById(R.id.circleimage);
        imageButton=(ImageButton) findViewById(R.id.imagecapture);

        gmail = (TextInputEditText) findViewById(R.id.gmail);
        password = (TextInputEditText) findViewById(R.id.password);
        Button login = (Button) findViewById(R.id.login);
        create_account = (TextView) findViewById(R.id.create_account);
        forget_password = (TextView) findViewById(R.id.forget_password);
        mauth = FirebaseAuth.getInstance();
        helpcard =(LinearLayout)findViewById(R.id.helpcard);

        /* Capture Image Set :- */
        ActivityResultLauncher<Intent> launcher=
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),(ActivityResult result)->{
                    if(result.getResultCode()==RESULT_OK){
                        Uri uri=result.getData().getData();
                        circleimage.setImageURI(uri);

                        /* Auto Progress Dialog:- */
                        ProgressDialog pd= new ProgressDialog(LoginActivity.this);
                        pd.setTitle("Uploading...");
                        pd.show();

                       /* For Taking Link & Save it into FireBase Storage & DataBase :- */
                        File file = new File(String.valueOf(uri));
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference().child("userImages");

                        storageRef.child(file.getName()).putFile(uri)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        pd.dismiss();

                                        taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(
                                                new OnCompleteListener<Uri>() {

                                                    @Override
                                                    public void onComplete(@NonNull Task<Uri> task) {

                                                        /*Update Data into the DataBase :-*/
                                                        String generatedFilePath = task.getResult().toString();
                                                        HashMap<String,Object> data= new HashMap<>();
                                                        data.put("profile_pic",generatedFilePath);

                                                        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(data)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        Toast.makeText(LoginActivity.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Log.w(TAG, "Something went wrong.Please try again!", e);
                                                                        Toast.makeText(LoginActivity.this, e.getMessage(),
                                                                                Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        pd.dismiss();
                                    }
                                });

                    }else if(result.getResultCode()== ImagePicker.RESULT_ERROR){
                        // Use ImagePicker.Companion.getError(result.getData()) to show an error
                    }
                });

        /* Image Capture & Set into Demo Image :-*/
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(LoginActivity.this)
                        .crop()
                        .cropOval()
                        .cropSquare()
                        .provider(ImageProvider.BOTH) //Or bothCameraGallery()
                        .createIntentFromDialog(
                                (Function1)(new Function1(){
                            public Object invoke(Object var1){
                                this.invoke((Intent)var1);
                                return Unit.INSTANCE;
                            }

                            public final void invoke(@NotNull Intent it){
                                Intrinsics.checkNotNullParameter(it,"it");
                                launcher.launch(it);
                            }
                        }));
            }
        });

        //Animation Slide_Left :-
        slide_left=AnimationUtils.loadAnimation(LoginActivity.this,R.anim.slide_left);

        /* Create New Account :-*/
        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create_account.startAnimation(slide_left);
                Intent next = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(next);
            }
        });

        /* Foreget Password :-*/
        /*Get email from our Field :-*/
        String emailAddress =gmail.getText().toString();

        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mauth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Email sent.");
                                    Toast toast=Toast.makeText(getApplicationContext(), "Check Your Gmail for Password Reset", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.START,50,50);
                                    toast.show();
                                }
                            }
                        });
            }
        });

        // Login Button:-
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*  Check Credentials :- */
                if (gmail.getText().toString().isEmpty()) {
                    gmail.setError("*Please Complete the Fields*");
                }
                else if (password.getText().toString().isEmpty()) {
                    password.setError("*Please Complete the Field*");
                } else {

                    /*Firebase Authentication :-*/
                    mauth.signInWithEmailAndPassword(gmail.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mauth.getCurrentUser();
                                        Toast.makeText(getApplicationContext(), "Thanks for Login !", Toast.LENGTH_SHORT).show();
                                        timer = new Timer();
                                        timer.schedule(new TimerTask() {
                                            @Override
                                            public void run() {
                                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                            }
                                        }, 5000);
                                        gmail.setText("");
                                        password.setText("");
                                    } else
                                    {
                                        /*Error Dialog :-*/
                                        OpenErrorDialog();
                                    }
                                }
                            });
                }
            }
        });

        /*  Help Dialog :-*/
        helpcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpcard.startAnimation(slide_left);
                OpenHelpDialog();
            }
        });
    }

    /* Error Dialog :-*/
    private void OpenErrorDialog() {
        errordialog = new Dialog(LoginActivity.this);
        errordialog.setContentView(R.layout.custom_error_dialog);
        errordialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        dissmiss =errordialog.findViewById(R.id.dissmiss);

        dissmiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errordialog.dismiss();
            }
        });
        errordialog.show();
    }

    /* Help Dialog :-*/
    private void OpenHelpDialog() {
        helpdialog = new Dialog(LoginActivity.this);
        helpdialog.setContentView(R.layout.help_dialog_box);
        helpdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        dialog_dissmiss =helpdialog.findViewById(R.id.dialog_dissmiss);

        dialog_dissmiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helpdialog.dismiss();
            }
        });
        helpdialog.show();
    }
}