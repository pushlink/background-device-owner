# Sample app using pushlink CUSTOM strategy to perform background updates

Background updates synonyms:

- Installing apk without user interaction
- Silent updates
- Zero-touch updates
- KIOSK apps
- POS - Point of Sale apps
- Dedicated devices
- COSU - Corporate Owned Single Use devices

## Warning

- This only works in devices running **Android 6 (Marshmallow, API 23)** or higher
- This sample requires the app to be a **device owner**. Device owner is NOT **device admin**.
- There must be just **one** device owner and it can't be **unset** without **factory reset**.
- Setting a device owner requires an unprovisioned device. In other words: a device **without accounts**, like google account, etc.
- Bottom line: Use an emulator or a test-born device before releasing it to production

## Steps

1. Clone and replace the string "yourApiKey" in the PushlinkSetup.java
2. Build a signed APK
3. Upload it to Pushlink web administration
4. `adb install` it into the device
5. Execute:

```
adb shell dpm set-device-owner com.pushlink.background/.PushlinkAdminReceiver
```

6. Make some visible change (like hello world NEW) and run it again
7. Wait and see the magic happen

## Resources

https://developer.android.com/work/cosu

https://codelabs.developers.google.com/codelabs/cosu/index.html

https://developer.android.com/guide/topics/admin/device-admin

http://florent-dupont.blogspot.com/2015/01/android-shell-command-dpm-device-policy.html
