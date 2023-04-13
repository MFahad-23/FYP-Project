package com.example.fyprojectnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import sharelayoutbyamit.example.sharelibrary.ShareLayout;

public class CheckGenerate extends AppCompatActivity {
ConstraintLayout cheque;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_generate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cheque Page");
        cheque=(ConstraintLayout) findViewById(R.id.cheque);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cheque_menue_options,menu);
        MenuItem menuItem1=menu.findItem(R.id.share);
        MenuItem menuItem2=menu.findItem(R.id.download);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()== R.id.share){
            ShareLayout.simpleLayoutShare(CheckGenerate.this,cheque,"");
        }
        else if(item.getItemId() == R.id.download ){

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}