package com.example.fyprojectnew.Adapters;

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

import com.example.fyprojectnew.Models.DesignationModel;
import com.example.fyprojectnew.Activities.EmployeeList;
import com.example.fyprojectnew.R;

import java.util.List;

public class DesignationViewAdpter extends RecyclerView.Adapter<DesignationViewAdpter.ViewHolder> {

     private  List<DesignationModel> designation_listview;
     Context context;
     public DesignationViewAdpter(Context context,List<DesignationModel> designation_listview){
         this.context=context;
         this.designation_listview=designation_listview;
     }
    @NonNull
    @Override
    public DesignationViewAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.designation_view_design,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull DesignationViewAdpter.ViewHolder holder, int position) {
String  empimage=designation_listview.get(position).getImage();
String name=designation_listview.get(position).getEmployeedesignation();
holder.setdata(empimage,name);
    }
    @Override
    public int getItemCount() {
        return designation_listview.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         private ImageView image;
         private TextView employeedesignation;
         private RelativeLayout designationlayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            employeedesignation=itemView.findViewById(R.id.employeedesignation);
            designationlayout=itemView.findViewById(R.id.designationlayout);
        }
        public void setdata(String empimage, String name) {
           //image.setImageResource(Integer.parseInt(empimage));
            employeedesignation.setText(name);

           /* Employee List :- */
            designationlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, EmployeeList.class));
                }
            });
        }
    }
}
