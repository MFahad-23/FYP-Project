package com.example.fyprojectnew;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.drjacky.imagepicker.ImagePicker;
import com.github.drjacky.imagepicker.constant.ImageProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public class ProfilePage extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    TextView name,location,mail,idendity,phone;
    CircleImageView circleimage;
    FloatingActionButton imagecapture;
    User user;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        circleimage=(CircleImageView)findViewById(R.id.circleimage);
        imagecapture=(FloatingActionButton)findViewById(R.id.imagecapture);
        name=(TextView) findViewById(R.id.name);
        location=(TextView) findViewById(R.id.location);
        mail=(TextView) findViewById(R.id.mail);
        idendity=(TextView) findViewById(R.id.idendity);
        phone=(TextView) findViewById(R.id.phone);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        ActivityResultLauncher<Intent> launcher=
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),(ActivityResult result)->{
                    if(result.getResultCode()==RESULT_OK){
                        Uri uri=result.getData().getData();
                        circleimage.setImageURI(uri);

                        ProgressDialog pd= new ProgressDialog(ProfilePage.this);
                        pd.setTitle("Uploading...");
                        pd.show();

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

                                                        /*Update Data into the Dtaatbase :-*/
                                                        String generatedFilePath = task.getResult().toString();
                                                        HashMap<String,Object> data= new HashMap<>();
                                                        data.put("profile_pic",generatedFilePath);

                                                        mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(data)
                                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void aVoid) {
                                                                        Toast.makeText(ProfilePage.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Log.w(TAG, "Something went wrong.Please try again!", e);
                                                                        Toast.makeText(ProfilePage.this, e.getMessage(),
                                                                                Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                        /*-End-*/
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

                    }else if(result.getResultCode()==ImagePicker.RESULT_ERROR){
                        // Use ImagePicker.Companion.getError(result.getData()) to show an error
                    }
                });

        /*Floating Action Button Function :-*/
        imagecapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(ProfilePage.this)
                        .crop()
                        .cropOval()
                        .maxResultSize(524,524,true)
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

        /*Data push to save into the Database :-*/
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                name.setText(user.username);
                location.setText(user.city);
                mail.setText(user.gmail);
                idendity.setText(user.cnic);
                phone.setText(user.contact);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(postListener);
    }

}