package com.catsruletheworld.cat;

import android.app.Application;
import com.kakao.sdk.common.KakaoSdk;

public class GlobalApplication extends Application {
    private static GlobalApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // sdk 초기화
        KakaoSdk.init(this, "80d4eefdb7484df45a53ea8b006f0430");
    }
}
