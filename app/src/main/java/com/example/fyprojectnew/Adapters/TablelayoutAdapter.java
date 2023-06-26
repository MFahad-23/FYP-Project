package com.example.fyprojectnew.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyprojectnew.Models.ApprovalsModel;
import com.example.fyprojectnew.R;

import java.util.List;

public class TablelayoutAdapter extends RecyclerView.Adapter<TablelayoutAdapter.ViewHolder> {
    List<ApprovalsModel> table_list;
    Context context;

    public TablelayoutAdapter(List<ApprovalsModel> table_list, Context context) {
        this.table_list = table_list;
        this.context = context;
    }

    @NonNull
    @Override
    public TablelayoutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_approvals_addition,parent,true);
        return new TablelayoutAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TablelayoutAdapter.ViewHolder holder, int position) {
    String name=table_list.get(position).employee_name;
    String teach_sub=table_list.get(position).teaching_subject;
    String sub=table_list.get(position).subject;
    String sect=table_list.get(position).section;
    String sem=table_list.get(position).semister_spinner;
    String qualification=table_list.get(position).employee_qualification;
    holder.setdata(position,name,teach_sub,sub,sect,sem,qualification);
    }

    @Override
    public int getItemCount() {
        return table_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private TextView textView1;
        private TextView textView2;
        private TextView textView3;
        private TextView textView4;
        private TextView textView5;
        private TextView textView6;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.no);
            textView1=itemView.findViewById(R.id.teacher);
            textView2=itemView.findViewById(R.id.teach_subject);
            textView3=itemView.findViewById(R.id.subject);
            textView4=itemView.findViewById(R.id.setion);
            textView5=itemView.findViewById(R.id.semister);
            textView6=itemView.findViewById(R.id.qualification);
        }
        public void setdata(int position,String name, String teach_sub, String sub, String sect, String sem, String qualification) {
            textView.setText(position);
            textView1.setText(name);
            textView2.setText(teach_sub);
            textView3.setText(sub);
            textView4.setText(sect);
            textView5.setText(sem);
            textView6.setText(qualification);
        }
    }
}
