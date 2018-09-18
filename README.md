# Sample app using pushlink
This the right place if your are looking for:

* Installing apk without user interaction
* Silent updates
* Zero-touch updates
* KIOSK apps
* POS - Point of Sale apps
* Dedicated devices
* COSU - Corporate Owned Single Use devices

## Warning
* This sample requires the app to be a **device owner**. Device owner is NOT **device admin**.
* There must be just *one* device owner and it can't be *unset* without factory reset.
* Setting a device owner requires an unprovisioned device. In other words: a device without accounts, like google account, etc.
* Bottom line: Use emulator or a test born device before releasing to production

## Steps
1. Clone and replace the string "yourApiKey"
2. Build a signed APK and upload it to Pushlink
3. Run the APK you just upload and execute: ```adb shell dpm set-device-owner com.pushlink.background/.PushlinkAdminReceiver```
4. Make some visible change (like hello world NEW) and run again
5. Wait and see the magic happen

## Resources
https://developer.android.com/work/cosu

https://codelabs.developers.google.com/codelabs/cosu/index.html

https://developer.android.com/guide/topics/admin/device-admin

http://florent-dupont.blogspot.com/2015/01/android-shell-command-dpm-device-policy.html
