package com.example.hospitalfinder;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.hospitalfinder.modelview.RegistrationViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFramgemt extends Fragment {

    private RegistrationViewModel registrationViewModel;

    public LoginFramgemt() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_framgemt, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button button = view.findViewById(R.id.button);
        ImageView imageView = view.findViewById(R.id.imagelogo);
        LinearLayout loginLayout = view.findViewById(R.id.loginLayout);

        Animation imagAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.animation1);
        Animation imagAnimation1 = AnimationUtils.loadAnimation(getContext(),R.anim.animation_above);
        imageView.startAnimation(imagAnimation);
        loginLayout.setAnimation(imagAnimation1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(view).navigate(R.id.mainDashBoard);
            }
        });
    }
}
