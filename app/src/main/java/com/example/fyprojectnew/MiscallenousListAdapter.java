package com.example.fyprojectnew;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MiscallenousListAdapter extends RecyclerView.Adapter<MiscallenousListAdapter.ViewHolder> {

    List<MiscallenousModel>miscallenousModelList;
    public MiscallenousListAdapter(List<MiscallenousModel> miscallenousModelList) {
        this.miscallenousModelList = miscallenousModelList;
    }

    @NonNull
    @Override
    public MiscallenousListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.miscalenous_staff_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MiscallenousListAdapter.ViewHolder holder, int position) {
String name=miscallenousModelList.get(position).staffname;
String image=miscallenousModelList.get(position).staffimgae;
        holder.setdata(name,image);
    }

    @Override
    public int getItemCount() {
        return miscallenousModelList.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView staffimage;
        private TextView staffname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            staffimage=itemView.findViewById(R.id.staffimage);
            staffname=itemView.findViewById(R.id.staffname);
        }

        public void setdata(String name, String image) {
            staffname.setText(name);
        }
    }
}
