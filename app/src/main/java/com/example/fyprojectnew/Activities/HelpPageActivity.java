package com.example.fyprojectnew.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.fyprojectnew.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HelpPageActivity extends AppCompatActivity {
CardView UploadData,DocsUpload,NewSalaryStructure,AdvanceSalary;
TextView MorePage,System_Purpose,Interface,About_Data,Updates;
    Animation slide_left;
    FloatingActionButton Back_Button;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("About Systems");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));

        UploadData=(CardView) findViewById(R.id.UploadData);
        DocsUpload=(CardView) findViewById(R.id.DocsUpload);
        NewSalaryStructure=(CardView) findViewById(R.id.NewSalaryStructure);
        AdvanceSalary=(CardView) findViewById(R.id.AdvanceSalary);
        MorePage=(TextView) findViewById(R.id.MorePage);
        System_Purpose=(TextView) findViewById(R.id.System_Purpose);
        Interface=(TextView) findViewById(R.id.Interface);
        /*Back_Button=(FloatingActionButton) findViewById(R.id.Back_Button);*/
        About_Data=(TextView) findViewById(R.id.About_Data);
        Updates=(TextView) findViewById(R.id.Updates);


      /* *//* Back_Button :- *//*
        Back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/

        /* Animation Slide Left :- */
        slide_left= AnimationUtils.loadAnimation(HelpPageActivity.this,R.anim.slide_left);

        /* Text Link :- */
        System_Purpose.setMovementMethod(LinkMovementMethod.getInstance());
        Interface.setMovementMethod(LinkMovementMethod.getInstance());
        About_Data.setMovementMethod(LinkMovementMethod.getInstance());
        Updates.setMovementMethod(LinkMovementMethod.getInstance());

        /* Question1 Upload Data :- */
        UploadData.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                gotourl("https://support.zinghr.com//");
                }

                private void gotourl(String s) {
                    Uri link = Uri.parse(s);
                    startActivity(new Intent(Intent.ACTION_VIEW, link));
            }
        });

        /* Question2 Documents Upload :- */
        DocsUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotourl("https://support.docusign.com//");
            }

            private void gotourl(String s) {
                Uri link = Uri.parse(s);
                startActivity(new Intent(Intent.ACTION_VIEW, link));
            }
        });

        /*Question3 Advance Salary :- */
        AdvanceSalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotourl("https://www.indeed.com/");
            }
            private void gotourl(String s) {
                Uri link = Uri.parse(s);
                startActivity(new Intent(Intent.ACTION_VIEW, link));
            }
        });

       /* Question4 New Salary Structure :- */
        NewSalaryStructure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotourl("https://www.indeed.com/");
            }
            private void gotourl(String s) {
                Uri link = Uri.parse(s);
                startActivity(new Intent(Intent.ACTION_VIEW, link));
            }
        });

        /* Answers for Questions more Detail Activity :- */
        MorePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MorePage.startAnimation(slide_left);
                startActivity(new Intent(HelpPageActivity.this, QuestionsSection.class));
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}