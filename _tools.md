## Targeting

Customer segmentation is the practice of dividing a customer base into groups of individuals that are similar in specific ways relevant to marketing, such as age, gender, interests and spending habits. You can build custom (or use predefined) segments from your users. This is potentially increase your monetization. 

### AdRequest

The AdRequest object collects targeting information to be sent with an ad request.

* Keywords

If your app already knows a user's interests, it can provide that information in the ad request for targeting purposes.

```java
AdRequest request = new AdRequest.Builder()
        .addKeyword("sport")
        .build();
```

* Gender

If your app already knows a user's gender, it can provide that information in the ad request for targeting purposes. The information is also forwarded to ad network mediation adapters if mediation is enabled.

```java
AdRequest request = new AdRequest.Builder()
        .setGender(AdRequest.GENDER_FEMALE)
        .build();
```

* Birthday

If your app already knows a user's birthday, it can provide that information in the ad request for targeting purposes. This information is also forwarded to ad network mediation adapters if mediation is enabled.

```java
AdRequest request = new AdRequest.Builder()
        .setBirthday(new GregorianCalendar(1985, 1, 1).getTime())
        .build();
```


### Load an ad with targeting

For any type of ad you can call method setAdRequest(AdRequest). Do it before call loadAd() method.
Example:

```java
 nativeAd.setAdRequest(
                new AdRequest.Builder()
                        .addKeyword("health")
                        .addKeyword("money")
                        .addKeyword("beauty")
                        .setGender(AdRequest.GENDER_MALE)
                        .setBirthday(new Date())
                        .addCustomTargeting("CustomTargetSingle", "testTargetSingle")
                        .setContentUrl("http://hyperadx.com/")
                        .setIsDesignedForFamilies(true)
                        .tagForChildDirectedTreatment(true)
                        .build());
```


## User segmentation [alpha]

Customer segmentation is the practice of dividing a customer base into groups of individuals that are similar in specific ways relevant to marketing, such as age, gender, interests and spending habits. You can build custom (or use predefined) segments from your users. This is potentially increase your monetization. 

For building segments based on in-app events you need to notify our backend for every event occured in your app.

Events sending is made very easy. Just call the method `sendEvent(Context, "YOUR_TOKEN", event_code)` from `HADEvent` class.

For example:

```java

 HADEvent hadEvent = new HADEvent(this);
        hadEvent.setToken("TOKEN");
        hadEvent.sendEvent(Event.ECOMMERCE_PURSHASE);
        
        // Or just
        
        HADEvent.sendEvent(this, "TOKEN", Event.GAMING_ACHIEVEMENT_UNLOCKED); //That's all!

```

### Event codes:

Authenticate events
- 101 Registration
- 102 Login
- 103 Open

eCommerce events

- 201 Add to Wishlist
- 202 Add to Cart
- 203 Added Payment Info
- 204 Reservation
- 205 Checkout Initiated
- 206 Purchase

Content events

- 301 Search
- 302 Content View

Gaming events

- 401 Tutorial Completed
- 402 Level Achieved
- 403 Achievement Unlocked
- 404 Spent Credit

Social events

- 501 Invite
- 502 Rated
- 504 Share