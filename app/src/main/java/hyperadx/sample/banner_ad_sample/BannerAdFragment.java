package hyperadx.sample.banner_ad_sample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.hyperadx.lib.sdk.banner.AdSize;
import com.hyperadx.lib.sdk.banner.BannerAdListener;
import com.hyperadx.lib.sdk.banner.HADBannerAd;

import hyperadx.sample.R;

public class BannerAdFragment extends Fragment {


    private FrameLayout adBannerFrame;
    private HADBannerAd bannerAd;


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

        adBannerFrame = (FrameLayout) rootView.findViewById(R.id.adBanner);


        showBannerAd();

        return rootView;
    }


    private void showBannerAd() {


        bannerAd = new HADBannerAd(getActivity(), getString(R.string.bannerAdPlacement), AdSize.BANNER_320_50);

        bannerAd.setAdListener(new BannerAdListener() {
            @Override
            public void onAdLoaded(com.hyperadx.lib.sdk.banner.Ad ad) {
                bannerAd.show(ad);
                adBannerFrame.addView(bannerAd);

                Toast.makeText(getActivity(), "Banner ad loaded", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(com.hyperadx.lib.sdk.banner.Ad Ad, String error) {
                Toast.makeText(getActivity(), "Native Ad failed to load with error: " + error, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onBannerDisplayed() {

            }


            @Override
            public void onAdClicked() {
                Toast.makeText(getActivity(), "Tracked banner Ad click", Toast.LENGTH_SHORT).show();
            }
        });

        bannerAd.loadAd();
    }

}
