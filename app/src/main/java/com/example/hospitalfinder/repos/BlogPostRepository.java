package com.example.hospitalfinder.repos;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.hospitalfinder.blogpostpojo.BlogResponseBody;
import com.example.hospitalfinder.serviceapi.BlogServiceApi;
import com.example.hospitalfinder.utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogPostRepository {

    MutableLiveData<BlogResponseBody> currentBlogLD = new MutableLiveData<>();
    public MutableLiveData<BlogResponseBody> getCurrentPost(String blogId,String apiKey)
    {
        BlogServiceApi blogServiceApi =
                RetrofitClient.getClient().create(BlogServiceApi.class);

        String EndUrl = String.format("blogs/%s/posts?key=%s",blogId,apiKey);

        blogServiceApi.getCurrentBlogPost(EndUrl)
                .enqueue(new Callback<BlogResponseBody>() {
                    @Override
                    public void onResponse(Call<BlogResponseBody> call, Response<BlogResponseBody> response) {
                        BlogResponseBody blogResponseBody = response.body();
                        currentBlogLD.postValue(blogResponseBody);
                    }

                    @Override
                    public void onFailure(Call<BlogResponseBody> call, Throwable t) {

                    }
                });

        return currentBlogLD;


    }
}
