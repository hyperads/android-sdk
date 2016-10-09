package hyperadx.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.hyperadx.lib.sdk.Event;
import com.hyperadx.lib.sdk.HADEvent;
import com.hyperadx.lib.sdk.interstitialads.HADInterstitialAd;
import com.hyperadx.lib.sdk.interstitialads.InterstitialAdListener;
import com.hyperadx.lib.sdk.nativeads.Ad;
import com.hyperadx.lib.sdk.nativeads.AdListener;
import com.hyperadx.lib.sdk.nativeads.HADNativeAd;


public class MainActivity extends AppCompatActivity {

    private HADNativeAd nativeAd;

    private HADInterstitialAd interstitialAd;

    private View AdView;
    private FrameLayout adFrame;
    private com.hyperadx.lib.sdk.interstitialads.Ad iAd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showNativeAd();

        loadInterstitialAd();

        sendSomeEvent();

    }


    //--------NATIVE AD-------------//

    private void showNativeAd() {

        adFrame = (FrameLayout) findViewById(R.id.adContent);

        nativeAd = new HADNativeAd(this,
                getString(R.string.Placement)
        ); //Native AD constructor

        nativeAd.setContent("title,icon,main,description"); // Set content to load
        nativeAd.setAdListener(new AdListener() { // Add Listeners

            @Override
            public void onAdLoaded(Ad ad) { // Called when AD is Loaded
                Toast.makeText(MainActivity.this, "Native ad loaded", Toast.LENGTH_SHORT).show();

                AdView = nativeAd.getNativeAdView(ad, R.layout.native_ad_layout); // Registering view for AD
                adFrame.addView(AdView); //Adding view to frame

                // Create native UI using the ad metadata.
                TextView tvTitle = (TextView) AdView.findViewById(R.id.tvTitle);
                TextView tvDescription = (TextView) AdView.findViewById(R.id.tvDescription);
                ImageView ivIcon = (ImageView) AdView.findViewById(R.id.ivIcon);
                ImageView ivImage = (ImageView) AdView.findViewById(R.id.ivImage);

                // Setting the Text.
                tvTitle.setText(ad.getTitle());
                tvDescription.setText(ad.getDescription());
                // Setting the ad icon.
                ivIcon.setImageBitmap(ad.getIcon());
                // Setting the cover image.
                ivImage.setImageBitmap(ad.getImage());

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
            HADInterstitialAd.show(iAd); // Call to show AD
        else
            Toast.makeText(this, "The Interstitial AD not ready yet. Try again!", Toast.LENGTH_LONG).show();
    }


    private void sendSomeEvent() {


/*   Here you see how to send events notifications to HyperAdx.com

                Authenticate events
            101 Registration
            102 Login
            103 Open

            		eCommerce events
            201 Add to Wishlist
            202 Add to Cart
            203 Added Payment Info
            204 Reservation
            205 Checkout Initiated
            206 Purchase

            		Content events
            301 Search
            302 Content View

            		Gaming events
            401 Tutorial Completed
            402 Level Achieved
            403 Achievement Unlocked
            404 Spent Credit

            		Social events
            501 Invite
            502 Rated
            504 Share    */

        HADEvent.sendEvent(this, Event.GAMING_ACHIEVEMENT_UNLOCKED); //That's all!

    }

    public void showNativeInList(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}
