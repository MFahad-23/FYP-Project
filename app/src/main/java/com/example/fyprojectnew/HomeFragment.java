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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class HomeFragment extends Fragment {

    CardView DepartmentCard,ApprovalCard,HistorySlipsCard,HelpCard;
    LinearLayout MenuBar;
    Animation scale_down_animation;

    /* Fragment Back Code :- */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DepartmentCard = view.findViewById(R.id.DepartmentCard);
        HelpCard = view.findViewById(R.id.HelpCard);
        ApprovalCard = view.findViewById(R.id.ApprovalCard);
        HistorySlipsCard=view.findViewById(R.id.HistorySlipsCard);
        MenuBar=view.findViewById(R.id.MenuBar);

        /* Animation Slide Left :- */
        scale_down_animation= AnimationUtils.loadAnimation(getActivity(),R.anim.scale_down_animation);


        /* Department Activity :- */
        DepartmentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getActivity(), DepartmentsList.class);
                startActivity(next);
            }
        });

        /* Help Activity :- */
        HelpCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getActivity(), HelpPageActivity.class);
                startActivity(next);
            }
        });

        /* Approvals Activity :- */
        ApprovalCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getActivity(), ApprovedFiles.class);
                startActivity(next);
            }
        });

      /* HistorySlips Activity :- */
        HistorySlipsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getActivity(),PaySlipsActivity.class);
                startActivity(next);
            }
        });

         /* Open Side MenuBar :- */
        MenuBar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                MenuBar.startAnimation(scale_down_animation);
                ((MainActivity)requireActivity()).openDrawer();
                Log.d("Prob","Solution");
            }
        });
    }
}