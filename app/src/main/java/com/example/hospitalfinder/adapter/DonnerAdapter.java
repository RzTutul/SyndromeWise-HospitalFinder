package com.example.hospitalfinder.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hospitalfinder.R;
import com.example.hospitalfinder.pojos.DonnerPojo;

import java.util.List;

public class DonnerAdapter extends RecyclerView.Adapter<DonnerAdapter.DonnerVierHolder>{

    Context context;
    List<DonnerPojo> donnerPojoList;

    public DonnerAdapter(Context context, List<DonnerPojo> donnerPojoList) {
        this.context = context;
        this.donnerPojoList = donnerPojoList;
    }

    @NonNull
    @Override
    public DonnerVierHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater= LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.donner_layout_row,parent,false);
        return new DonnerVierHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonnerVierHolder holder, int position) {
        DonnerPojo donnerPojo = donnerPojoList.get(position);

        holder.nameTV.setText(donnerPojo.getDonnerName());
        holder.genderTV.setText(donnerPojo.getDonnerGender());
        holder.phoneTV.setText(donnerPojo.getDonnerPhone());
        holder.districtTV.setText(donnerPojo.getDonnerDistric());


        holder.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri phoneUri = Uri.parse("tel:"+donnerPojo.getDonnerPhone());
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, phoneUri);
                if (dialIntent.resolveActivity(context.getPackageManager()) != null){
                    context.startActivity(dialIntent);
                }
                else{
                    Toast.makeText(context, "no component found", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return donnerPojoList.size();
    }

    public class DonnerVierHolder extends RecyclerView.ViewHolder{

        TextView nameTV,genderTV,phoneTV,districtTV;
        Button callBtn;
        public DonnerVierHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.d_NameTV);
            genderTV = itemView.findViewById(R.id.d_genderTV);
            phoneTV = itemView.findViewById(R.id.d_phoneTV);
            districtTV = itemView.findViewById(R.id.d_districtTV);
            callBtn = itemView.findViewById(R.id.phnCallBtn);
        }
    }

}
