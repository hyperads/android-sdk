package hyperadx.sample.interstitial_ad_sample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hyperadx.lib.sdk.interstitialads.Ad;
import com.hyperadx.lib.sdk.interstitialads.HADInterstitialAd;
import com.hyperadx.lib.sdk.interstitialads.InterstitialAdListener;

import hyperadx.sample.R;

public class InterstitialAdFragment extends Fragment {

    private HADInterstitialAd interstitialAd;
    private Ad iAd = null;


    public InterstitialAdFragment() {
    }

    public static InterstitialAdFragment newInstance() {
        InterstitialAdFragment fragment = new InterstitialAdFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_interstitial, container, false);

        Button btShowInterstitial = (Button) rootView.findViewById(R.id.btShowInterstitial);
        btShowInterstitial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitial();
            }
        });

        loadInterstitialAd();

        return rootView;
    }

    private void loadInterstitialAd() {
        interstitialAd = new HADInterstitialAd(getActivity() /*Strongly recomend to use Activity context*/,
                getString(R.string.Placement)); //Interstitial AD constructor.

        interstitialAd.setAdListener(new InterstitialAdListener() { // Set Listener
            @Override
            public void onAdLoaded(com.hyperadx.lib.sdk.interstitialads.Ad ad) { // Called when AD is Loaded
                iAd = ad;
                Toast.makeText(getActivity(), "Interstitial Ad loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(com.hyperadx.lib.sdk.interstitialads.Ad Ad, String error) { // Called when load is fail
                Toast.makeText(getActivity(), "Interstitial Ad failed to load with error: " + error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInterstitialDisplayed() { // Called when Ad was impressed
                Toast.makeText(getActivity(), "Tracked Interstitial Ad impression", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInterstitialDismissed(com.hyperadx.lib.sdk.interstitialads.Ad ad) { // Called when Ad was dissnissed by user
                Toast.makeText(getActivity(), "Interstitial Ad Dismissed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdClicked() { // Called when user click on AD
                Toast.makeText(getActivity(), "Tracked Interstitial Ad click", Toast.LENGTH_SHORT).show();

            }
        });
        interstitialAd.loadAd(); // Call to load AD
    }

    private void showInterstitial() {
        if (iAd != null)
            HADInterstitialAd.show(iAd); // Call to show AD
        else
            Toast.makeText(getActivity(), "The Interstitial AD not ready yet. Try again!", Toast.LENGTH_LONG).show();
    }

}
