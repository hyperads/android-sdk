package com.hyperadx.had_sdk_v2.interstitial_ad_sample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hyperadx.had_sdk_v2.R;
import com.hyperadx.hypernetwork.ads.Ad;
import com.hyperadx.hypernetwork.ads.AdError;
import com.hyperadx.hypernetwork.ads.InterstitialAd;
import com.hyperadx.hypernetwork.ads.InterstitialAdListener;
import com.hyperadx.hypernetwork.ads.VideoInterstitialAd;

public class InterstitialFragment extends Fragment implements InterstitialAdListener {

    private TextView interstitialAdStatusLabel;
    private Button loadInterstitialButton;
    private Button showInterstitialButton;
    private Button loadInterstitialVideoButton;
    private Button showInterstitialVideoButton;
    private VideoInterstitialAd videoInterstitialAd;

    private InterstitialAd interstitialAd;

    private String statusLabel = "";

    public static InterstitialFragment newInstance() {
        InterstitialFragment fragment = new InterstitialFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interstitial, container, false);

        interstitialAdStatusLabel = (TextView) view.findViewById(R.id.interstitialAdStatusLabel);

        loadInterstitialButton = (Button) view.findViewById(R.id.loadInterstitialButton);
        showInterstitialButton = (Button) view.findViewById(R.id.showInterstitialButton);
        loadInterstitialVideoButton = (Button) view.findViewById(R.id.loadInterstitialVideoButton);
        showInterstitialVideoButton = (Button) view.findViewById(R.id.showInterstitialVideoButton);



        loadInterstitialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadInterstitial();
            }
        });

        showInterstitialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInterstitial();
            }


        });

        loadInterstitialVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadInterstitialVideo();
            }
        });

        showInterstitialVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInterstitialVideo();
            }
        });


        return view;
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

    private void loadInterstitial() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
            interstitialAd = null;
        }
        setLabel("Loading interstitial ad...");

        // Create the interstitial unit with a placement ID.
        interstitialAd = new InterstitialAd(InterstitialFragment.this.getActivity(), getString(R.string.interstitialAdPlacement));

        // Set a listener to get notified on changes or when the user interact with the ad.
        interstitialAd.setAdListener(InterstitialFragment.this);

        // Load a new interstitial.
        interstitialAd.loadAd();

    }


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

    private void setLabel(String label) {
        statusLabel = label;
        if (interstitialAdStatusLabel != null) {
            interstitialAdStatusLabel.setText(statusLabel);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (interstitialAd != null) {
            interstitialAd.destroy();
            interstitialAd = null;
        }
    }
}