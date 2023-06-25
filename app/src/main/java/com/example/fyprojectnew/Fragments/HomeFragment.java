package com.example.fyprojectnew.Fragments;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.example.fyprojectnew.Activities.ApprovedFiles;
import com.example.fyprojectnew.Activities.DepartmentsList;
import com.example.fyprojectnew.Activities.HelpPageActivity;
import com.example.fyprojectnew.Activities.MainActivity;
import com.example.fyprojectnew.Activities.PaySlipsActivity;
import com.example.fyprojectnew.R;
import com.example.fyprojectnew.Models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    ImageSlider slider;
    SlideModel modal;

    CardView DepartmentCard,ApprovalCard,HistorySlipsCard,HelpCard;
    ImageView MenuBar;
    Animation scale_down_animation;
    TextView textView;

    /* Fragment Back Code :- */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        slider=view.findViewById(R.id.image_slider);
        textView=view.findViewById(R.id.marquee);

        DepartmentCard = view.findViewById(R.id.DepartmentCard);
        HelpCard = view.findViewById(R.id.HelpCard);
        ApprovalCard = view.findViewById(R.id.ApprovalCard);
        HistorySlipsCard=view.findViewById(R.id.HistorySlipsCard);
        MenuBar=view.findViewById(R.id.MenuBar);

        List<com.denzcoskun.imageslider.models.SlideModel> modal=new ArrayList<com.denzcoskun.imageslider.models.SlideModel>();

        modal.add(new com.denzcoskun.imageslider.models.SlideModel(R.drawable.uniimages,ScaleTypes.FIT));
        modal.add(new com.denzcoskun.imageslider.models.SlideModel(R.drawable.uniimage1,ScaleTypes.FIT));
        modal.add(new com.denzcoskun.imageslider.models.SlideModel(R.drawable.uniimage2,ScaleTypes.FIT));
        modal.add(new com.denzcoskun.imageslider.models.SlideModel(R.drawable.uniimage3,ScaleTypes.FIT));
        modal.add(new com.denzcoskun.imageslider.models.SlideModel(R.drawable.uniimage4,ScaleTypes.FIT));
        modal.add(new com.denzcoskun.imageslider.models.SlideModel(R.drawable.uniimage5,ScaleTypes.FIT));
        modal.add(new com.denzcoskun.imageslider.models.SlideModel(R.drawable.uniimgae6,ScaleTypes.FIT));
        modal.add(new com.denzcoskun.imageslider.models.SlideModel(R.drawable.uniimgae7,ScaleTypes.FIT));
        modal.add(new com.denzcoskun.imageslider.models.SlideModel(R.drawable.uniimgae8,ScaleTypes.FIT));
        modal.add(new com.denzcoskun.imageslider.models.SlideModel(R.drawable.uniimgae9,ScaleTypes.FIT));
        modal.add(new com.denzcoskun.imageslider.models.SlideModel(R.drawable.uniimgae10,ScaleTypes.FIT));

        slider.setImageList(modal,ScaleTypes.FIT);

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
                Intent next = new Intent(getActivity(), PaySlipsActivity.class);
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

    @Override
    public void onResume() {
        /* Marquee Text in android :- */
        textView.setSelected(true);
        super.onResume();
    }
}