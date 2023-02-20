package com.example.fyprojectnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HelpPageActivity extends AppCompatActivity {
CardView uploddata,docsupload,newsalarystructure,advancesalary;
TextView morepage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);
        uploddata=(CardView) findViewById(R.id.uploddata);
        docsupload=(CardView) findViewById(R.id.docsupload);
        newsalarystructure=(CardView) findViewById(R.id.newsalarystructure);
        advancesalary=(CardView) findViewById(R.id.advancesalary);
        morepage=(TextView) findViewById(R.id.morepage);

        /*Question Upload Data :-*/
        uploddata.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                    gotourl("https://support.zinghr.com//");
                }

                private void gotourl(String s) {
                    Uri link = Uri.parse(s);
                    startActivity(new Intent(Intent.ACTION_VIEW, link));
            }
        });

        /* Question Documents Upload :-*/
        docsupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gotourl("https://support.docusign.com//");
            }

            private void gotourl(String s) {
                Uri link = Uri.parse(s);
                startActivity(new Intent(Intent.ACTION_VIEW, link));
            }
        });

        /*Question Advance Salary :-*/
        advancesalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotourl("https://www.indeed.com/");
            }
            private void gotourl(String s) {
                Uri link = Uri.parse(s);
                startActivity(new Intent(Intent.ACTION_VIEW, link));
            }
        });

       /* Question New Salary Structure :-*/
        newsalarystructure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotourl("https://www.indeed.com/");
            }
            private void gotourl(String s) {
                Uri link = Uri.parse(s);
                startActivity(new Intent(Intent.ACTION_VIEW, link));
            }
        });

      /*  Answers for Questions more Detail Page :-*/
        morepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HelpPageActivity.this,QuestionsSection.class));
            }
        });
    }
}