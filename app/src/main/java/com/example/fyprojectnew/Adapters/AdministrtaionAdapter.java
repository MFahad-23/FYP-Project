package com.example.fyprojectnew.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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

import com.example.fyprojectnew.Activities.PayrollSlipTemp;
import com.example.fyprojectnew.Models.AdministrationModel;
import com.example.fyprojectnew.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AdministrtaionAdapter extends RecyclerView.Adapter<AdministrtaionAdapter.ViewHolder> implements Filterable {

    List<AdministrationModel> adminstafflist;
    List<AdministrationModel>adminstafflistFull;
    Context context;
    public AdministrtaionAdapter(Context context,List<AdministrationModel> adminstafflist) {
        this.context = context;
        this.adminstafflistFull = adminstafflist;
        this.adminstafflist=new ArrayList<>(adminstafflistFull);
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
        String key=adminstafflist.get(position).key;
        holder.setdata(name,designation,image,key);
    }

    @Override
    public int getItemCount() {
        return adminstafflist.size();
    }
    //SearchView :-
    @Override
    public Filter getFilter() {
        return Employeefilter;
    }

    private final Filter Employeefilter =new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<AdministrationModel> adminstafflist = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                adminstafflist.addAll(adminstafflistFull);
            } else {
                String FilterPatterns = charSequence.toString().toLowerCase().trim();
                for (AdministrationModel employee : adminstafflistFull) {
                    if (employee.getEmployeename().toLowerCase().contains(FilterPatterns))
                        adminstafflist.add(employee);
                }
            }
            FilterResults results = new FilterResults();
            results.values = adminstafflist;
            results.count = adminstafflist.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            adminstafflist.clear();
            adminstafflist.addAll((ArrayList)filterResults.values);
            notifyDataSetChanged();
        }
    };
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView employeeimage;
        private TextView employeename;
        private TextView employeedesignation;
        private RelativeLayout employeelist;
        private ImageView deleteimage;
        private Dialog deldialog;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeimage=itemView.findViewById(R.id.employeeimage);
            employeename=itemView.findViewById(R.id.employeename);
            deleteimage=itemView.findViewById(R.id.deleteimage);
            employeedesignation=itemView.findViewById(R.id.employeedesignation);
            employeelist=itemView.findViewById(R.id.employeelist);
        }
        public void setdata(String name, String designation, String image,String key) {
            employeename.setText(name);
            employeedesignation.setText(designation);

           /* Payroll Slips Activity :- */
            employeelist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, PayrollSlipTemp.class);
                    intent.putExtra("name",name);
                    intent.putExtra("designation",designation);
                    intent.putExtra("user_id",key);
                    context.startActivity(intent);
                }
            });

           /* Delete Employee from List as well as From FireBase DataBase :- */
            deleteimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  deldialog=new Dialog(context);
                    deldialog.setContentView(R.layout.file_delete_dialog);
                    deldialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_background);
                    deldialog.show();
                    TextView cancel,ok;
                    cancel=deldialog.findViewById(R.id.cancel);
                    ok=deldialog.findViewById(R.id.ok);

                    /* Dialog Cancel Option :- */
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            deldialog.dismiss();
                        }
                    });

                    /* Dialog Ok Option :- */
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FirebaseDatabase.getInstance().getReference().child("Administration").child(key).removeValue();
                            deldialog.dismiss();
                        }
                    });
                }
            });
        }
    }
}
