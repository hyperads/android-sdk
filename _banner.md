# Banner ads

This document describes how to monetize your Android apps with the banner ads. Please see [Supported Ad types](https://github.com/hyperads/android-sdk#supported-ad-types) for infomation about other supported ad formats.

### Set up the SDK

Sample project:

* [Download](https://github.com/hyperads/android-sdk/releases) the latest release and extract the Example app for Android.

Then please take the steps below:

* Add the following under manifest tag to your AndroidManifest.xml:

```xml
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```

* Add following under application tag to your AndroidManifest.xml:

```xml
 <meta-data
            android:name="com.google.android.gms.version"
            android:value="@integer/google_play_services_version" />
            
 <activity
            android:name="com.hyperadx.hypernetwork.ads.internal.interstitial.HadNetworkActivity"
            android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize" />

 <service
            android:name="com.hyperadx.hypernetwork.ads.internal.vast.network.asynctask.VASTAsyncTask$Async"
            android:exported="false" />
```

* Put the hypernetwork-release.aar in “libs” folder in your Android Studio. Add it to dependencies in build.grandle file. Also you need to add flatDir support and google play services:

```groove
allprojects {
    repositories {
        jcenter()
        flatDir {
            dirs 'libs'
        }
    }
}

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])

    compile(name: 'hypernetwork-release', ext: 'aar')

    compile 'com.google.android.gms:play-services-base:10.0.1'
}
```

* Create the holder for banner in your layout:

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">


    <RelativeLayout
        android:id="@+id/adViewContainer"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:layout_alignParentBottom="true" />

</RelativeLayout>
```

```java
RelativeLayout adViewBannerContainer = (RelativeLayout) rootView.findViewById(R.id.adViewContainer);
```

* Then, create a function that requests a banner ad. The SDK will log the impression and handle the click automatically.

```java
    private void showBannerAd() {

          adViewBanner = new BannerAd(this.getActivity(), getString(R.string.bannerAdPlacement),
                        AdSize.BANNER_300_250);

                // Set a listener to get notified on changes or when the user interact with the ad.
                adViewBanner.setAdListener(this);

                // Initiate a request to load an ad.
                adViewBanner.loadAd();
```

* For the last step just realize AdListener methods.

```java
    @Override
    public void onError(Ad ad, AdError adError) {
        if (ad == adViewBanner) {
            Toast.makeText(this.getActivity(), "Banner Ad failed to load with error: " + adError.getErrorMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAdLoaded(Ad ad) {
        if (ad == adViewBanner) {
            // Add banner into container
            adViewBannerContainer.addView(adViewBanner);
        }
    }

    @Override
    public void onAdClicked(Ad ad) {
        Toast.makeText(this.getActivity(), "Ad Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        destroyAd();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        destroyAd();
    }

    private void destroyAd() {
        if (adViewBanner != null) {
            adViewBanner.destroy();
            adViewBanner = null;
        }
    }
```