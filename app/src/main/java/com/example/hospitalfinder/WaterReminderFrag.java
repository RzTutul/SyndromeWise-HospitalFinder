package com.example.hospitalfinder;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hospitalfinder.worker.NotificaitonWorker;
import com.google.android.material.textfield.TextInputEditText;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;


public class WaterReminderFrag extends Fragment {


    TextInputEditText titleNameET;
    TextView textView;
    Button saveMedicineBtn;
    private NumberPicker numberPicker1, number_picker2;

    public WaterReminderFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_water_reminder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        saveMedicineBtn = view.findViewById(R.id.saveMedicinebtn);
        titleNameET = view.findViewById(R.id.medicineET);

        numberPicker1 = (NumberPicker) view.findViewById(R.id.number_picker1);
        number_picker2 = (NumberPicker) view.findViewById(R.id.number_picker2);

        saveMedicineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

         ShowNotificaiton(numberPicker1.getValue(),number_picker2.getValue());

            }
        });
    }

    private void ShowNotificaiton(int hour, int minute) {
      int time;
        if (hour==0)
        {
            time = minute;
        }
        else
        {
            time = (hour*60)+minute;
        }
        Log.i(TAG, "timehouer: "+time);

       String titleName = titleNameET.getText().toString();
        Data inputdata = new Data.Builder().putString("msg",titleName).build();
        Toast.makeText(getActivity(), "Saved Successfully !", Toast.LENGTH_SHORT).show();
        PeriodicWorkRequest periodicWorkRequest =new PeriodicWorkRequest.Builder(NotificaitonWorker.class,time, TimeUnit.MINUTES).setInputData(inputdata).build();
        WorkManager.getInstance(getContext()).enqueue(periodicWorkRequest);
    }
}