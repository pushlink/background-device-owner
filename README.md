# kiosk-pos
Learn how to update your KIOSK Android application (POS - Point of Sale) without user interaction with pushlink.

## Are you interested to update your private enterprise android app automatically?

Pushlink covers 100% of this need.

1. For Android <= 4 just use [NINJA MODE](https://www.pushlink.com/docs.xhtml#ninja). (it requires a ROOTED device) 
2. For Android >= 5 just user [CUSTOM MODE](https://www.pushlink.com/docs.xhtml#custom-strategy) with [COSU](https://developer.android.com/work/cosu.html). (it requires admin privileges on device)

## This repo is a demo app showing how to use pushlink with android >= 5.

```
Just enjoy the code ;)
```

## NINJA Disclaimer

NINJA worked flawlessly until android 4. :metal:

When android 5 was released NINJA began to have issues and we was forced to implement a ```<receiver android:name="com.pushlink.android.NinjaReceiver" />``` to make it work. **Unfortunately** it was not fixed completely and **it still can fails sometimes**, depending the combination of `OS version` x `Vendor`. :confused:

So if you have Android >= 5, the better choice is `CUSTOM MODE` with admin privileges on device.
