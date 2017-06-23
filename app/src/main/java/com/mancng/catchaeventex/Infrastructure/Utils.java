package com.mancng.catchaeventex.Infrastructure;

public class Utils {

    //Shared preferences data
    public static final String USER_INFO_PREFERENCE = "USER_INFO_PREFERENCE";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_PICTURE = "USER_PICTURE";

    //Because Firebase doesn't allow the url to have the period from an email. We're going to change the period to a comma.
    public static String encodeEmail(String userEmail) {
        return userEmail.replace(".",",");
    }

    //Change the comma back to period on emails
    public static String decodeEmail(String userEmail) {
        return userEmail.replace(",",".");
    }

}
