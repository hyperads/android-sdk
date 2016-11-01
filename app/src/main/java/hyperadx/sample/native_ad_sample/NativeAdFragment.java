package hyperadx.sample.native_ad_sample;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyperadx.lib.sdk.nativeads.Ad;
import com.hyperadx.lib.sdk.nativeads.AdListener;
import com.hyperadx.lib.sdk.nativeads.HADNativeAd;

import hyperadx.sample.R;

public class NativeAdFragment extends Fragment {

    private HADNativeAd nativeAd;
    private FrameLayout adFrame;
    private View AdView;


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

        adFrame = (FrameLayout) rootView.findViewById(R.id.adContent);

        Button btListActivity = (Button) rootView.findViewById(R.id.btListActivity);

        btListActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListActivity.class);
                startActivity(intent);
            }
        });

        showNativeAd();

        return rootView;
    }


    private void showNativeAd() {


        nativeAd = new HADNativeAd(getActivity(), getString(R.string.Placement)); //Native AD constructor

        nativeAd.setContent("title,icon,main,description"); // Set content to load
        nativeAd.setAdListener(new AdListener() { // Add Listeners

            @Override
            public void onAdLoaded(Ad ad) { // Called when AD is Loaded
                Toast.makeText(getActivity(), "Native ad loaded", Toast.LENGTH_SHORT).show();

                AdView = nativeAd.getNativeAdView(ad, R.layout.native_ad_layout); // Registering view for AD
                adFrame.addView(AdView); //Adding view to frame

                // Create native UI using the ad metadata.
                TextView tvTitle = (TextView) AdView.findViewById(R.id.tvTitle);
                TextView tvDescription = (TextView) AdView.findViewById(R.id.tvDescription);
                ImageView ivIcon = (ImageView) AdView.findViewById(R.id.ivIcon);
                ImageView ivImage = (ImageView) AdView.findViewById(R.id.ivImage);
                Button btCTA = (Button) AdView.findViewById(R.id.btCTA);

                // Setting the Text.
                tvTitle.setText(ad.getTitle());
                tvDescription.setText(ad.getDescription());
                // Downloading and setting the ad icon.
                HADNativeAd.downloadAndDisplayImage(ivIcon, ad.getIcon_url());
                // Download and setting the cover image.
                HADNativeAd.downloadAndDisplayImage(ivImage, ad.getImage_url());

                if (ad.getCta() != null)
                    btCTA.setText(ad.getCta());
            }

            @Override
            public void onError(Ad nativeAd, String error) { // Called when load is fail
                Toast.makeText(getActivity(), "Native Ad failed to load with error: " + error, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAdClicked() { // Called when user click on AD
                Toast.makeText(getActivity(), "Tracked Native Ad click", Toast.LENGTH_SHORT).show();

            }
        });

        nativeAd.loadAd(); // Call to load AD
    }

}
