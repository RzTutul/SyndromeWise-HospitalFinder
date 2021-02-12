package com.example.hospitalfinder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalfinder.R;
import com.example.hospitalfinder.pojos.PescriptionPojo;
import com.example.hospitalfinder.utils.Helper;

import java.util.List;

public class PescriptionListAdapter extends RecyclerView.Adapter<PescriptionListAdapter.PescriptionViewHolder>{

    Context context;
    List<PescriptionPojo> pescriptionPojoList;

    public PescriptionListAdapter(Context context, List<PescriptionPojo> pescriptionPojoList) {
        this.context = context;
        this.pescriptionPojoList = pescriptionPojoList;
    }

    @NonNull
    @Override
    public PescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_pescription,parent,false);

        return new PescriptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PescriptionViewHolder holder, int position) {

        String apoinmentDate = pescriptionPojoList.get(position).getApoinmentDate();

       long leftDate = Helper.getDefferentBetweenTwoDate(Helper.getCrrentDate(),apoinmentDate);

        holder.hospitalNameTv.setText(pescriptionPojoList.get(position).getHospitalName());
        holder.doctorNameTV.setText(pescriptionPojoList.get(position).getDoctorName());
        holder.doctorPhoneTV.setText(pescriptionPojoList.get(position).getDoctorPhone());
        holder.apoinmentDateTV.setText(pescriptionPojoList.get(position).getApoinmentDate());

        if (leftDate==0)
        {
            holder.daysLeftTV.setText("Today");

        }
           else if (leftDate<0)
        {
            holder.daysLeftTV.setText("End appointment");

        }
        else
        {
            holder.daysLeftTV.setText(String.valueOf(leftDate)+" Days Left");
        }

    }

    @Override
    public int getItemCount() {

        return pescriptionPojoList.size();
    }

    class PescriptionViewHolder extends RecyclerView.ViewHolder {
        TextView hospitalNameTv,doctorNameTV,doctorPhoneTV,apoinmentDateTV,daysLeftTV;

        public PescriptionViewHolder(@NonNull View itemView) {
            super(itemView);
            hospitalNameTv = itemView.findViewById(R.id.hospitalnameTV);
            doctorNameTV = itemView.findViewById(R.id.doctorNameTV);
            doctorPhoneTV = itemView.findViewById(R.id.doctorPhoneTV);
            apoinmentDateTV = itemView.findViewById(R.id.apoinmentDateTV);
            daysLeftTV = itemView.findViewById(R.id.dayLeftTV);
        }
    }



}
