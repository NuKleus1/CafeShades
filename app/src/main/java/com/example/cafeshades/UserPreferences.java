package com.example.cafeshades;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cafeshades.models.UserData;

public class UserPreferences {
    private static SharedPreferences startPref;
    SharedPreferences.Editor startPrefEditor;
    private static UserPreferences prefInstance;

    public String emailId;

    public UserPreferences(Context context) {
        startPref = context.getSharedPreferences("Flags", 0);
    }

    public UserPreferences() {
        startPref = App.getAppContext().getSharedPreferences("Flags", 0);
    }



//    public static UserPreferences getPrefInstance() {
//        if (prefInstance == null) {
//            prefInstance = new UserPreferences();
//        }
//        return prefInstance;
//    }

    public static UserPreferences getPrefInstance(Context context) {
        if (prefInstance == null) {
            prefInstance = new UserPreferences(context);
        }
        return prefInstance;
    }

//    public static UserPreferences getPrefInstance() {
//        if (prefInstance == null) {
////            prefInstance = new UserPreferences();
//        }
//        return prefInstance;
//    }

    private void openEditor() {
        startPrefEditor = startPref.edit();
    }


    public boolean getLoggedInFlag() {

        return startPref.getBoolean("LoggedInFlag", false);
    }

    public void setLoggedInFlag(boolean logInFlag) {
        openEditor();
        startPrefEditor.putBoolean("LoggedInFlag", logInFlag);
        startPrefEditor.apply();
    }

    public boolean getDatabaseCreated() {
        return startPref.getBoolean("DatabaseCreated", false);
    }

    public void setDatabaseCreated(boolean databaseCreated) {
        openEditor();
        startPrefEditor.putBoolean("DatabaseCreated", databaseCreated);
        startPrefEditor.apply();
    }

    public boolean getApplicationOpenFirstTime() {
        return startPref.getBoolean("ApplicationOpenFirstTime", false);
    }

    public void setApplicationOpenFirstTime(boolean applicationOpenFirstTime) {
        openEditor();
        startPrefEditor.putBoolean("ApplicationOpenFirstTime", false);
        startPrefEditor.apply();
    }

    public String getToken() {
        return startPref.getString("FCMToken", "wer");
    }

    public void setToken(String token) {
        openEditor();
        startPrefEditor.putString("FCMToken", token);
        startPrefEditor.apply();
    }


    //START User Data

    public String getUserId() {
        return startPref.getString("userId", "");
    }

    public void setUserId(String userId) {
        openEditor();
        startPrefEditor.putString("userId", userId);
        startPrefEditor.apply();
    }

    public String getName() {
        return startPref.getString("name", "");
    }

    public void setName(String name) {
        openEditor();
        startPrefEditor.putString("name", name);
        startPrefEditor.apply();
    }

    public String getBuildingName() {
        return startPref.getString("buildingName", "");
    }

    public void setBuildingName(String buildingName) {
        openEditor();
        startPrefEditor.putString("buildingName", buildingName);
        startPrefEditor.apply();
    }

    public String getFloorNumber() {
        return startPref.getString("floorNumber", "");
    }

    public void setFloorNumber(String floorNumber) {
        openEditor();
        startPrefEditor.putString("floorNumber", floorNumber);
        startPrefEditor.apply();
    }

    public String getOfficeNumber() {
        return startPref.getString("officeNumber", "");
    }

    public void setOfficeNumber(String officeNumber) {
        openEditor();
        startPrefEditor.putString("officeNumber", officeNumber);
        startPrefEditor.apply();
    }

    public String getLandmark() {
        return startPref.getString("landmark", "");
    }

    public void setLandmark(String landmark) {
        openEditor();
        startPrefEditor.putString("landmark", landmark);
        startPrefEditor.apply();
    }

    public String getMobileNumber() {
        return startPref.getString("mobileNumber", "");
    }

    public void setMobileNumber(String mobileNumber) {
        openEditor();
        startPrefEditor.putString("mobileNumber", mobileNumber);
        startPrefEditor.apply();
    }

    public String getEmailId() {
        return startPref.getString("emailId", "");
    }

    public void setEmailId(String emailId) {
        openEditor();
        startPrefEditor.putString("emailId", emailId);
        startPrefEditor.apply();
    }

    //START User Data
    public UserData.User getUserData() {
        UserData.User user = new UserData().getUser();
        user.setUserId(startPref.getString("userId", ""));
        user.setName(startPref.getString("name", ""));
        user.setBuildingName(startPref.getString("buildingName", ""));
        user.setFloorNumber(startPref.getString("floorNumber", ""));
        user.setOfficeNumber(startPref.getString("officeNumber", ""));
        user.setMobileNumber(startPref.getString("mobileNumber", ""));
        user.setLandmark(startPref.getString("landmark", ""));
        return user;
    }

    public void setUserData(UserData.User user) {
        openEditor();
        startPrefEditor.putString("userId", user.getUserId());
        startPrefEditor.putString("name", user.getName());
        startPrefEditor.putString("buildingName", user.getBuildingName());
        startPrefEditor.putString("floorNumber", user.getFloorNumber());
        startPrefEditor.putString("officeNumber", user.getOfficeNumber());
        startPrefEditor.putString("mobileNumber", user.getMobileNumber());
        startPrefEditor.putString("landmark", user.getLandmark());
        startPrefEditor.apply();
    }
    //END User Data
}