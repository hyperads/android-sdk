package com.hyperadx.had_sdk_v2.native_ad_sample;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hyperadx.had_sdk_v2.R;
import com.hyperadx.hypernetwork.ads.Ad;
import com.hyperadx.hypernetwork.ads.AdError;
import com.hyperadx.hypernetwork.ads.AdListener;
import com.hyperadx.hypernetwork.ads.HadContent;
import com.hyperadx.hypernetwork.ads.MediaView;
import com.hyperadx.hypernetwork.ads.NativeAd;
import com.hyperadx.hypernetwork.ads.internal.server.AdRequest;

import java.util.Date;


public class NativeAdFragment extends Fragment {

    private NativeAd nativeAd;
    private String TAG = "HyperADX sample";

    private LinearLayout adView;
    private LinearLayout nativeAdContainer;
    private Button btListActivity, btLoadAd;
    private ProgressBar pbLoad;


    public NativeAdFragment() {
    }

    public static NativeAdFragment newInstance() {
        NativeAdFragment fragment = new NativeAdFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_native, container, false);

        nativeAdContainer = (LinearLayout) rootView.findViewById(R.id.native_ad_container);
        pbLoad = (ProgressBar) rootView.findViewById(R.id.pbLoad);

        LayoutInflater adInflater = LayoutInflater.from(getContext());
        adView = (LinearLayout) adInflater.inflate(R.layout.ad_unit, nativeAdContainer, false);
        adView.setVisibility(View.GONE);
        nativeAdContainer.addView(adView);

        btListActivity = (Button) rootView.findViewById(R.id.btListActivity);
        btLoadAd = (Button) rootView.findViewById(R.id.btLoadAd);

        // Start list-based example
        btListActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListActivity.class);
                startActivity(intent);
            }
        });

        // Load Native Ad
        btLoadAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadNativeAd();
                pbLoad.setVisibility(View.VISIBLE);
            }
        });


        return rootView;
    }


    private void loadNativeAd() {

        //Native AD constructor.
        nativeAd = new NativeAd(getActivity(), "YOUR_PLACEMENT_ID"); /*Strongly recommend to use Activity context*/

        // Setting content types to request
        nativeAd.setContent(
                HadContent.TITLE,
                HadContent.ICON,
                HadContent.MAIN);

        // Allow Video-Native ad type
        nativeAd.allowVideo(true);

        // Set Listener
        nativeAd.setAdListener(new AdListener() {

            @Override // Called when something goes wrong
            public void onError(Ad ad, AdError adError) {
                Toast.makeText(getContext(), "Failure load with errors " + adError.getErrorMessage(), Toast.LENGTH_LONG).show();
            }

            @Override // Called when AD is Loaded
            public void onAdLoaded(Ad ad) {

                if (nativeAd == null || nativeAd != ad) {
                    // Race condition, load() called again before last ad was displayed
                    return;
                }

                pbLoad.setVisibility(View.GONE);
                adView.setVisibility(View.VISIBLE);

                btLoadAd.setVisibility(View.GONE);

                // Unregister last ad
                nativeAd.unregisterView();

                inflateAd(nativeAd, adView); // Inflating all needed views

                nativeAd.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            switch (view.getId()) {
                                case R.id.native_ad_call_to_action:
                                    Log.d(TAG, "Call to action button clicked");
                                    break;
                                case R.id.native_ad_media:
                                    Log.d(TAG, "Main image clicked");
                                    break;
                                default:
                                    Log.d(TAG, "Other ad component clicked");
                            }
                        }
                        return false;
                    }
                });

                Toast.makeText(getContext(), "Success load", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onAdClicked(Ad ad) {
                Toast.makeText(getContext(), "Ad clicked", Toast.LENGTH_LONG).show();
            }
        });

        // Optionally you can send to us some extra information about current user and your app
        nativeAd.setAdRequest(
                new AdRequest.Builder()
                        .addKeyword("health")
                        .addKeyword("money")
                        .addKeyword("beauty")
                        .setGender(AdRequest.GENDER_MALE)
                        .setBirthday(new Date())
                        .addCustomTargeting("CustomTargetSingle", "testTargetSingle")
                        .setContentUrl("http://hyperadx.com/")
                        .setIsDesignedForFamilies(true)
                        .tagForChildDirectedTreatment(true)
                        .build());

        nativeAd.loadAd();
    }

    public static void inflateAd(NativeAd nativeAd, View adView) {

        // Create native UI using the ad metadata.
        ImageView nativeAdIcon = (ImageView) adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = (TextView) adView.findViewById(R.id.native_ad_title);
        TextView nativeAdBody = (TextView) adView.findViewById(R.id.native_ad_body);
        MediaView nativeAdMedia = (MediaView) adView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext =
                (TextView) adView.findViewById(R.id.native_ad_social_context);
        Button nativeAdCallToAction = (Button) adView.findViewById(R.id.native_ad_call_to_action);

        // Setting the Text

        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        nativeAdCallToAction.setVisibility(View.VISIBLE);
        nativeAdTitle.setText(nativeAd.getAdTitle());
        nativeAdBody.setText(nativeAd.getAdBody());

        // Downloading and setting the ad icon.
        NativeAd.Image adIcon = nativeAd.getAdIcon();
        NativeAd.downloadAndDisplayImage(adIcon, nativeAdIcon);


        nativeAd.registerViewForInteraction(adView); // Configuring your view

        nativeAdMedia.setNativeAd(nativeAd); // Configuring MediaView

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
        if (nativeAd != null) {
            nativeAd.destroy();
            nativeAd = null;
        }
    }
}
