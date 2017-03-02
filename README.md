# kiosk-pos
Learn how to update your KIOSK Android application (POS - Point of Sale) without user interaction with pushlink.

## Are you interested to update your private enterprise android app automatically?

Pushlink covers 100% of this need.

1. For Android <= 4 just use [NINJA MODE](https://www.pushlink.com/docs.xhtml#ninja). (it requires a **ROOTED** device) 
2. For Android >= 5 just use [CUSTOM MODE](https://www.pushlink.com/docs.xhtml#custom-strategy) with [COSU](https://developer.android.com/work/cosu.html). (it requires **ADMIN PRIVILEGES** on device)

## How to use pushlink to update versions silently (android >= 5).

Basically you are going to:

1. Register a brodcast receiver using [custom strategy](https://www.pushlink.com/docs.xhtml#custom-strategy)
2. Get the `uri` extra and call: 
``` java
public void installSilently(Context context, Uri apkUri) throws IOException {

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

    session.commit(createIntentSender(context, sessionId));
    
}
```

## NINJA Disclaimer

NINJA worked flawlessly until android 4. :metal:

When android 5 was released NINJA began to have issues and we was forced to implement a ```<receiver android:name="com.pushlink.android.NinjaReceiver" />``` to make it work. **Unfortunately** it was not fixed completely and **it still can fails sometimes**, depending the combination of `OS version` x `Vendor`. :confused:

So if you have Android >= 5, the better choice is `CUSTOM MODE` with admin privileges on device.
