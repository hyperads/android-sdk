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
import com.hyperadx.hypernetwork.ads.HADReward;
import com.hyperadx.hypernetwork.ads.InterstitialAd;
import com.hyperadx.hypernetwork.ads.InterstitialAdListener;
import com.hyperadx.hypernetwork.ads.RewardedInterstitialAdListener;
import com.hyperadx.hypernetwork.ads.RewardedVideoInterstitialAd;
import com.hyperadx.hypernetwork.ads.VideoInterstitialAd;

public class InterstitialFragment extends Fragment implements InterstitialAdListener {

    private TextView interstitialAdStatusLabel;
    private Button loadInterstitialButton;
    private Button showInterstitialButton;
    private Button loadInterstitialVideoButton;
    private Button showInterstitialVideoButton;
    private Button loadRewardedVideoButton;
    private Button showRewardedVideoButton;
    private VideoInterstitialAd videoInterstitialAd;
    private RewardedVideoInterstitialAd rewardedVideoInterstitialAd;

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

        loadRewardedVideoButton = (Button) view.findViewById(R.id.loadRewardedVideoButton);
        showRewardedVideoButton = (Button) view.findViewById(R.id.showRewardedVideoButton);

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

        loadRewardedVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardedVideo();
            }
        });

        showRewardedVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRewardedVideo();
            }
        });

        return view;
    }

    private void loadRewardedVideo() {
        if (rewardedVideoInterstitialAd != null) {
            rewardedVideoInterstitialAd.destroy();
            rewardedVideoInterstitialAd = null;
        }
        setLabel("Loading rewarded video ad...");

        rewardedVideoInterstitialAd = new RewardedVideoInterstitialAd(InterstitialFragment.this.getActivity(), getString(R.string.interstitialRewardedVideoAdPlacement), "customerid" /*pass null if you not use S2S*/);

        rewardedVideoInterstitialAd.setAdListener(new RewardedInterstitialAdListener() {
            @Override
            public void onRewardedVideoCompleted(Ad ad, HADReward reward) {
                // Called when a rewarded video is completed and the user should be rewarded.
                // You can query the reward object with String getLabel(), and int getAmount().

                Toast.makeText(InterstitialFragment.this.getActivity(), String.format("Rewarded Video Completed. Now you may gift %d %s to user!", reward.getAmount(), reward.getLabel()), Toast.LENGTH_LONG).show();


            }

            @Override
            public void onVideoCompleted(Ad ad) {
                Toast.makeText(InterstitialFragment.this.getActivity(), "Rewarded Video Completed. Now you may gift some profit to user!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onInterstitialDisplayed(Ad ad) {
                Toast.makeText(InterstitialFragment.this.getActivity(), "Rewarded Video Displayed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                Toast.makeText(InterstitialFragment.this.getActivity(), "Rewarded Video Dismissed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                if (ad == rewardedVideoInterstitialAd)
                    setLabel("Rewarded Video ad failed to load: " + adError.getErrorMessage());

            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (ad == rewardedVideoInterstitialAd)
                    setLabel("Rewarded Video Ad loaded. Click show to present!");

            }

            @Override
            public void onAdClicked(Ad ad) {
                if (ad == rewardedVideoInterstitialAd)
                    Toast.makeText(InterstitialFragment.this.getActivity(), "Interstitial Video Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        rewardedVideoInterstitialAd.loadAd();
    }

    private void showRewardedVideo() {
        if (rewardedVideoInterstitialAd == null || !rewardedVideoInterstitialAd.isAdLoaded()) {
            // Ad not ready to show.
            setLabel("Rewarded Video Ad not loaded. Click load to request an video ad.");
        } else {
            // Ad was loaded, show it!
            rewardedVideoInterstitialAd.show();
            setLabel("");
        }
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
    public void onVideoCompleted(Ad ad) {

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