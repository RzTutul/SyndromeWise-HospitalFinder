package com.example.hospitalfinder;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.hospitalfinder.viewmodel.RegistrationViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFramgemt extends Fragment {

    private RegistrationViewModel registrationViewModel;

    public LoginFramgemt() {
        // Required empty pulic constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        registrationViewModel = ViewModelProviders.of(this).get(RegistrationViewModel.class);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_framgemt, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        if (Build.VERSION.SDK_INT>=21)
        {
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }


    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout regstrationBtn = view.findViewById(R.id.registrationBtn);

        Button loginbtn = view.findViewById(R.id.loginbtn);
        ImageView imageView = view.findViewById(R.id.imagelogo);
        LinearLayout loginLayout = view.findViewById(R.id.loginLayout);

        Animation imagAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.animation1);
        Animation imagAnimation1 = AnimationUtils.loadAnimation(getContext(),R.anim.animation_above);
        imageView.startAnimation(imagAnimation);
        loginLayout.setAnimation(imagAnimation1);

        ChangeStatusBarColor();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(view).navigate(R.id.mainDashBoard);
            }
        });

        regstrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.registrationFragment);
            }
        });

        registrationViewModel.stateLiveData.observe(getActivity(), new Observer<RegistrationViewModel.AuthenticationState>() {
            @Override
            public void onChanged(RegistrationViewModel.AuthenticationState authenticationState) {
                switch (authenticationState)
                {
                    case AUTHENTICATED:
                        Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.mainDashBoard);
                   break;
                    case UNAUTHENTICATED:
                        break;
                }
            }
        });


    }

    private void ChangeStatusBarColor() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {View decor = getActivity().getWindow().getDecorView();
            Window window = getActivity().getWindow();

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.RED);


        }
    }
}
