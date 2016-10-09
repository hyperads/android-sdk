## User segmentation [alpha]

Customer segmentation is the practice of dividing a customer base into groups of individuals that are similar in specific ways relevant to marketing, such as age, gender, interests and spending habits. You can build custom (or use predefined) segments from your users. This is potentially increase your monetization. 

For building segments based on in-app events you need to notify our backend for every event occured in your app.

Events sending is made very easy. Just call the method `sendEvent(Context, event_code)` from `HADEvent` class.

For example:

```java
HADEvent.sendEvent(this, Event.GAMING_ACHIEVEMENT_UNLOCKED);
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
