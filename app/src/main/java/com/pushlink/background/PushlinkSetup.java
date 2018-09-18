package com.pushlink.background;

import android.app.Application;
import android.provider.Settings;

import com.pushlink.android.PushLink;
import com.pushlink.android.StrategyEnum;

public class PushlinkSetup extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        PushLink.setCurrentStrategy(StrategyEnum.CUSTOM);
        PushLink.start(this, R.mipmap.ic_launcher, "yourApiKey", deviceId);
    }

}
