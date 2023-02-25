package com.example.fyprojectnew;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdministrtaionAdapter extends RecyclerView.Adapter<AdministrtaionAdapter.ViewHolder> {

    List<AdministrationModel> adminstafflist;
    Context context;
    public AdministrtaionAdapter(Context context,List<AdministrationModel> adminstafflist) {
        this.context = context;
        this.adminstafflist = adminstafflist;
    }
    @NonNull
    @Override
    public AdministrtaionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adminstaff_design,parent,false);
        return new AdministrtaionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdministrtaionAdapter.ViewHolder holder, int position) {
        String name= adminstafflist.get(position).getEmployeename();
        String designation= adminstafflist.get(position).getEmployeedesignation();
        String image= adminstafflist.get(position).getEmployeeimage();
        holder.setdata(name,designation,image);
    }

    @Override
    public int getItemCount() {
        return adminstafflist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView employeeimage;
        private TextView employeename;
        private TextView employeedesignation;
        private RelativeLayout employeelist;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeimage=itemView.findViewById(R.id.employeeimage);
            employeename=itemView.findViewById(R.id.employeename);
            employeedesignation=itemView.findViewById(R.id.employeedesignation);
            employeelist=itemView.findViewById(R.id.employeelist);
        }

        public void setdata(String name, String designation, String image) {
            employeename.setText(name);
            employeedesignation.setText(designation);

            employeelist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context,PayrollSlipTemp.class));
                }
            });
        }
    }
}
