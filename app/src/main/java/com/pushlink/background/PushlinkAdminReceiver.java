package com.pushlink.background;

import android.app.PendingIntent;
import android.app.admin.DeviceAdminReceiver;
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
        } else if (intent.getAction().equals(context.getPackageName() + ".pushlink.APPLY")) {
            Log.i("PUSHLINK", "pushlink.APPLY called");
            Uri apkUri = (Uri) intent.getExtras().get("uri");
            installSilently(context, apkUri);
        }else{
            Log.i("PUSHLINK", "DEVICE_ADMIN_ENABLED called");
        }
    }

    private void installSilently(Context context, Uri apkUri) {

        try {

            InputStream in = context.getContentResolver().openInputStream(apkUri);
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

}
