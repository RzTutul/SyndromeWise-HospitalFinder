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

import com.example.hospitalfinder.pojos.DonnerPojo;
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
    private Spinner genderSP, bloodSP,donnerSP,districSP;
    Button registerButton;
    String[] gender;
    String[] bloodgroup;
    String [] donnerStatus  ={"Are you a blood donner?","Yes","No"};
    String select_gender = "Select Gender";
    String bloodDonner ;
    String select_blood = "Select Blood Group", dateofBirth;
    String select_distric ="Select District";

    private RegistrationViewModel registrationViewModel;

   String[] districList ={
           "Select District",
           "Bagerhat",
           "Bandarban",
           "Barguna",
           "Barisal",
           "Bhola",
           "Bogra",
           "Brahmanbaria",
           "Chandpur",
           "Chittagong",
           "Chuadanga",
           "Comilla",
           "Cox's Bazar",
           "Dhaka",
           "Dinajpur",
           "Faridpur",
           "Feni",
           "Gaibandha",
           "Gazipur",
           "Gopalganj",
           "Habiganj",
           "Jaipurhat",
           "Jamalpur",
           "Jessore",
           "Jhalakati",
           "Jhenaidah",
           "Khagrachari",
           "Khulna",
           "Kishoreganj",
           "Kurigram",
           "Kushtia",
           "Lakshmipur",
           "Lalmonirhat",
           "Madaripur",
           "Magura",
           "Manikganj",
           "Meherpur",
           "Moulvibazar",
           "Munshiganj",
           "Mymensingh",
           "Naogaon",
           "Narail",
           "Narayanganj",
           "Narsingdi",
           "Natore",
           "Nawabganj",
           "Netrakona",
           "Nilphamari",
           "Noakhali",
           "Pabna",
           "Panchagarh",
           "Parbattya Chattagram",
           "Patuakhali",
           "Pirojpur",
           "Rajbari",
           "Rajshahi",
           "Rangpur",
           "Satkhira",
           "Shariatpur",
           "Sherpur",
           "Sirajganj",
           "Sunamganj",
           "Sylhet",
           "Tangail",
           "Thakurgaon"
   };
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
        donnerSP = view.findViewById(R.id.donnerSP);
        districSP = view.findViewById(R.id.districSP);
        registrationProgress = view.findViewById(R.id.registerProgressBar);

        registerButton = view.findViewById(R.id.registerBtn);

        gender = getResources().getStringArray(R.array.gender);
        bloodgroup = getResources().getStringArray(R.array.blood_group);

        ArrayAdapter<String> genderAdpaer = new ArrayAdapter<>(getActivity(), R.layout.spinner_layout, gender);

        ArrayAdapter<String> bloodAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_layout, bloodgroup);
        ArrayAdapter<String> donnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_layout, donnerStatus);
        ArrayAdapter<String> disticAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_layout, districList);
        genderSP.setAdapter(genderAdpaer);
        bloodSP.setAdapter(bloodAdapter);
        donnerSP.setAdapter(donnerAdapter);
        districSP.setAdapter(disticAdapter);

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
                }
                else if (bloodDonner.equals("Are you a blood donner?"))
                {
                    Snackbar.make(getView(), "Select Donner Status",Snackbar.LENGTH_SHORT).show();

                }
                else if (select_distric.equals("Select District")) {
                    Snackbar.make(getView(), "Select District", Snackbar.LENGTH_SHORT).show();

                }

                else {

                    registrationProgress.setVisibility(View.VISIBLE);
                    userInformationPojo userinfopojo = new userInformationPojo(null, name, email, phone, select_blood, select_blood,bloodDonner,select_distric, dateofBirth, password);

                    registrationViewModel.register(userinfopojo);

                    if (bloodDonner.equals("Yes"))
                    {
                        DonnerPojo donnerPojo = new DonnerPojo(null,name,select_gender,phone,select_distric);
                        registrationViewModel.AddDonnerList(donnerPojo);
                    }



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


        donnerSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bloodDonner = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
         districSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                select_distric = parent.getItemAtPosition(position).toString();

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
