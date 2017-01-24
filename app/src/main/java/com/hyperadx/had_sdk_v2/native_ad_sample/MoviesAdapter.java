package com.hyperadx.had_sdk_v2.native_ad_sample;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyperadx.had_sdk_v2.R;
import com.hyperadx.hypernetwork.ads.Ad;
import com.hyperadx.hypernetwork.ads.AdError;
import com.hyperadx.hypernetwork.ads.AdListener;
import com.hyperadx.hypernetwork.ads.NativeAd;
import com.hyperadx.hypernetwork.ads.NativeAdsManager;

import java.util.List;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private String TAG = "HyperADX Native in list";
    private List<Movie> moviesList;
    private Activity activity;
    private static final int AD_TYPE = 1;
    private static final int CONTENT_TYPE = 0;
    private LayoutInflater inflater;
    private NativeAdsManager mNativeAdsManager;

    public void addNativeAdsManager(NativeAdsManager nativeAdsManager) {
        this.mNativeAdsManager = nativeAdsManager;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
        public RelativeLayout rlRoot;
        public ImageView ivIcon;

        public MyViewHolder(View view) {
            super(view);
            ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);
            rlRoot = (RelativeLayout) view.findViewById(R.id.rlRoot);
        }
    }


    public MoviesAdapter(Activity activity, List<Movie> moviesList) {
        this.moviesList = moviesList;
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        if (getItemViewType(position) == AD_TYPE && mNativeAdsManager != null && mNativeAdsManager.hasNext()) {

            final NativeAd nativeAd = mNativeAdsManager.nextNativeAd();

            if (nativeAd == null) {
                showData(holder, position);
                return;
            }

            final View adView = inflater.inflate(R.layout.ad_unit, holder.rlRoot, false);
            holder.rlRoot.addView(adView);

            // Unregister last ad
            nativeAd.unregisterView();

            NativeAdFragment.inflateAd(nativeAd, adView);

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

            nativeAd.setAdListener(new AdListener() {
                @Override
                public void onError(Ad ad, AdError adError) {
                    Toast.makeText(activity, "Ad failed to load: " + adError.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    Toast.makeText(activity, "Success load", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onAdClicked(Ad ad) {
                    Toast.makeText(activity, "Ad Clicked", Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            showData(holder, position);
        }
    }

    private void showData(MyViewHolder holder, int position) {
        holder.ivIcon.setVisibility(View.GONE);

        Movie movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
        holder.year.setText(movie.getYear());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    @Override
    public int getItemViewType(int position) {

        switch (position) {

            case 25:
            case 35:
            case 45:
                return AD_TYPE;
            default:
                return CONTENT_TYPE;

        }

    }

}
