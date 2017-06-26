# Catcha Event

This is a time matching application with chat functionalities. Users get to pick a number of connected friends that he/she wants to simply hang out or coordinate with. A matched event will be generated if selected friend(s) has an event created that overlaps within a chosen time frame. The app also includes chat capabilities for every matched event. There the users can further communicate with each other about specific details of the event, driving instructions, venue, etc. This application was built with Firebase Authentication, Database, Storage, Analytics and Crashytics. It also uses Joda Time, RX Java 2, Butter Knife and Roughike Bottombar libraries with the backend powered by node.js.

Here are some sameple code from this project:

[POJO with Parcelable](https://github.com/mancng/catchaeventex/blob/master/app/src/main/java/com/mancng/catchaeventex/entities/User.java) <br />
[Shared Preference](https://github.com/mancng/catchaeventex/blob/master/app/src/main/java/com/mancng/catchaeventex/Infrastructure/Utils.java) <br />
[Push Notification](https://github.com/mancng/CatchaEventEx/blob/master/app/src/main/java/com/mancng/catchaeventex/notifications/FriendRequestMessagingServices.java) <br />
[Base Fragment Activity](https://github.com/mancng/catchaeventex/blob/master/app/src/main/java/com/mancng/catchaeventex/activities/BaseFragmentActivity.java) <br />
[Login Authentication](https://github.com/mancng/catchaeventex/blob/master/app/src/main/java/com/mancng/catchaeventex/fragments/LoginFragment.java) <br />
[Dialog Fragment](https://github.com/mancng/catchaeventex/blob/master/app/src/main/java/com/mancng/catchaeventex/dialogs/DeleteEventDialogFragment.java) <br />
[Time Picker Dialog](https://github.com/mancng/catchaeventex/blob/master/app/src/main/java/com/mancng/catchaeventex/dialogs/TimePickerDialogFragment.java) <br />
[Date Picker Dialog](https://github.com/mancng/catchaeventex/blob/master/app/src/main/java/com/mancng/catchaeventex/dialogs/DatePickerDialogFragment.java) <br />
[Check Marshmallow Permissions for camera and external storage access](https://github.com/mancng/catchaeventex/blob/master/app/src/main/java/com/mancng/catchaeventex/Infrastructure/MarshmallowPermissions.java) <br />
[Fragment Transaction](https://github.com/mancng/catchaeventex/blob/master/app/src/main/java/com/mancng/catchaeventex/fragments/EventDetailsFragment.java) <br />
[Send task to node.js using RXJava 2](https://github.com/mancng/catchaeventex/blob/master/app/src/main/java/com/mancng/catchaeventex/services/LiveMessageServices.java) <br />
[View Pager Adapter](https://github.com/mancng/catchaeventex/blob/master/app/src/main/java/com/mancng/catchaeventex/views/FriendsViewPagerAdapter.java) <br />
[RecyclerView.ViewHolder](https://github.com/mancng/catchaeventex/blob/master/app/src/main/java/com/mancng/catchaeventex/views/InboxViewHolder.java) <br />
[RecyclerView.Adapter](https://github.com/mancng/catchaeventex/blob/master/app/src/main/java/com/mancng/catchaeventex/views/InboxAdapter.java) <br />
[Search Bar](https://github.com/mancng/catchaeventex/blob/master/app/src/main/java/com/mancng/catchaeventex/fragments/EventCreationFriendPickerFragment.java) <br />
[Layout Design](https://github.com/mancng/catchaeventex/blob/master/app/src/main/res/layout/activity_login.xml) <br />
[FrameLayout & DialogLayout](https://github.com/mancng/catchaeventex/blob/master/app/src/main/res/layout/dialog_delete_event.xml) <br />
[Espresso UI Test](https://github.com/mancng/catchaeventex/blob/master/app/src/androidTest/java/com/mancng/catchaeventex/LoginScreenTest.java)

[<img src="resources/google-play-badge150.jpg">](https://play.google.com/store/apps/details?id=com.mancng.catcha&hl=en)
