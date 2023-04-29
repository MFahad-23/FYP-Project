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

public class SettingFragment extends Fragment {
    CardView Calculate_Payrolls_Card,Approvals_Calculation_Card;

    /* Fragment Back :- */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Calculate_Payrolls_Card=view.findViewById(R.id.Calculate_Payrolls_Card);
        Approvals_Calculation_Card=view.findViewById(R.id.Approvals_Calculation_Card);

        /* Pay Calculation Activity :- */
        Calculate_Payrolls_Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),CalculationPageActivity.class));
            }
        });

        /* Approvals Generate Activity :- */
        Approvals_Calculation_Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),ApprovalsGenerate.class));
            }
        });
    }
}