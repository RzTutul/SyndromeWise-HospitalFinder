package com.example.hospitalfinder;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hospitalfinder.viewmodel.RegistrationViewModel;
import com.example.hospitalfinder.pojos.userInformationPojo;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegistrationFragment extends Fragment {

    private EditText nameET, emailET, phoneET, dataOfET, passET;
    private ProgressBar registrationProgress;
    private Spinner genderSP, bloodSP;
    Button registerButton;
    String[] gender;
    String[] bloodgroup;
    String select_gender = "Select Gender";
    String select_blood = "Select Blood Group", dateofBirth;
    private RegistrationViewModel registrationViewModel;
    public RegistrationFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        registrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameET = view.findViewById(R.id.userNameET);
        emailET = view.findViewById(R.id.userEmailEt);
        phoneET = view.findViewById(R.id.userPhoneNumberET);
        dataOfET = view.findViewById(R.id.dateofBitthET);
        passET = view.findViewById(R.id.userPasswordET);

        genderSP = view.findViewById(R.id.genderSp);
        bloodSP = view.findViewById(R.id.bloodgrpSp);
        registrationProgress = view.findViewById(R.id.registerProgressBar);

        registerButton = view.findViewById(R.id.registerBtn);

        gender = getResources().getStringArray(R.array.gender);
        bloodgroup = getResources().getStringArray(R.array.blood_group);

        ArrayAdapter<String> genderAdpaer = new ArrayAdapter<>(getActivity(), R.layout.spinner_layout, gender);

        ArrayAdapter<String> bloodAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_layout, bloodgroup);
        genderSP.setAdapter(genderAdpaer);
        bloodSP.setAdapter(bloodAdapter);

        dataOfET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectBirthDayDate();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameET.getText().toString();
                String email = emailET.getText().toString();
                String phone = phoneET.getText().toString();
                String dateofBirth = dataOfET.getText().toString();
                String password = passET.getText().toString();


                if (select_gender.equals("Select Gender")) {
                    Snackbar.make(getView(), "Select gender", Snackbar.LENGTH_SHORT).show();
                } else if (select_blood.equals("Select Blood Group")) {

                    Snackbar.make(getView(), "Select blood group",Snackbar.LENGTH_SHORT).show();
                } else {

                    registrationProgress.setVisibility(View.VISIBLE);
                    userInformationPojo userinfopojo = new userInformationPojo(null, name, email, phone, select_blood, select_blood, dateofBirth, password);
                    registrationViewModel.register(userinfopojo);

                    Sprite threeBound = new ThreeBounce();
                    registrationProgress.setIndeterminateDrawable(threeBound);

                }

            }
        });

        registrationViewModel.stateLiveData.observe(getActivity(), new Observer<RegistrationViewModel.AuthenticationState>() {
            @Override
            public void onChanged(RegistrationViewModel.AuthenticationState authenticationState) {
                switch (authenticationState) {
                    case AUTHENTICATED:
                        Navigation.findNavController(view).navigate(R.id.mainDashBoard);
                        break;
                    case UNAUTHENTICATED:
                        break;
                }
            }
        });

        registrationViewModel.errMsg.observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Snackbar.make(getView(),""+s,Snackbar.LENGTH_LONG).show();
                registrationProgress.setVisibility(View.GONE);
            }
        });

        genderSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select_gender = parent.getItemAtPosition(position).toString();

                Log.i(TAG, "gender: " + select_gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        bloodSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select_blood = parent.getItemAtPosition(position).toString();
                Log.i(TAG, "blood" + select_blood);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    private void SelectBirthDayDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            dateofBirth = new SimpleDateFormat("dd.MM.yyyy")
                    .format(calendar.getTime());
            dataOfET.setText(dateofBirth);
        }
    };
}
