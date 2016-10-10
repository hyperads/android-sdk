## Native Ads

The Native Ad API allows you to build a customized experience for the ads you show in your app. When using the Native Ad API, instead of receiving an ad ready to be displayed, you will receive a group of ad properties such as a title, an image, a call to action, and you will have to use them to construct a custom view where the ad is shown.

Sample project:

* [Download](https://github.com/hyperads/android-sdk/releases) latest release and extract the Example app for Android.

### Set up the SDK

Add following under manifest tag to your AndroidManifest.xml:

```xml
 <uses-permission android:name="android.permission.INTERNET"/>
 <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

Put the HyperAdxSDK_xxx.jar in “libs” folder in your Android Studio or Eclipse. Add it to dependencies in build.grandle file. Also you need to add google play services.

```groove
dependencies {
    compile fileTree(include: ['*.jar'], dir: 'libs')
    compile 'com.android.support:appcompat-v7:23.4.0'
    compile 'com.android.support:support-v4:23.4.0'
    compile 'com.google.android.gms:play-services-ads:9.0.2'
    compile 'com.google.android.gms:play-services-base:9.0.2'
}
```

Then, create a function that requests a native ad:

```java
private void showNativeAd() {
    adFrame = (FrameLayout) findViewById(R.id.adContent);
    nativeAd = new HADNativeAd(this, "YOUR_PLACEMENT_ID"); //Native AD constructor
    nativeAd.setContent("title,icon,main,description"); // Set content to load
    nativeAd.setAdListener(new AdListener() { // Add Listeners
        @Override
        public void onAdLoaded(Ad ad) { // Called when AD is Loaded

        }
        @Override
        public void onError(Ad nativeAd, String error) { // Called when load is fail

        }

        @Override
        public void onAdClicked() { // Called when user click on AD

        }
    });
    nativeAd.loadAd(); // Call to load AD
}
```

The next step is to extract the ad metadata and use its properties to build your customized native UI. You can either create your custom view in a layout .xml, or you can add elements in code. The custom layout .xml. For example:

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" >
    <ImageView
        android:id="@+id/ivIcon"
        android:layout_width="50dp"
        android:layout_height="50dp"
        android:layout_alignParentLeft="true"
        android:layout_alignParentTop="true" />
    <TextView
        android:id="@+id/tvTitle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentTop="true"
        android:layout_toRightOf="@+id/ivIcon"
        android:paddingLeft="10dp"
        android:textSize="18sp"
        android:textStyle="bold"
        android:typeface="monospace" />
    <ImageView
        android:id="@+id/ivImage"
        android:layout_width="480dp"
        android:layout_height="168dp"
        android:layout_alignParentLeft="true"
        android:layout_below="@+id/ivIcon" />
    <TextView
        android:id="@+id/tvDescription"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentLeft="true"
        android:layout_below="@+id/ivImage" />
</RelativeLayout>
```

Now you can use this  layout .xml as a frame. For example:

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin">
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!" />
    <FrameLayout
        android:id="@+id/adContent"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true" >
    </FrameLayout>
</RelativeLayout>
```

Modify the `onAdLoaded` function above to retrieve the ad properties. The SDK will log the impression and handle the click automatically.

```java
private NativeAd nativeAd;
private View AdView;
private FrameLayout adFrame; //FrameLayout with all views that you need
-------------------------------------------------------------------------
@Override
public void onAdLoaded(Ad ad) { // Called when AD is Loaded
    Toast.makeText(MainActivity.this, "Native ad loaded", Toast.LENGTH_SHORT).show();
    AdView = nativeAd.getNativeAdView(ad, R.layout.native_ad_layout); // Registering view for AD
    adFrame.addView(AdView); //Adding view to frame
    // Create native UI using the ad metadata.
    TextView tvTitle = (TextView) AdView.findViewById(R.id.tvTitle);
    TextView tvDescription = (TextView) AdView.findViewById(R.id.tvDescription);
    ImageView ivIcon = (ImageView) AdView.findViewById(R.id.ivIcon);
    ImageView ivImage = (ImageView) AdView.findViewById(R.id.ivImage);
    // Setting the Text.
    tvTitle.setText(ad.getTitle());
    tvDescription.setText(ad.getDescription());
    // Downloading and setting the ad icon.
    HADNativeAd.downloadAndDisplayImage(ivIcon, ad.getIcon_url());
    // Download and setting the cover image.
    HADNativeAd.downloadAndDisplayImage(ivImage, ad.getImage_url());

}
```

If you want to use Native AD in `RecyclerView` you should use `registerViewForInteraction(Ad ad, View adView)` method instead of `getNativeAdView(Ad ad, int ResID)`

For example:

```java

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

```
