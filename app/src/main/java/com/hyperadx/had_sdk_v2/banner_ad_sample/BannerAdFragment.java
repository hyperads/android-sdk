package com.hyperadx.had_sdk_v2.banner_ad_sample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hyperadx.had_sdk_v2.R;
import com.hyperadx.hypernetwork.ads.Ad;
import com.hyperadx.hypernetwork.ads.AdError;
import com.hyperadx.hypernetwork.ads.AdListener;
import com.hyperadx.hypernetwork.ads.AdSize;
import com.hyperadx.hypernetwork.ads.BannerAd;


public class BannerAdFragment extends Fragment implements AdListener {

    private String TAG = "HyperADX sample";

    private RelativeLayout adViewBannerContainer;
    private BannerAd adViewBanner;


    public BannerAdFragment() {
    }

    public static BannerAdFragment newInstance() {
        BannerAdFragment fragment = new BannerAdFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_banner, container, false);

        adViewBannerContainer = (RelativeLayout) rootView.findViewById(R.id.adViewContainer);


        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Create a banner's ad view with a unique placement ID.

        adViewBanner = new BannerAd(this.getActivity(), getString(R.string.bannerAdPlacement),
                AdSize.BANNER_300_250);

        // Set a listener to get notified on changes or when the user interact with the ad.
        adViewBanner.setAdListener(this);

        // Initiate a request to load an ad.
        adViewBanner.loadAd();

    }


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

}
