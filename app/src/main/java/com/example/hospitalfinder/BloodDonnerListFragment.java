package com.example.hospitalfinder;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.hospitalfinder.adapter.DonnerAdapter;
import com.example.hospitalfinder.pojos.DonnerPojo;
import com.example.hospitalfinder.viewmodel.RegistrationViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BloodDonnerListFragment extends Fragment {

    RecyclerView donnerRV;
    RegistrationViewModel registrationViewModel;
    EditText searchET;
    List<DonnerPojo> list;

    public BloodDonnerListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        registrationViewModel = ViewModelProviders.of(getActivity()).get(RegistrationViewModel.class);
        registrationViewModel.getDonnerList();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blood_donner_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        donnerRV = view.findViewById(R.id.donnerRV);
        searchET = view.findViewById(R.id.searchET);

        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filterData(s.toString());
            }
        });

        registrationViewModel.donnerLD.observe(getActivity(), new Observer<List<DonnerPojo>>() {
            @Override
            public void onChanged(List<DonnerPojo> donnerPojos) {

                list = donnerPojos;
                DonnerAdapter donnerAdapter = new DonnerAdapter(getActivity(),donnerPojos);

                LinearLayoutManager llm = new LinearLayoutManager(getActivity());

                donnerRV.setLayoutManager(llm);
                donnerRV.setAdapter(donnerAdapter);


            }
        });

    }

    private void filterData(String district) {
        List<DonnerPojo> filertList = new ArrayList<>();

        for (DonnerPojo donnerPojo: list)
        {
            if ((donnerPojo.getDonnerDistric().toLowerCase()).contains(district.toLowerCase()))
            {
                filertList.add(donnerPojo);
            }
        }
        if (filertList.size()>0)
        {
            DonnerAdapter donnerAdapter = new DonnerAdapter(getActivity(),filertList);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            donnerRV.setLayoutManager(llm);
            donnerRV.setAdapter(donnerAdapter);
        }
    }
}
