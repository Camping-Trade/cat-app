package com.catsruletheworld.cat.login;

import android.location.Location;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login_retrofit extends AppCompatActivity {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://ec2-3-38-233-110.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/index.html#/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    Login_interface service1 = retrofit.create(Login_interface.class);

    Call<Login_dto> call1 = service1.postLoginInfo("1");

    call1.enqueue(new Callback<Login_dto>() {
        @Override
        public void onResponse(Call<Login_dto> call1, Response<Location> response) {
            if (response.isSuccessful()) {
                Login_dto result = onResponse.body();
                Log.d("CaT", "onResponse: 성공, 결과 = " + result.toString());
            } else {
                Log.d("CaT", "onResponse: 실패");
            }
        }

        @Override
        public void onFailure(Call<Login_dto> call1, Throwable t) {
            Log.d("CaT", "onFailure: " + t.getMessage());
        }

    });

}
