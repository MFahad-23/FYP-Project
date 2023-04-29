package com.example.fyprojectnew;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class QuestionsSection extends AppCompatActivity {
TextView BackOption;
    Animation slide_left;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions_section);
        BackOption=(TextView) findViewById(R.id.BackOption);

        /* Animation Slide Left :- */
        slide_left= AnimationUtils.loadAnimation(QuestionsSection.this,R.anim.slide_left);

       /* Go Back Option :- */
        BackOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackOption.startAnimation(slide_left);
                finish();
            }
        });
    }
}