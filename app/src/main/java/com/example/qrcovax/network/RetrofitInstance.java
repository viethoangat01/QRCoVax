package com.example.qrcovax.network;

import com.example.qrcovax.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit = null;

    public static RestApiService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(Constants.SERVER)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(RestApiService.class);
    }
}
