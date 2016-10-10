package hyperadx.sample;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyperadx.lib.sdk.nativeads.Ad;
import com.hyperadx.lib.sdk.nativeads.AdListener;
import com.hyperadx.lib.sdk.nativeads.HADNativeAd;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {


    private List<Movie> moviesList;
    private Activity activity;
    private static final int AD_TYPE = 1;
    private static final int CONTENT_TYPE = 0;

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
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        if (getItemViewType(position) == AD_TYPE) {

            final HADNativeAd nativeAd = new HADNativeAd(activity,
                    activity.getString(R.string.Placement)

            ); //Native AD constructor

            nativeAd.setContent("title,icon,description"); // Set content to load
            nativeAd.setAdListener(new AdListener() { // Add Listeners

                @Override
                public void onAdLoaded(Ad ad) { // Called when AD is Loaded
                    Toast.makeText(activity, "Native ad loaded", Toast.LENGTH_SHORT).show();

                    holder.ivIcon.setVisibility(View.VISIBLE);


                    nativeAd.registerViewForInteraction(ad, holder.rlRoot); // Configuring your view

                    //  Setting the Text.
                    holder.title.setText(ad.getTitle());
                    holder.genre.setText(ad.getDescription());
                    // Downloading and setting the ad icon.
                    HADNativeAd.downloadAndDisplayImage(holder.ivIcon, ad.getIcon_url());

                }

                @Override
                public void onError(Ad nativeAd, String error) { // Called when load is fail
                    Toast.makeText(activity, "Native Ad failed to load with error: " + error, Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onAdClicked() { // Called when user click on AD
                    Toast.makeText(activity, "Tracked Native Ad click", Toast.LENGTH_SHORT).show();

                }
            });

            nativeAd.loadAd(); // Call to load AD


        } else {

            holder.ivIcon.setVisibility(View.GONE);

            Movie movie = moviesList.get(position);
            holder.title.setText(movie.getTitle());
            holder.genre.setText(movie.getGenre());
            holder.year.setText(movie.getYear());
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 5 == 0) // Each 5th item is AD
            return AD_TYPE;
        return CONTENT_TYPE;
    }
}
