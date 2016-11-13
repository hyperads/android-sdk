#  User segmentation events

Custom ad targeting results in increazing your monetization values and obtaining maximum value from every user. HyperADX provides user segmentation feature by processing in-app events triggered by the users. Based on the user segmentation events
HyperADX automatically builds audiences and assigns high performing campaigns individually to each segment. Besides our advertisers and media buying department encourage the most engaged users by making higher bids for them.

You can send the events very easy. Just call the method 'sendEvent(Context, event_code)' from HADEvent class.
   
Example: HADEvent.sendEvent(this, Event.GAMING_ACHIEVEMENT_UNLOCKED);

## Event codes

The following event codes are implemented:

**Authenticate events**

* __101__ Registration
* __102__ Login
* __103__ Open

**eCommerce events**

* __201__ Add to Wishlist
* __202__ Add to Cart
* __203__ Added Payment Info
* __204__ Reservation
* __205__ Checkout Initiated
* __206__ Purchase

**Content events**

* __301__ Search
* __302__ Content View

**Gaming events**

* __401__ Tutorial Completed
* __402__ Level Achieved
* __403__ Achievement Unlocked
* __404__ Spent Credit

**Social events**

* __501__ Invite
* __502__ Rated
* __504__ Share
