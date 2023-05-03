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
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

public class EmployeListAdpter extends RecyclerView.Adapter<EmployeListAdpter.ViewHolder> implements Filterable {
    /*private List<ClaculationModel>calculationmodal;*/
    private List<EmployeeModel> employee_items_view;
    List<EmployeeModel>employee_items_viewFull;
    Context context;
    public EmployeListAdpter(Context context, List<EmployeeModel> employee_items_view){
        this.context=context;
        this.employee_items_viewFull=employee_items_view;
        this.employee_items_view=new ArrayList<>(employee_items_viewFull);
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
        String designation=employee_items_view.get(position).getEmployeedesignation();
        holder.setdata(key,image,delimage,name,designation);
    }

    @Override
    public int getItemCount() {
        return employee_items_view.size();
    }

    @Override
    public Filter getFilter() {
        return EmployeeFilter;
    }

   /* Activity Search Option :- */
    private final Filter EmployeeFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<EmployeeModel> employee_items_view = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                employee_items_view.addAll(employee_items_viewFull);
            } else {
                String FilterPatterns = charSequence.toString().toLowerCase().trim();
                for (EmployeeModel employee : employee_items_viewFull) {
                    if (employee.getEmployeename().toLowerCase().contains(FilterPatterns))
                        employee_items_view.add(employee);
                }
            }
            FilterResults results = new FilterResults();
            results.values = employee_items_view;
            results.count = employee_items_view.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            employee_items_view.clear();
            employee_items_view.addAll((ArrayList)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView employeeimage;
        private TextView employee_name;
        private TextView employee_designation;
        private RelativeLayout employeelist;
        private ImageView deleteimage;
        private Dialog deldialog;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeimage=itemView.findViewById(R.id.employeeimage);
            deleteimage=itemView.findViewById(R.id.deleteimage);
            employee_name=itemView.findViewById(R.id.employee_name);
            employee_designation=itemView.findViewById(R.id.employee_designation);
            employeelist=itemView.findViewById(R.id.employeelist);
        }
        public void setdata( String key,String image, String delimag, String name, String designation ) {
            Log.d("turn",":"+name);
            employee_name.setText(name);
            employee_designation.setText(designation);

            /* Post Data to other Activity To Set On TextViews :- */
            employeelist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context,PayrollSlipTemp.class);
                   /* intent.putExtra("data", (CharSequence) calculationmodal);*/
                    intent.putExtra("name",name);
                    intent.putExtra("designation",designation);
                    intent.putExtra("user_id",key);
                    context.startActivity(intent);
                }
            });

           /* Employee Delete From  FireBase Database as well as List :- */
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
