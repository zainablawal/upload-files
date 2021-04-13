package com.example.sampleapp;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {
    private static Retrofit retrofit;
    private static String BASE_URL = "www.abc.com/";

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder( ).build( );
            retrofit = new Retrofit.Builder( ).baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create( )).client(okHttpClient).build( );
        }
        return retrofit;
    }
}
