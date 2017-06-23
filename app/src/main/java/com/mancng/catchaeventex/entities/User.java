package com.mancng.catchaeventex.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class User implements Parcelable {

    public String email;
    public String userName;
    public String userPicture;
    private Boolean hasLoggedInWithPassword;
    private HashMap<String, Object> dateJoined = null;

    //Make empty constructor for Firebase
    public User() {

    }

    public User(String email, String userName, String userPicture, Boolean hasLoggedInWithPassword, HashMap<String, Object> dateJoined) {
        this.email = email;
        this.userName = userName;
        this.userPicture = userPicture;
        this.hasLoggedInWithPassword = hasLoggedInWithPassword;
        this.dateJoined = dateJoined;
    }

    public User(Parcel in) {
        email = in.readString();
        userName = in.readString();
        userPicture = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public Boolean getHasLoggedInWithPassword() {
        return hasLoggedInWithPassword;
    }

    public HashMap<String, Object> getDateJoined() {
        return dateJoined;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(userName);
        dest.writeString(userPicture);
    }
}
