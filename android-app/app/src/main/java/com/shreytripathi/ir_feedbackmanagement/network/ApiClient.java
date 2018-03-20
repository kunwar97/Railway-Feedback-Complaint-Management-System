package com.shreytripathi.ir_feedbackmanagement.network;

import android.util.Log;

import com.google.gson.Gson;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 16-Jun-17.
 */

public class ApiClient {
    public static final String BASE_URL = "http://35.167.125.98/";
    public static ApiInterface apiInterface = null;

    public static ApiInterface getApiClient() {
        if (apiInterface == null) {
            okhttp3.OkHttpClient.Builder builder = new okhttp3.OkHttpClient.Builder();
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.interceptors().add(httpLoggingInterceptor);

            okhttp3.OkHttpClient okHttpClient = builder.build();

            apiInterface = new Retrofit.Builder().
                    baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
                    .create(ApiInterface.class);
        }
        return apiInterface;
    }

    public static abstract class Callback<T> implements retrofit2.Callback<T> {

        @Override
        public void onResponse(final Call<T> call, retrofit2.Response<T> response) {

            if (response.isSuccessful()) {
                success(response.body());
                return;
            }
            try {
                String responseString = response.errorBody().string();
                Log.d("ayush", "onResponse: error response is " + responseString);
                Gson gson = new Gson();
                Error error = gson.fromJson(responseString, Error.class);
                failure(error);
            } catch (Exception e) {
                Log.d("ayush", "onResponse Exception failure " + e.getMessage());
                failure(new Error());
            }

        }


        @Override
        public void onFailure(Call<T> call, Throwable t) {
            Log.d("ayush", "onFailure: retrofit failure " + t.getMessage());
            Error error = new Error(t.getMessage());
            failure(error);
        }

        abstract public void success(T response);

        abstract public void failure(Error error);

    }
}
