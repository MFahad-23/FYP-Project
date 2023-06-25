package com.example.fyprojectnew.Adapters;

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
import com.example.fyprojectnew.Activities.PayrollsPageActivity;
import com.example.fyprojectnew.Models.ApprovalsModel;
import com.example.fyprojectnew.R;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

public class ApprovalsAdapter extends RecyclerView.Adapter<ApprovalsAdapter.ViewHolder> implements Filterable {

    private List<ApprovalsModel> aprovedlist;
    List<ApprovalsModel>approved_items_viewFull;
    Context context;

    public ApprovalsAdapter(List<ApprovalsModel> aprovedlist, Context context) {
        this.context = context;
        this.approved_items_viewFull=aprovedlist;
        this.aprovedlist = new ArrayList<>(approved_items_viewFull);
    }

    @NonNull
    @Override
    public ApprovalsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.approved_employees_design, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApprovalsAdapter.ViewHolder holder, int position) {
        String key = aprovedlist.get(position).key;
        String image = aprovedlist.get(position).employee_image;
        String name = aprovedlist.get(position).employee_name;
        String designation = aprovedlist.get(position).employee_designation;
        String date = aprovedlist.get(position).datepicker;
        holder.setdata(key, image, name, designation, date,aprovedlist.get(position));
    }

    @Override
    public int getItemCount() {
        return aprovedlist.size();
    }

    public Filter getFilter() {
        return Approvelist;
    }

    /* Activity Search Option :- */
    private final Filter Approvelist = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<ApprovalsModel> aprovedlist = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                aprovedlist.addAll(approved_items_viewFull);
            } else {
                String FilterPatterns = charSequence.toString().toLowerCase().trim();
                for (ApprovalsModel employee : approved_items_viewFull) {
                if (employee.employee_name.toLowerCase().contains(FilterPatterns))
                    aprovedlist.add(employee);
        }
    }
    FilterResults results = new FilterResults();
    results.values =aprovedlist;
    results.count =aprovedlist.size();
            return results;
}
    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        aprovedlist.clear();
        aprovedlist.addAll((ArrayList)filterResults.values);
        notifyDataSetChanged();
    }
};
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView employeeimage;
        private ImageView deleteimage;
        private TextView employeename;
        private TextView employee_designation;
        private TextView spinner_datepicker;
        private RelativeLayout employeelist;
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

        public void setdata(String key,String image, String name, String designation,String date,ApprovalsModel model) {
           /* Set Data into the Image :- */
            if(image!=null) {
                Glide.with(context).load(image).into(employeeimage);
            }
            employeename.setText(name);
            employee_designation.setText(designation);
            spinner_datepicker.setText(date);

           /* Employee Approvals Activity :- */
            employeelist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, PayrollsPageActivity.class);
                    intent.putExtra("data",model);
                    context.startActivity(intent);
                }
            });

            /* Employee Delete :- */
            deleteimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OpenDialog(key);
                }
            });

            /* Employee Image Check :- */
            employeeimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   Dialog imagedialog=new Dialog(context);
                    imagedialog.setContentView(R.layout.imagedialog);
                    ImageView imageview;
                    imageview=imagedialog.findViewById(R.id.imageview09);

                    if(image!=null) {
                        Log.d("this",image);
                        Glide.with(context).load(image).into(imageview);
                    }
                    imagedialog.show();
                }
            });
        }
    }

    /* Employee Delete Dialog :-  */
    private void OpenDialog(String key) {
      Dialog deldialog=new Dialog(context);
        deldialog.setContentView(R.layout.file_delete_dialog);
        deldialog.getWindow().setBackgroundDrawableResource(R.drawable.custom_dialog_background);
        TextView cancel,ok;
        cancel=deldialog.findViewById(R.id.cancel);
        ok=deldialog.findViewById(R.id.ok);
        deldialog.show();

        /* Dialog Cancel Button :- */

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
                FirebaseDatabase.getInstance().getReference().child("Employee Approvals").child(key).removeValue();
                deldialog.dismiss();
            }
        });
    }
}

