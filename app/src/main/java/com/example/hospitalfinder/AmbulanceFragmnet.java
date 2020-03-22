package com.example.hospitalfinder;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AmbulanceFragmnet extends Fragment {

    CardView nearbyAmbulanceCard;
    ImageView callBtn;

    public AmbulanceFragmnet() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ambulance_fragmnet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nearbyAmbulanceCard = view.findViewById(R.id.nearbyAmbulanceCard);
        callBtn = view.findViewById(R.id.callBtn);

        nearbyAmbulanceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.nearbyAmbulanceFragment);
            }
        });

        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchCallIntent();
            }
        });
    }

    private void dispatchCallIntent() {
        Uri phoneUri = Uri.parse("tel:" + 999);
        Intent callItent = new Intent(Intent.ACTION_CALL, phoneUri);
        if (callItent.resolveActivity(((AppCompatActivity) getActivity()).getPackageManager()) != null) {
            if (isCallRequestAccepted()) {
                 startActivity(callItent);
            }
        } else {
            Toast.makeText(getActivity(), "no component found", Toast.LENGTH_SHORT).show();

        }

    }
    private boolean isCallRequestAccepted() {
        String[] permissionList = {Manifest.permission.CALL_PHONE};
        if (((AppCompatActivity) getActivity()).checkSelfPermission(Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissionList, 123);
            return false;

        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 123:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchCallIntent();
                } else {
                    Toast.makeText(getActivity(), "Need Permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }
}