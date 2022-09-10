package com.catsruletheworld.cat.login;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Login_interface {

    @GET("get/{login}")
    Call<Login_dto> getLoginInfo(@Path("login") String login);

    @POST("post/{login}")
    Call<Login_dto> postLoginInfo(@Path("login") String login);
}
