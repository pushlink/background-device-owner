package com.pushlink.background;

import android.app.Application;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInstaller;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

import com.pushlink.android.PushLink;
import com.pushlink.android.StrategyEnum;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PushlinkSetup extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        PushLink.setCurrentStrategy(StrategyEnum.CUSTOM);
        PushLink.start(this, R.mipmap.ic_launcher, "yourApiKey", deviceId);
        //DO NOT register this receiver in AndroidManifest.xml (if you do that, CUSTOM will not work in Android Oreo!)
        //more info https://developer.android.com/about/versions/oreo/background
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i("PUSHLINK", "pushlink.APPLY called");
                if (isDeviceOwner(context)) {
                    Uri apkUri = (Uri) intent.getExtras().get("uri");
                    installSilently(context, apkUri);
                } else {
                    Log.i("PUSHLINK", "You are not DEVICE OWNER. Update skipped!");
                }
            }
        }, new IntentFilter(getPackageName() + ".pushlink.APPLY"));
    }

    /* PRIVATE */

    private void installSilently(Context context, Uri apkUri) {

        try {

            InputStream in = new FileInputStream(new File(context.getFilesDir(), apkUri.getPath()));
            PackageInstaller packageInstaller = context.getPackageManager().getPackageInstaller();
            PackageInstaller.SessionParams params = new PackageInstaller.SessionParams(
                    PackageInstaller.SessionParams.MODE_FULL_INSTALL);
            params.setAppPackageName(context.getPackageName());
            // set params
            int sessionId = packageInstaller.createSession(params);
            PackageInstaller.Session session = packageInstaller.openSession(sessionId);
            OutputStream out = session.openWrite("COSU", 0, -1);
            byte[] buffer = new byte[65536];
            int c;
            while ((c = in.read(buffer)) != -1) {
                out.write(buffer, 0, c);
            }
            session.fsync(out);
            in.close();
            out.close();

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, sessionId, new Intent("dummy.intent.not.used"), 0);
            session.commit(pendingIntent.getIntentSender());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private boolean isDeviceOwner(Context context) {
        try {
            DevicePolicyManager mDPM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            return mDPM != null && mDPM.isDeviceOwnerApp(context.getPackageName());
        } catch (Throwable t) {
            Log.e("PUSHLINK", "", t);
            return false;
        }
    }

}
