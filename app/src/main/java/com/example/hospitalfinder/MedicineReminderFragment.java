package com.example.hospitalfinder;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hospitalfinder.worker.NotificaitonWorker;
import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.TimeUnit;


public class MedicineReminderFragment extends Fragment {

    String medName ="Hello";
     TextInputEditText medicineName;
     TextView textView;
    Button saveMedicineBtn;
    public MedicineReminderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_medicine_reminder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        saveMedicineBtn = view.findViewById(R.id.saveMedicinebtn);
         medicineName = view.findViewById(R.id.medicineET);




        saveMedicineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medName = medicineName.getText().toString();
                Data inputdata = new Data.Builder().putString("msg",medName).build();
                Toast.makeText(getActivity(), ""+medName, Toast.LENGTH_SHORT).show();
                OneTimeWorkRequest oneTimeWorkRequest =new OneTimeWorkRequest.Builder(NotificaitonWorker.class).setInitialDelay(1, TimeUnit.DAYS).setInputData(inputdata).build();
                WorkManager.getInstance(getContext()).enqueue(oneTimeWorkRequest);



            }
        });
    }
}
