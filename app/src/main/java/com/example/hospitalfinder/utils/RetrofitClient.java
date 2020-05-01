package com.example.hospitalfinder.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {

    public static final String BASE_URL ="https://www.googleapis.com/blogger/v3/";

    public static Retrofit getClient()
    {
        return new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

    }




}

