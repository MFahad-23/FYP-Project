package com.example.fyprojectnew;

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

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MiscallenousListAdapter extends RecyclerView.Adapter<MiscallenousListAdapter.ViewHolder> implements Filterable {

    List<MiscallenousModel>miscellenouslist;
    List<MiscallenousModel>getMiscellenouslistFull;
    Context context;

    public MiscallenousListAdapter(Context context,List<MiscallenousModel> miscellenouslist) {
        this.context = context;
        this.getMiscellenouslistFull = miscellenouslist;
        this.miscellenouslist=new ArrayList<>(getMiscellenouslistFull);
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
String key=miscellenouslist.get(position).Key;
        holder.setdata(name,designation,image,key);
    }

    @Override
    public int getItemCount() {
        return miscellenouslist.size() ;
    }

    @Override
    public Filter getFilter() {
        return EmployeeFilter;
    }

    private final Filter EmployeeFilter=new Filter() {

       /* Activity Search Option :- */
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<MiscallenousModel> miscellenouslist=new ArrayList<>();
            if (charSequence ==null || charSequence.length() == 0){
             miscellenouslist.addAll(getMiscellenouslistFull);
            }else
            {
                String FiltersPattern=charSequence.toString().toLowerCase().trim();
                for (MiscallenousModel employee : getMiscellenouslistFull){
                    if (employee.getEmployeename().toLowerCase().contains(FiltersPattern)){
                        miscellenouslist.add(employee);
                    }
                }
            }
            FilterResults results =new FilterResults();
            results.values=miscellenouslist;
            results.count=miscellenouslist.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        miscellenouslist.clear();
        miscellenouslist.addAll((ArrayList)filterResults.values);
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
            employeedesignation=itemView.findViewById(R.id.employeedesignation);
            employeelist=itemView.findViewById(R.id.employeelist);
            deleteimage=itemView.findViewById(R.id.deleteimage);
        }

        public void setdata(String name, String designation, String image,String key) {
            employeename.setText(name);
            employeedesignation.setText(designation);

           /* Pay Slip Template Activity :- */
            employeelist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 context.startActivity(new Intent(context,PayrollSlipTemp.class));
                }
            });

           /* Employee Delete :- */
            deleteimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OpenDialog(key);
                }
            });
        }
    }

    /* Employee Delete Dialog :- */
    private void OpenDialog(String key) {
        Dialog deldialog=new Dialog(context);
        deldialog.setContentView(R.layout.file_delete_dialog);
        deldialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_background);
        deldialog.show();
        TextView cancel,ok;
        cancel=deldialog.findViewById(R.id.cancel);
        ok=deldialog.findViewById(R.id.ok);

        /* Dialog Cancel Button  :- */
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deldialog.dismiss();
            }
        });
        /* Dialog Ok Button :- */
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference().child("Miscallenous").child(key).removeValue();
                deldialog.dismiss();
            }
        });
    }
}