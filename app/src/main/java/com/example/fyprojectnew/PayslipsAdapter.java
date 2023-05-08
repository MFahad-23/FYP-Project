package com.example.fyprojectnew;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PayslipsAdapter extends RecyclerView.Adapter<PayslipsAdapter.ViewHolder> {
List<PaySlipModal> historySlips;
    Context context;
    public PayslipsAdapter(List<PaySlipModal> historySlips, Context context) {
        this.historySlips = historySlips;
        this.context = context;
    }

    @NonNull
    @Override
    public PayslipsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.historyslips_designs, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PayslipsAdapter.ViewHolder holder, int position) {
        String name=historySlips.get(position).employeename;
        String designation=historySlips.get(position).employeedesignation;
        String date=historySlips.get(position).date;
        String key=historySlips.get(position).key;
        holder.setdata(name,designation,date,key);
    }

    @Override
    public int getItemCount() {
        return historySlips.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView employeename;
        private RelativeLayout historyslips;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            employeename = itemView.findViewById(R.id.employeename);
            historyslips = itemView.findViewById(R.id.historyslips);
        }
        public void setdata(String name, String designation, String date, String key) {
            employeename.setText(name);
            Log.d("chil",":"+name);

            /* History slips List :- */
            historyslips.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
