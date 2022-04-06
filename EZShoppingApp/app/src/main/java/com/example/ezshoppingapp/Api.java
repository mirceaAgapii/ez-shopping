package com.example.ezshoppingapp;

import com.example.ezshoppingapp.model.UserRegistrationDTO;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @POST("users/save")
    Call<ResponseBody> register(@Body UserRegistrationDTO dto);
}
