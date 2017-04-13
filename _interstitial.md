# Interstitial ads

Here is the sample how to monetize your Android apps with the interstitial ads. See [Supported Ad types](https://github.com/hyperads/android-sdk#supported-ad-types) for information about other supported ad formats.

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

* Then, create a function that requests a interstitial ad. The SDK will log the impression and handle the click automatically:

```java

    private void loadInterstitial() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
            interstitialAd = null;
        }
        setLabel("Loading interstitial ad...");

        // Create the interstitial unit with a placement ID.
        interstitialAd = new InterstitialAd(InterstitialFragment.this.getActivity(), "W93593Xw");

        // Set a listener to get notified on changes or when the user interact with the ad.
        interstitialAd.setAdListener(InterstitialFragment.this);

        // Load a new interstitial.
        interstitialAd.loadAd();

    }

     private void showInterstitial() {
            if (interstitialAd == null || !interstitialAd.isAdLoaded()) {
                // Ad not ready to show.
                setLabel("Ad not loaded. Click load to request an ad.");
            } else {
                // Ad was loaded, show it!
                interstitialAd.show();
                setLabel("");
            }
        }

    private void setLabel(String label) {
                statusLabel = label;
                if (interstitialAdStatusLabel != null) {
                    interstitialAdStatusLabel.setText(statusLabel);
                }
            }


// Methods that overrided by implementation of InterstitialAdListener

       @Override
       public void onDestroy() {
           if (interstitialAd != null) {
               interstitialAd.destroy();
               interstitialAd = null;
           }
           super.onDestroy();
       }

       @Override
       public void onError(Ad ad, AdError error) {
           if (ad == interstitialAd) {
               setLabel("Interstitial ad failed to load: " + error.getErrorMessage());
           }
       }

       @Override
       public void onAdLoaded(Ad ad) {
           if (ad == interstitialAd) {
               setLabel("Ad loaded. Click show to present!");
           }
       }

       @Override
       public void onInterstitialDisplayed(Ad ad) {
           Toast.makeText(this.getActivity(), "Interstitial Displayed", Toast.LENGTH_SHORT).show();
       }

       @Override
       public void onInterstitialDismissed(Ad ad) {
           Toast.makeText(this.getActivity(), "Interstitial Dismissed", Toast.LENGTH_SHORT).show();

           // Cleanup.
           interstitialAd.destroy();
           interstitialAd = null;
       }

       @Override
       public void onAdClicked(Ad ad) {
           Toast.makeText(this.getActivity(), "Interstitial Clicked", Toast.LENGTH_SHORT).show();
       }

       @Override
       public void onVideoCompleted(Ad ad) {

       }

```
