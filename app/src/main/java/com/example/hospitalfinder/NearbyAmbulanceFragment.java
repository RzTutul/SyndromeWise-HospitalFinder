package com.example.hospitalfinder;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.hospitalfinder.viewmodel.LocationViewModel;
import com.example.hospitalfinder.webviewHelper.GeoWebChromeClient;
import com.example.hospitalfinder.webviewHelper.GeoWebViewClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


/**
 * A simple {@link Fragment} subclass.
 */
public class NearbyAmbulanceFragment extends Fragment {

    WebView webView;
    LocationViewModel locationViewModel;

    public NearbyAmbulanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        locationViewModel = ViewModelProviders.of(getActivity()).get(LocationViewModel.class);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nearby_ambulance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        webView = view.findViewById(R.id.webviewAmbulance);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(createLocationRequest());

        SettingsClient client = LocationServices.getSettingsClient(getActivity());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                locationViewModel.getDeviceCurrentLocation();
            }
        });

        task.addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {

                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        /*resolvable.startResolutionForResult(getActivity(),
                                123);*/
                        startIntentSenderForResult(resolvable.getResolution().getIntentSender(),
                                123,null, 0, 0,
                                0, null);

                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebViewClient(new GeoWebViewClient());
        // Below required for geolocation
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.setWebChromeClient(new GeoWebChromeClient());


        locationViewModel.locationLD.observe(getActivity(), new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                if (location == null){

                    return;
                }

                else
                {


                    String url = String.format("https://www.google.com.bd/maps/search/ambulance/@%f,%f,15z/data=!3m1!4b1?hl=en", location.getLatitude(), location.getLongitude());
                    webView.loadUrl(url);

                    webView.setFocusableInTouchMode(true);
                    webView.requestFocus();
                }

            }
        });

    }

    private LocationRequest createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(3000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }


    private boolean isLocationPermissionGranted(){
        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 111);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 111 && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED){

            locationViewModel.getDeviceCurrentLocation();
            //Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.nearbyHospitalFragment);


        }
        else
        {

            Toast.makeText(getActivity(), "Need Permission for use Map", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123){
            if (resultCode == Activity.RESULT_OK){
                locationViewModel.getLocationUpdate();
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        locationViewModel.getDeviceCurrentLocation();
    }

}
