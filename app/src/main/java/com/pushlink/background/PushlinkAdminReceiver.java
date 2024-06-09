package com.pushlink.background;

import android.app.PendingIntent;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PushlinkAdminReceiver extends DeviceAdminReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(Intent.ACTION_MY_PACKAGE_REPLACED)) {
            Log.i("PUSHLINK", "MY_PACKAGE_REPLACED called");
            Intent restartIntent = new Intent(context, MainActivity.class);
            restartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(restartIntent);
        }  else {
            Log.i("PUSHLINK", "DEVICE_ADMIN_ENABLED called");
        }
    }

}
