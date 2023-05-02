package com.example.fyprojectnew;


import static com.example.fyprojectnew.CalculationPageActivity.final_key;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CalculationAdapter extends RecyclerView.Adapter<CalculationAdapter.ViewHolder> {
    List<ClaculationModel> employeeslist;

    public CalculationAdapter(List<ClaculationModel> employeeslist) {
        this.employeeslist = employeeslist;
    }

    @NonNull
    @Override
    public CalculationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.employeesearching_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalculationAdapter.ViewHolder holder, int position) {
        String name= employeeslist.get(position).employeename;
        String key=employeeslist.get(position).key;
        holder.setdata(name,key);
    }

    @Override
    public int getItemCount() {
        return employeeslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView employeename;
        private ImageView tick_mark;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            employeename=itemView.findViewById(R.id.employeename);
            tick_mark=itemView.findViewById(R.id.tick_mark);
        }
        public void setdata(String name, String key) {
            employeename.setText(name);

            employeename.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tick_mark.setVisibility(View.VISIBLE);
                    Log.d("text",":"+key);
                    final_key = key;
                }
            });
        }
    }
}
