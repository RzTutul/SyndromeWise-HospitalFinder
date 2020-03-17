package com.example.hospitalfinder;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainDashBoard extends Fragment {


    public MainDashBoard() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        (getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_dash_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).setTitle("DashBoard");

        LinearLayout dashboardoption = view.findViewById(R.id.dashboardOption);
        Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.animation_above);
        dashboardoption.setAnimation(animation);
          LinearLayout dashboardoptionUperText = view.findViewById(R.id.dashboardOption_uperText);
        Animation moveRL = AnimationUtils.loadAnimation(getActivity(),R.anim.move_rl_animation);
        dashboardoptionUperText.setAnimation(moveRL);
       /* LinearLayout dashboardTextUper = view.findViewById(R.id.dashboardTextUper);
        Animation moveRL1 = AnimationUtils.loadAnimation(getActivity(), R.anim.move_rl_animation);
        dashboardTextUper.setAnimation(moveRL1);
*/

         ImageView imageView = view.findViewById(R.id.dashboardImage);
        Animation animation1 = AnimationUtils.loadAnimation(getActivity(),R.anim.animation1);
        imageView.setAnimation(animation1);

        TextView textView = view.findViewById(R.id.dashboardText);
        Animation moveAnimation = AnimationUtils.loadAnimation(getActivity(),R.anim.move_animation);
        textView.setAnimation(moveAnimation);





    }


}
