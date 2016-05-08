package sample.dispplyadsample;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dispply.lib.sdk.interstitialads.InterstitialAd;
import com.dispply.lib.sdk.interstitialads.InterstitialAdListener;
import com.dispply.lib.sdk.nativeads.Ad;
import com.dispply.lib.sdk.nativeads.AdListener;
import com.dispply.lib.sdk.nativeads.NativeAd;


public class MainActivity extends AppCompatActivity {

    private NativeAd nativeAd;

    private InterstitialAd interstitialAd;

    private View AdView;
    private FrameLayout adFrame;
    private com.dispply.lib.sdk.interstitialads.Ad iAd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(dispply.com.dispplyadssample.R.layout.activity_main);

        showNativeAd();

        loadInterstitialAd();

    }


    //--------NATIVE AD-------------//

    private void showNativeAd() {

        adFrame = (FrameLayout) findViewById(dispply.com.dispplyadssample.R.id.adContent);

        nativeAd = new NativeAd(this, getString(dispply.com.dispplyadssample.R.string.adPlacement)); //Native AD constructor

        nativeAd.setContent("title,icon,main,description"); // Set content to load
        nativeAd.setAdListener(new AdListener() { // Add Listeners

            @Override
            public void onAdLoaded(Ad ad) { // Called when AD is Loaded
                Toast.makeText(MainActivity.this, "Native ad loaded", Toast.LENGTH_SHORT).show();

                AdView = nativeAd.getNativeAdView(ad, dispply.com.dispplyadssample.R.layout.native_ad_layout); // Registering view for AD
                adFrame.addView(AdView); //Adding view to frame

                // Create native UI using the ad metadata.
                TextView tvTitle = (TextView) AdView.findViewById(dispply.com.dispplyadssample.R.id.tvTitle);
                TextView tvDescription = (TextView) AdView.findViewById(dispply.com.dispplyadssample.R.id.tvDescription);
                ImageView ivIcon = (ImageView) AdView.findViewById(dispply.com.dispplyadssample.R.id.ivIcon);
                ImageView ivImage = (ImageView) AdView.findViewById(dispply.com.dispplyadssample.R.id.ivImage);

                // Setting the Text.
                tvTitle.setText(ad.getTitle());
                tvDescription.setText(ad.getDescription());
                // Downloading and setting the ad icon.
                NativeAd.downloadAndDisplayImage(ivIcon, ad.getIcon_url());
                // Download and setting the cover image.
                NativeAd.downloadAndDisplayImage(ivImage, ad.getImage_url());


            }

            @Override
            public void onError(Ad nativeAd, String error) { // Called when load is fail
                Toast.makeText(MainActivity.this, "Native Ad failed to load with error: " + error, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAdClicked() { // Called when user click on AD
                Toast.makeText(MainActivity.this, "Tracked Native Ad click", Toast.LENGTH_SHORT).show();
                adFrame.removeView(AdView);
            }
        });

        nativeAd.loadAd(); // Call to load AD
    }


    //--------INTERSTITIAL AD-------------//

    private void loadInterstitialAd() {
        interstitialAd = new InterstitialAd(this, getString(dispply.com.dispplyadssample.R.string.adPlacement)); //Interstitial AD constructor
        interstitialAd.setAdListener(new InterstitialAdListener() { // Set Listener
            @Override
            public void onAdLoaded(com.dispply.lib.sdk.interstitialads.Ad ad) { // Called when AD is Loaded
                iAd = ad;
                Toast.makeText(MainActivity.this, "Interstitial Ad loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(com.dispply.lib.sdk.interstitialads.Ad Ad, String error) { // Called when load is fail
                Toast.makeText(MainActivity.this, "Interstitial Ad failed to load with error: " + error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInterstitialDisplayed() { // Called when Ad was impressed
                Toast.makeText(MainActivity.this, "Tracked Interstitial Ad impression", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInterstitialDismissed(com.dispply.lib.sdk.interstitialads.Ad ad) { // Called when Ad was dissnissed by user
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


}
