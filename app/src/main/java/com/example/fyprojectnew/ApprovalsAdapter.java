package com.example.fyprojectnew;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ApprovalsAdapter extends RecyclerView.Adapter<ApprovalsAdapter.ViewHolder> {

    private List<ApprovalsModel>aprovedlist;
    Context context;
    public ApprovalsAdapter(List<ApprovalsModel> aprovedlist, Context context) {
        this.aprovedlist = aprovedlist;
        this.context = context;
    }

    @NonNull
    @Override
    public ApprovalsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.approved_employees_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApprovalsAdapter.ViewHolder holder, int position) {
    String employeeimage=aprovedlist.get(position).employee_image;
    String name=aprovedlist.get(position).employee_name;
    String designation=aprovedlist.get(position).employee_designation;
    String date=aprovedlist.get(position).datepicker;
        holder.setdata(employeeimage,name,designation,date);
    }

    @Override
    public int getItemCount() {
        return aprovedlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView employeeimage;
        private TextView employeename;
        private TextView employee_designation;
        private TextView spinner_datepicker;
        private RelativeLayout employeelist;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeimage=itemView.findViewById(R.id.employeeimage);
            employeename=itemView.findViewById(R.id.employeename);
            employee_designation=itemView.findViewById(R.id.employee_designation);
            spinner_datepicker=itemView.findViewById(R.id.spinner_datepicker);
            employeelist=itemView.findViewById(R.id.employeelist);
        }

        public void setdata(String employeeimage, String name, String designation,String date) {
            employeename.setText(name);
            employee_designation.setText(designation);
            spinner_datepicker.setText(date);
            employeelist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context,PayrollsPageActivity.class));
                }
            });
        }
    }
}
