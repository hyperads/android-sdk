# Video Interstitial ads

Here is the sample how to monetize your Android apps with the video interstitial ads. See [Supported Ad types](https://github.com/hyperads/android-sdk#supported-ad-types) for information about other supported ad formats.

### Set up the SDK

Sample project:

* [Download](https://github.com/hyperads/android-sdk/releases) the latest release and extract the Example app for Android.

Then please take the steps below:

* Add following under manifest tag to your AndroidManifest.xml:

```xml
<uses-permission android:name="android.permission.INTERNET"/>
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

* Then, create a function that requests a VideoInterstitialAd. The SDK will log the impression and handle the click automatically:

```java

       private void loadInterstitialVideo() {
            if (videoInterstitialAd != null) {
                videoInterstitialAd.destroy();
                videoInterstitialAd = null;
            }
            setLabel("Loading video interstitial ad...");

            videoInterstitialAd = new VideoInterstitialAd(InterstitialFragment.this.getActivity(), getString(R.string.interstitialVideoAdPlacement));

            videoInterstitialAd.setAdListener(new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {
                    Toast.makeText(InterstitialFragment.this.getActivity(), "Interstitial Video Displayed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    Toast.makeText(InterstitialFragment.this.getActivity(), "Interstitial Video Dismissed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onVideoCompleted(Ad ad) {
                    Toast.makeText(InterstitialFragment.this.getActivity(), "Interstitial Video Completed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    if (ad == videoInterstitialAd)
                        setLabel("Interstitial video ad failed to load: " + adError.getErrorMessage());

                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (ad == videoInterstitialAd)
                        setLabel("Video Ad loaded. Click show to present!");

                }

                @Override
                public void onAdClicked(Ad ad) {
                    if (ad == videoInterstitialAd)
                        Toast.makeText(InterstitialFragment.this.getActivity(), "Interstitial Video Clicked", Toast.LENGTH_SHORT).show();
                }
            });

            videoInterstitialAd.loadAd();
        }

  private void showInterstitialVideo() {
                if (videoInterstitialAd == null || !videoInterstitialAd.isAdLoaded()) {
                    // Ad not ready to show.
                    setLabel("Video Ad not loaded. Click load to request an video ad.");
                } else {
                    // Ad was loaded, show it!
                    videoInterstitialAd.show();
                    setLabel("");
                }
            }

```

