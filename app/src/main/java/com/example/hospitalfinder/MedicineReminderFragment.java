package com.example.hospitalfinder;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.hospitalfinder.utils.NotificationReceiver;
import com.example.hospitalfinder.worker.NotificaitonWorker;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;
import static android.content.Context.ALARM_SERVICE;


public class MedicineReminderFragment extends Fragment {

    String medName ="Hello";
    TimePicker timePicker;
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
        timePicker = view.findViewById(R.id.timePicker);




        saveMedicineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowNotificaiton(timePicker.getHour(),timePicker.getMinute());

            }
        });
    }

    private void ShowNotificaiton(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        long nowMillis = calendar.getTimeInMillis();

        if(calendar.get(Calendar.HOUR_OF_DAY) > hour ||
                (calendar.get(Calendar.HOUR_OF_DAY) == hour && calendar.get(Calendar.MINUTE)+1 >= minute)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);

        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        long diff = calendar.getTimeInMillis() - nowMillis;

        Log.i(TAG, "ShowNotificaiton: "+diff);

        medName = medicineName.getText().toString();
        Data inputdata = new Data.Builder().putString("msg",medName).build();
        Toast.makeText(getActivity(), ""+medName, Toast.LENGTH_SHORT).show();
        OneTimeWorkRequest oneTimeWorkRequest =new OneTimeWorkRequest.Builder(NotificaitonWorker.class).setInitialDelay(diff, TimeUnit.MILLISECONDS).setInputData(inputdata).build();
        WorkManager.getInstance(getContext()).enqueue(oneTimeWorkRequest);
    }

}
