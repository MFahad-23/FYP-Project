package com.example.fyprojectnew.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.fyprojectnew.R;

public class QuestionsSection extends AppCompatActivity {
TextView BackOption;
    Animation slide_left;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_section);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
        getSupportActionBar().setTitle("Question Page");
       /* BackOption=(TextView) findViewById(R.id.BackOption);*/

        /* Animation Slide Left :- */
        slide_left= AnimationUtils.loadAnimation(QuestionsSection.this,R.anim.slide_left);

  /*     *//* Go Back Option :- *//*
        BackOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackOption.startAnimation(slide_left);
                finish();
            }
        });*/
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}