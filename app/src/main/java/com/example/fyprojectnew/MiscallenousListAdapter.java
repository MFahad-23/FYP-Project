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

public class MiscallenousListAdapter extends RecyclerView.Adapter<MiscallenousListAdapter.ViewHolder> {

    List<MiscallenousModel>miscellenouslist;
    Context context;
    public MiscallenousListAdapter(Context context,List<MiscallenousModel> miscellenouslist) {
        this.context = context;
        this.miscellenouslist = miscellenouslist;
    }


    @NonNull
    @Override
    public MiscallenousListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.miscalenous_staff_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MiscallenousListAdapter.ViewHolder holder, int position) {
String name= miscellenouslist.get(position).getEmployeename();
String designation= miscellenouslist.get(position).getEmployeedesignation();
String image= miscellenouslist.get(position).getEmployeeimage();
        holder.setdata(name,designation,image);
    }

    @Override
    public int getItemCount() {
        return miscellenouslist.size() ;
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
