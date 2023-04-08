package com.example.fyprojectnew;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        String key=aprovedlist.get(position).key;
    String image=aprovedlist.get(position).employee_image;
    String name=aprovedlist.get(position).employee_name;
    String designation=aprovedlist.get(position).employee_designation;
    String date=aprovedlist.get(position).datepicker;
        holder.setdata(key,image,name,designation,date);
    }

    @Override
    public int getItemCount() {
        return aprovedlist.size();
    }

        public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView employeeimage;
        private ImageView deleteimage;
        private TextView employeename;
        private TextView employee_designation;
        private TextView spinner_datepicker;
        private RelativeLayout employeelist;
        private Dialog deldialog;
        private Dialog imagedialog;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeimage=itemView.findViewById(R.id.employeeimage);
            deleteimage=itemView.findViewById(R.id.deleteimage);
            employeename=itemView.findViewById(R.id.employeename);
            employee_designation=itemView.findViewById(R.id.employee_designation);
            spinner_datepicker=itemView.findViewById(R.id.spinner_datepicker);
            employeelist=itemView.findViewById(R.id.employeelist);
        }

        public void setdata(String key,String image, String name, String designation,String date) {
            if(image!=null) {
                Glide.with(context).load(image).into(employeeimage);
            }
            employeename.setText(name);
            employee_designation.setText(designation);
            spinner_datepicker.setText(date);

            employeelist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context,PayrollsPageActivity.class));
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
                            FirebaseDatabase.getInstance().getReference().child("Employee Approvals").child(key).removeValue();
                            deldialog.dismiss();
                        }
                    });
                }
            });

            employeeimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("this","chk");
                    imagedialog=new Dialog(context);
                    imagedialog.setContentView(R.layout.imagedialog);
                    imagedialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_background);
                    ImageView imageview;
                    imageview=imagedialog.findViewById(R.id.imageview);
                    imagedialog.show();
//                    if(image!=null) {
//                        Glide.with(context).load(image).into(imageview);
//                    }
                }
            });
        }
    }
}

