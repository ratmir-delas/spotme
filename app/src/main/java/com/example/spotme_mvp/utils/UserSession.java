package com.example.spotme_mvp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.spotme_mvp.entities.User;

public class UserSession {

    private static UserSession instance;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    // Keys for storing user details
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_USER_EMAIL = "userEmail";
    private static final String KEY_USER_PROFILE_IMAGE = "userProfileImage";

    // Private constructor to ensure it is not instantiated outside the class
    private UserSession(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    // Method to get the single instance of the class
    public static synchronized UserSession getInstance(Context context) {
        if (instance == null) {
            instance = new UserSession(context.getApplicationContext());
        }
        return instance;
    }

    // Save userId
    public void setUserId(long userId) {
        editor.putLong(KEY_USER_ID, userId);
        editor.apply();
    }

    // Retrieve userId
    public long getUserId() {
        return sharedPreferences.getLong(KEY_USER_ID, -1);  // -1 is the default value if not stored
    }

    // Save userName
    public void setUserName(String userName) {
        editor.putString(KEY_USER_NAME, userName);
        editor.apply();
    }

    // Retrieve userName
    public String getUserName() {
        return sharedPreferences.getString(KEY_USER_NAME, null);  // null is the default value if not stored
    }

    // Save userEmail
    public void setUserEmail(String userEmail) {
        editor.putString(KEY_USER_EMAIL, userEmail);
        editor.apply();
    }

    // Retrieve userEmail
    public String getUserEmail() {
        return sharedPreferences.getString(KEY_USER_EMAIL, null);  // null is the default value if not stored
    }

    public void setUser(User user) {
        editor.putLong(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_NAME, user.getUsername());
        editor.putString(KEY_USER_EMAIL, user.getEmail());
        editor.apply();
    }

    public void setUserProfileImage(String profileImage) {
        editor.putString(KEY_USER_PROFILE_IMAGE, profileImage);
        editor.apply();
    }

    // Retrieve profileImage
    public String getUserProfileImage() {
        return sharedPreferences.getString(KEY_USER_PROFILE_IMAGE, null);
    }

    // Add this method to the UserSession class
    public User getUser() {
        long userId = getUserId();
        String userName = getUserName();
        String userEmail = getUserEmail();
        String userPassword = ""; // Retrieve the password if stored
        String userPhone = ""; // Retrieve the phone if stored
        String userProfileImage = getUserProfileImage();

        if (userId != -1 && userName != null && userEmail != null) {
            User user = new User(userName, userPassword, userEmail, userPhone);
            user.setId(userId);
            user.setProfileImage(userProfileImage);
            return user;
        } else {
            return null;
        }
    }

    // Clear session (remove user details)
    public void clearSession() {
        editor.remove(KEY_USER_ID);
        editor.remove(KEY_USER_NAME);
        editor.remove(KEY_USER_EMAIL);
        editor.apply();
    }
}