package com.example.fyprojectnew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class ApprovalsGenerate extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approvals_generate);

//        Semister Spinner :-
        MaterialSpinner material_spinner = (MaterialSpinner) findViewById(R.id.material_spinner);
        material_spinner.setItems("Semister", "1st", "2nd", "3rd", "4th", "5th","6th","7th","8th");
        material_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_INDEFINITE).show();
            }
        });

//        Session Spinner :-
        MaterialSpinner spinner=(MaterialSpinner) findViewById(R.id.spinner);
        spinner.setItems("Session","2019", "2020", "2021", "2022", "2023", "2024");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
            }
        });

//        Class Spinner :-
        MaterialSpinner material_spinner2 = (MaterialSpinner) findViewById(R.id.material_spinner2);
        material_spinner2.setItems("Class", "D1", "D2", "B4", "E5", "D6","F4","G3","G4");
        material_spinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
            }
        });
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
}