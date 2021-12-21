package com.example.qrcovax.network;

import com.example.qrcovax.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RestApiService {
    @POST("congdans")
    Call<User> updateInformation(@Body User user);
}
