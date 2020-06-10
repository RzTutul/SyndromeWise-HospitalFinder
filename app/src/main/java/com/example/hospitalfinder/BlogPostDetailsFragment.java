package com.example.hospitalfinder;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlogPostDetailsFragment extends Fragment {

    String title,post,imageUrl;
    TextView titleTV,postTV;
    ImageView postImage;

    public BlogPostDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        if (bundle !=null)
        {
            title = bundle.getString("title");
            post = bundle.getString("post");
            imageUrl = bundle.getString("imgUrl");

        }

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blog_post_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();


        titleTV = view.findViewById(R.id.titleTV);
        postTV = view.findViewById(R.id.postTV);
        postImage = view.findViewById(R.id.postImage);

        titleTV.setText(title);
        postTV.setText(post);

        Glide.with(getActivity()).load(imageUrl).into(postImage);


    }
}
