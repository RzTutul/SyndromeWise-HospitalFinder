package com.example.hospitalfinder;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hospitalfinder.adapter.PescriptionListAdapter;
import com.example.hospitalfinder.pojos.PescriptionPojo;
import com.example.hospitalfinder.viewmodel.PescriptionViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PescriptionListFragment extends Fragment {

    FloatingActionButton addPescriptionBtn;
    RecyclerView pescriptionRV;
    PescriptionListAdapter pescriptionListAdapter ;
    PescriptionViewModel pescriptionViewModel;


    public PescriptionListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        pescriptionViewModel = ViewModelProviders.of(this).get(PescriptionViewModel.class);

     //   pescriptionViewModel.getAllPescription();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pescription_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addPescriptionBtn = view.findViewById(R.id.addPescriptionBtn);
        pescriptionRV = view.findViewById(R.id.pescriptionRV);

        pescriptionViewModel.pescriptionLD.observe(getActivity(), new Observer<List<PescriptionPojo>>() {
            @Override
            public void onChanged(List<PescriptionPojo> pescriptionPojos) {
                pescriptionListAdapter = new PescriptionListAdapter(getActivity(),pescriptionPojos);

                LinearLayoutManager llm = new LinearLayoutManager(getActivity());

                pescriptionRV.setLayoutManager(llm);
                pescriptionRV.setAdapter(pescriptionListAdapter);
            }
        });

        addPescriptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(view).navigate(R.id.addPescriptionFragment);

            }
        });


    }
}
