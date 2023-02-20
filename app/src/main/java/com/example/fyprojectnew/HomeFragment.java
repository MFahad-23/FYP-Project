package com.example.fyprojectnew;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class HomeFragment extends Fragment {

    CardView departcard,payrollscard,slipscard,helpcard;
    LinearLayout meunebar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        departcard = view.findViewById(R.id.departcard);
        helpcard = view.findViewById(R.id.helpcard);
        payrollscard = view.findViewById(R.id.payrollscard);
        slipscard=view.findViewById(R.id.slipscard);
        meunebar=view.findViewById(R.id.meunebar);



        departcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getActivity(), DepartmentsList.class);
                startActivity(next);
            }
        });
        helpcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getActivity(), HelpPageActivity.class);
                startActivity(next);
            }
        });
        payrollscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getActivity(), PayrollsPageActivity.class);
                startActivity(next);
            }
        });
        slipscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getActivity(),PaySlipsActivity.class);
                startActivity(next);
            }
        });
        meunebar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ((MainActivity)requireActivity()).openDrawer();
                Log.d("Prob","Solution");
            }
        });
}
}  