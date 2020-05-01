package com.example.hospitalfinder.serviceapi;

import com.example.hospitalfinder.blogpostpojo.BlogResponseBody;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface BlogServiceApi {
    @GET()
    Call<BlogResponseBody> getCurrentBlogPost(@Url String endUrl);

}
