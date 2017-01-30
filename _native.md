## Native Ads

The HyperADX's Native Ads allows you to build a customized experience for the ads displayed in your app. When using the Native Ad API, instead of receiving an ad ready to be displayed, you will receive a group of ad properties such as a title, an image, a call to action. These properties are used to construct a custom UIView, which displays the ad.

### Set up the SDK

Sample project:

* [Download](https://github.com/hyperads/android-sdk/releases) the latest release and extract the Example app for Android.

Then please take the steps below:

* Add the following under the manifest tag to your AndroidManifest.xml:

```xml
 <uses-permission android:name="android.permission.INTERNET"/>
 <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
 <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
```

* Add following under application tag to your AndroidManifest.xml:

```xml
 <meta-data
            android:name="com.google.android.gms.version"
            android:value="@integer/google_play_services_version" />

 <service
            android:name="com.hyperadx.hypernetwork.ads.internal.vast.network.asynctask.VASTAsyncTask$Async"
            android:exported="false" />
```

* Put the hypernetwork-release.aar in “libs” folder in your Android Studio. Add it to dependencies in build.grandle file. Also you need to add flatDir support and google play services:

```groove
allprojects {
    repositories {
        jcenter()
        flatDir {
            dirs 'libs'
        }
    }
}

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])

    compile(name: 'hypernetwork-release', ext: 'aar')

    compile 'com.google.android.gms:play-services-base:10.0.1'
}
```

* Inflate the views on your Activity\Fragment in onCreate or onCreateView:

```java

 nativeAdContainer = (LinearLayout) rootView.findViewById(R.id.native_ad_container);
        pbLoad = (ProgressBar) rootView.findViewById(R.id.pbLoad);

        LayoutInflater adInflater = LayoutInflater.from(getContext());
        adView = (LinearLayout) adInflater.inflate(R.layout.ad_unit, nativeAdContainer, false);
        adView.setVisibility(View.GONE);
        nativeAdContainer.addView(adView);

```


* Then, create a function that requests a native ad:

```java
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
```

* The next step is to extract the ad metadata and use its properties to build your customized native UI. You can either create your custom view in a layout .xml, or you can add elements in the code. Example of a custom layout .xml:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/ad_unit"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@android:color/white"
    android:orientation="vertical">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:paddingBottom="10dp"
        android:paddingTop="10dp">

        <ImageView
            android:id="@+id/native_ad_icon"
            android:layout_width="50dp"
            android:layout_height="50dp" />

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical"
            android:paddingLeft="5dp">

            <TextView
                android:id="@+id/native_ad_title"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:ellipsize="end"
                android:lines="1"
                android:textColor="@android:color/black"
                android:textSize="18sp" />

            <TextView
                android:id="@+id/native_ad_body"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:ellipsize="end"
                android:lines="2"
                android:textColor="@android:color/black"
                android:textSize="15sp" />

        </LinearLayout>

    </LinearLayout>

    <com.hyperadx.hypernetwork.ads.MediaView
        android:id="@+id/native_ad_media"
        android:layout_width="match_parent"
        android:layout_height="220dp"
        android:gravity="center"
        android:visibility="visible" />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="5dp">

        <TextView
            android:id="@+id/native_ad_social_context"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight="3"
            android:ellipsize="end"
            android:lines="2"
            android:paddingRight="5dp"
            android:textColor="@android:color/black"
            android:textSize="15sp" />

        <Button
            android:id="@+id/native_ad_call_to_action"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="2"

            android:textSize="16sp"
            android:visibility="gone" />

    </LinearLayout>

</LinearLayout>

```

* Now you can use this  layout .xml as a frame. For example:

```xml
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/rlMain"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin">

    <LinearLayout
        android:id="@+id/native_ad_container"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@android:color/white"
        android:orientation="vertical"
        android:paddingBottom="5dp">


        <ProgressBar
            style="?android:attr/progressBarStyleSmall"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:visibility="gone"
            android:id="@+id/pbLoad" />
    </LinearLayout>

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Load AD"
        android:id="@+id/btLoadAd"
        android:layout_below="@+id/native_ad_container"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="20dp" />

</RelativeLayout>
```

* The best practice is destroy the Ad when you kill current screen or when user device generate onLowMemory() event.
Example :

```java
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
```

### Native Ads Manager

The Native Ads Manager is a way to pre-fetch Native Ads for your app. Use the Native Ads Manager when your user experience involves displaying multiple ads within a short amount of time, such as a vertical feed or horizontal scroll.

* Example:

```java
public class ListActivity extends AppCompatActivity implements NativeAdsManager.Listener {

    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    private NativeAdsManager listNativeAdsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listNativeAdsManager = new NativeAdsManager(this, "YOUR_PLACEMENT_ID", 5);
        listNativeAdsManager.setListener(this);

        listNativeAdsManager.loadAds();
        Toast.makeText(this, "Wait for it...", Toast.LENGTH_SHORT).show();


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new MoviesAdapter(this, movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareMovieData();
    }


    @Override
    public void onAdsLoaded() {
        Toast.makeText(this, "All ads was successfully loaded",
                Toast.LENGTH_LONG).show();
        mAdapter.addNativeAdsManager(listNativeAdsManager);
    }

    @Override
    public void onAdError(AdError adError) {
        Toast.makeText(this, "Native ads manager failed to load: " + adError.getErrorMessage(),
                Toast.LENGTH_SHORT).show();
    }


    private void prepareMovieData() {

        Movie movie = new Movie("Mad Max: Fury Road", "Action & Adventure", "2015");

        for(int i = 0; i < 100; i++) { //Just fetch some dummy data
          movieList.add(movie);
        }

        mAdapter.notifyDataSetChanged();
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
        if (listNativeAdsManager != null) {
            listNativeAdsManager.destroy();
            listNativeAdsManager = null;
        }
    }
}
```

* Now create RecyclerView Adapter:

```java

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

```