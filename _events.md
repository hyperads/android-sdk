## User segmentation [alpha]

Understanding how the users interact with your app directly impacts your ability to build effective advertising campaigns. By defining and measuring a number of in-app events you can more efficiently optimize both the functionality of your app and your advertising strategies. In-app events allow you to track users' actions, taps, purchases, shares and more in real time. As a result, these data can be used to create defined audience segments based upon users that have similar activity profiles. This is potentially increase app monetization.

For building segments based on in-app events you need to notify our backend for every event occured in your app.

Events sending is made very easy. Just call the method `sendEvent(Context, "YOUR_TOKEN", event_code)` from `HADEvent` class.

For example:

```java

 HADEvent hadEvent = new HADEvent(this);
 hadEvent.setToken("TOKEN");
 hadEvent.sendEvent(Event.ECOMMERCE_PURSHASE);
 
 // Or just
 
 HADEvent.sendEvent(this, "TOKEN", Event.GAMING_ACHIEVEMENT_UNLOCKED); 
 
 //That's all!

```

### Event codes:

The predefined situational in-app events cover wide variety of typical user actions.

**Authenticate events**

* __101__ Registration - Creating a user profile, before enabling the full functionality of the app. 
* __102__ Login - Logging into application with the existing credentials. 
* __103__ Open - Opening the applicaiton.

**eCommerce events**

* __201__ Add to Wishlist - Saving the desired product to a list to buy in the future.  
* __202__ Add to Cart - Adding a desired product into online shopping cart. 
* __203__ Added Payment Info - Specifying payment details in the user account. 
* __204__ Reservation -  Making reservation in travel and entertainment-related mobile apps.
* __205__ Checkout Initiated - Choosing the desired products and starting the purchasing process. 
* __206__ Purchase - Completing purchase for the selected products. 

**Content events**

* __301__ Search - Looking for something via a keyword in the app.
* __302__ Content View - Viewing a product or key pages, inspecting content. 

**Gaming events**

* __401__ Tutorial Completed - Finishing a lesson or instructions on how to do/achieve something.
* __402__ Level Achieved - Completing one level and proceeding to the next one 
* __403__ Achievement Unlocked - Gaining access to additional functionality; generally by completion of a particular event or level.
* __404__ Spent Credit - Applying accumulated rewards/points as payment for an item.


**Social events**

* __501__ Invite - Inviting somebody to join the application activity.
* __502__ Rated - Assigning a value to something according to a particular scale.
* __504__ Share - Giving others access to something; e.g. sending a link online content.
