package com.example.fyprojectnew;

import android.app.Dialog;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class EmployeListAdpter extends RecyclerView.Adapter<EmployeListAdpter.ViewHolder> {
    //Data Delete From Database :-
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
        String key=employee_items_view.get(position).key;
        String image=employee_items_view.get(position).getEmployeeimage();
        String delimage=employee_items_view.get(position).getDelimage();
        String name=employee_items_view.get(position).getEmployeename();
        String id=employee_items_view.get(position).getEmployeeid();
        holder.setdata(key,image,delimage,name,id);
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
        private ImageView deleteimage;
        private Dialog deldialog;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeimage=itemView.findViewById(R.id.employeeimage);
            deleteimage=itemView.findViewById(R.id.deleteimage);
            employeename=itemView.findViewById(R.id.employeename);
            employeeid=itemView.findViewById(R.id.employeeid);
            employeelist=itemView.findViewById(R.id.employeelist);
        }
        public void setdata( String key,String image, String name, String id, String delimage) {
            //employeeimage.setImageResource(image);
            employeename.setText(name);
            employeeid.setText(id);

            employeelist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context,PayrollSlipTemp.class));
                }
            });

            deleteimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deldialog=new Dialog(context);
                    deldialog.setContentView(R.layout.file_delete_dialog);
                    deldialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_background);
                    TextView cancel,ok;
                    cancel=deldialog.findViewById(R.id.cancel);
                    ok=deldialog.findViewById(R.id.ok);
                    deldialog.show();

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            deldialog.dismiss();
                        }
                    });

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FirebaseDatabase.getInstance().getReference().child("Employees").child(key).removeValue();
                            deldialog.dismiss();
                        }
                    });
                }
            });
        }
    }
}
