package com.example.hospitalfinder;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hospitalfinder.adapter.AllPostAdapter;
import com.example.hospitalfinder.adapter.BlogPostAdapter;
import com.example.hospitalfinder.adapter.TrendingBlogPostAdapter;
import com.example.hospitalfinder.blogpostpojo.BlogResponseBody;
import com.example.hospitalfinder.blogpostpojo.Items;
import com.example.hospitalfinder.viewmodel.BlogPostViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Blogpost extends Fragment {

    TextView textVie;
    BlogPostViewModel blogPostViewModel;
    RecyclerView latestpostRV,trendingpostRV,allPostRV;
    ProgressBar loadingPostbar;
    String latestPostLabel = "latestPost";

    public Blogpost() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        blogPostViewModel = ViewModelProviders.of(this).get(BlogPostViewModel.class);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blogpost, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        latestpostRV = view.findViewById(R.id.latestPostRV);
        trendingpostRV = view.findViewById(R.id.trendintRV);
        allPostRV = view.findViewById(R.id.allArticleRV);
        loadingPostbar = view.findViewById(R.id.postProgressBar);
        String blogId = getString(R.string.blog_id);
        String apiKey = getString(R.string.blog_api_key);
        loadingPostbar.setVisibility(View.VISIBLE);

        blogPostViewModel.getLatestBlogPost(blogId, apiKey,latestPostLabel).observe(getActivity(), new Observer<BlogResponseBody>() {
            @Override
            public void onChanged(BlogResponseBody blogResponseBody) {

                List<Items> list = blogResponseBody.getItems();

                if (list.size() > 0) {
                    loadingPostbar.setVisibility(View.GONE);

                }
                BlogPostAdapter blogPostAdapter = new BlogPostAdapter(getActivity(), list);
                LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                latestpostRV.setLayoutManager(llm);
                latestpostRV.setAdapter(blogPostAdapter);

            }
        });

        blogPostViewModel.getCurrentBlogPost(blogId, apiKey).observe(getActivity(), new Observer<BlogResponseBody>() {
            @Override
            public void onChanged(BlogResponseBody blogResponseBody) {

                List<Items> list = blogResponseBody.getItems();

                if (list.size() > 0) {
                    loadingPostbar.setVisibility(View.GONE);

                }
                TrendingBlogPostAdapter blogPostAdapter = new TrendingBlogPostAdapter(getActivity(), list);
                LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                trendingpostRV.setLayoutManager(llm);
                trendingpostRV.setAdapter(blogPostAdapter);

            }
        });
        blogPostViewModel.getCurrentBlogPost(blogId, apiKey).observe(getActivity(), new Observer<BlogResponseBody>() {
            @Override
            public void onChanged(BlogResponseBody blogResponseBody) {

                List<Items> list = blogResponseBody.getItems();

                if (list.size() > 0) {
                    loadingPostbar.setVisibility(View.GONE);

                }
                AllPostAdapter blogPostAdapter = new AllPostAdapter(getActivity(), list);
                LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                allPostRV.setLayoutManager(llm);
                allPostRV.setAdapter(blogPostAdapter);

            }
        });


    }
}
