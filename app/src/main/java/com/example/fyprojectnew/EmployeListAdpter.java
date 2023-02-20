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

public class EmployeListAdpter extends RecyclerView.Adapter<EmployeListAdpter.ViewHolder> {

    private List<EmployeeModel> employee_items_view;
    Context context;
    public EmployeListAdpter(Context ctx, List<EmployeeModel> employee_items_view){
        this.context=ctx;
        this.employee_items_view=employee_items_view;
    }

    @NonNull
    @Override
    public EmployeListAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_list_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeListAdpter.ViewHolder holder, int position) {
        String image=employee_items_view.get(position).getEmployeeimage();
        String name=employee_items_view.get(position).getEmployeename();
        String id=employee_items_view.get(position).getEmployeeid();
        holder.setdata(image,name,id);
    }

    @Override
    public int getItemCount() {
        return employee_items_view.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView employeeimage;
        private TextView employeename;
        private TextView employeeid;
        private RelativeLayout employeelist;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeimage=itemView.findViewById(R.id.employeeimage);
            employeename=itemView.findViewById(R.id.employeename);
            employeeid=itemView.findViewById(R.id.employeeid);
            employeelist=itemView.findViewById(R.id.employeelist);
        }
        public void setdata(String image, String name, String id) {
            //employeeimage.setImageResource(image);
            employeename.setText(name);
            employeeid.setText(id);

            employeelist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context,PayrollSlipTemp.class));
                }
            });
        }
    }
}
