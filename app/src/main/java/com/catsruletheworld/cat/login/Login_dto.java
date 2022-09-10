package com.catsruletheworld.cat.login;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class Login_dto {

    @SerializedName("email")
    private String email;

    @SerializedName("id")
    private int id;

    @SerializedName("nickname")
    private String nickname;

    @SerializedName("profileImageUrl")
    private String profileImageUrl;

    @NonNull
    @Override
    public String toString() {
        return "Login_dto{" +
                "\nemail: " + email +
                "\nid: " + id +
                "\nnickname: " + nickname +
                "\nprofile: " + profileImageUrl +
                "}";
    }
}