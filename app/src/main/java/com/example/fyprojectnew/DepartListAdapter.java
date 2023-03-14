package com.example.fyprojectnew;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class DepartListAdapter extends RecyclerView.Adapter<DepartListAdapter.ViewHolder> {

    private List<DepartmentModel>departlist;
    Context context;
    public DepartListAdapter(Context context, List<DepartmentModel> departlist) {
        this.context=context;
        this.departlist=departlist;
    }

    @NonNull
    @Override
    public DepartListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.departments_list_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DepartListAdapter.ViewHolder holder, int position) {
String image=departlist.get(position).getDepartimage();
String name=departlist.get(position).getDepartname();
holder.setdata(image,name);
    }

    @Override
    public int getItemCount() {
        return departlist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView departsimage;
        private TextView departname;
        private RelativeLayout departmentlist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            departsimage=itemView.findViewById(R.id.departsimage);
            departname=itemView.findViewById(R.id.departname);
            departmentlist=itemView.findViewById(R.id.departmentlist);
        }

        public void setdata(String image, String name) {
            departname.setText(name);
            if (name.equals("Administration"))
            {
                Glide.with(context).load(image).into(departsimage);
//                departsimage.setImageResource(R.drawable.admin_staff);
            }else if(name.equals("Mechanical Department")) {
                Glide.with(context).load(image).into(departsimage);
            }
            else if(name.equals("Mech Technology Department")) {
                Glide.with(context).load(image).into(departsimage);
            }
            else if(name.equals("Physics Department")) {
                Glide.with(context).load(image).into(departsimage);
            }
            else if (name.equals("Civil Engineering")){
                Glide.with(context).load(image).into(departsimage);
            }
            else if (name.equals("Chemistry Department")){
                Glide.with(context).load(image).into(departsimage);
            }
            else if(name.equals("Maths Department")) {
                Glide.with(context).load(image).into(departsimage);
            }
            else if(name.equals("CS Department")) {
                Glide.with(context).load(image).into(departsimage);
            }
            else if(name.equals("IT Department")) {
                Glide.with(context).load(image).into(departsimage);
            }
            else if(name.equals("Buissness Administration Department")) {
                Glide.with(context).load(image).into(departsimage);
            }
            else if(name.equals("Miscallaneous")) {
                Glide.with(context).load(image).into(departsimage);
            }
            else if(name.equals("Electrical Department")) {
                Glide.with(context).load(image).into(departsimage);
            }
            else if(name.equals("Elect Technology Department")) {
                Glide.with(context).load(image).into(departsimage);
            }
            else {
            }
            departmentlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(name.equals("Miscallaneous"))
                    {
                        context.startActivity(new Intent(context,MiscallennousStaff.class));
                    }else if (name.equals("Administration")){

                        context.startActivity(new Intent(context,Administration_staff.class));
                    }
                    else{
                        context.startActivity(new Intent(context, DesignationList.class));
                    }
                }
            });
        }
    }

}
