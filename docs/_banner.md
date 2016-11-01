# Banner ads

The HyperADX Banner ads allows you to monetize your Android apps. This guide explains how to add banner ads to your app. If you’re interested in other kinds of ad units, see the [list of available types](https://github.com/hyperads/android-sdk) .

Sample project:

* [Download](https://github.com/hyperads/android-sdk/releases) latest release and extract the Example app for Android.

### Set up the SDK

Add following under manifest tag to your AndroidManifest.xml:

```xml
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

Put the HyperADXSDK_xxx.jar in “libs” folder in your Android Studio or Eclipse. Add it to dependencies in build.grandle file. Also you need to add google play services.

```groove
dependencies {
    compile fileTree(include: ['*.jar'], dir: 'libs')
    compile 'com.android.support:appcompat-v7:23.4.0'
    compile 'com.android.support:support-v4:23.4.0'
    compile 'com.google.android.gms:play-services-ads:9.0.2'
    compile 'com.google.android.gms:play-services-base:9.0.2'
}
```

Then, create a function that requests a banner ad. The SDK will log the impression and handle the click automatically.

```java
    private void showBannerAd() {

        bannerAd = new HADBannerAd(getActivity(), getString(R.string.bannerAdPlacement), AdSize.BANNER_300_250);

        bannerAd.setAdListener(new BannerAdListener() {
            @Override
            public void onAdLoaded(com.hyperadx.lib.sdk.banner.Ad ad) {
                bannerAd.show(ad);
                adBannerFrame.addView(bannerAd);

                Toast.makeText(getActivity(), "Banner ad loaded", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(com.hyperadx.lib.sdk.banner.Ad Ad, String error) {
                Toast.makeText(getActivity(), "Native Ad failed to load with error: " + error, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onBannerDisplayed() {

            }


            @Override
            public void onAdClicked() {
                Toast.makeText(getActivity(), "Tracked banner Ad click", Toast.LENGTH_SHORT).show();
            }
        });

        bannerAd.loadAd();
    }
```
