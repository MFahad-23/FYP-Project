package com.example.fyprojectnew;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

public class SettingFragment extends Fragment {
    CardView calculate_payrolls,approvals_calculation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calculate_payrolls=view.findViewById(R.id.calculate_payrolls);
        approvals_calculation=view.findViewById(R.id.approvals_calculation);

        /*Payroll Setting Page :-*/
        calculate_payrolls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),CalculationPageActivity.class));
            }
        });

        approvals_calculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),ApprovalsGenerate.class));
            }
        });
    }
}