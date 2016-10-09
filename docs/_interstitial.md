# Interstitial ads

The HyperAdX Interstitial ads allows you to monetize your Android apps. This guide explains how to add interstitial ads to your app. If you’re interested in other kinds of ad units, see the [list of available types](https://github.com/hyperads/android-sdk) .

Sample project:

* [Download](https://github.com/hyperads/android-sdk/releases) latest release and extract the Example app for Android.

### Set up the SDK

Add following under manifest tag to your AndroidManifest.xml:

```xml
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

Put the HyperAdxSDK_xxx.jar in “libs” folder in your Android Studio or Eclipse. Add it to dependencies in build.grandle file. Also you need to add google play services.

```groove
dependencies {
    compile fileTree(include: ['*.jar'], dir: 'libs')
    compile 'com.android.support:appcompat-v7:23.4.0'
    compile 'com.android.support:support-v4:23.4.0'
    compile 'com.google.android.gms:play-services-ads:9.0.2'
    compile 'com.google.android.gms:play-services-base:9.0.2'
}
```

Then, create a function that requests a interstitial ad. The SDK will log the impression and handle the click automatically.

```java
private void loadInterstitialAd() {
        interstitialAd = new HADInterstitialAd(this /*Strongly recomend to use Activity context*/,
                getString(R.string.Placement)); //Interstitial AD constructor.

        interstitialAd.setAdListener(new InterstitialAdListener() { // Set Listener
            @Override
            public void onAdLoaded(com.hyperadx.lib.sdk.interstitialads.Ad ad) { // Called when AD is Loaded
                iAd = ad;
                Toast.makeText(MainActivity.this, "Interstitial Ad loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(com.hyperadx.lib.sdk.interstitialads.Ad Ad, String error) { // Called when load is fail
                Toast.makeText(MainActivity.this, "Interstitial Ad failed to load with error: " + error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInterstitialDisplayed() { // Called when Ad was impressed
                Toast.makeText(MainActivity.this, "Tracked Interstitial Ad impression", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInterstitialDismissed(com.hyperadx.lib.sdk.interstitialads.Ad ad) { // Called when Ad was dissnissed by user
                Toast.makeText(MainActivity.this, "Interstitial Ad Dismissed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClicked() { // Called when user click on AD
                Toast.makeText(MainActivity.this, "Tracked Interstitial Ad click", Toast.LENGTH_SHORT).show();

            }
        });
        interstitialAd.loadAd(); // Call to load AD
    }

public void showInterstitial(View view) {
    if (iAd != null)
        InterstitialAd.show(iAd); // Call to show AD
    else
        Toast.makeText(this, "The Interstitial AD not ready yet. Try again!", Toast.LENGTH_LONG).show();
}
```
