package com.example.cafeshades;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private static SharedPreferences startPref;
    private static SharedPref prefInstance;
//    Context context = ;
    SharedPreferences.Editor startPrefEditor;

    public SharedPref() {
        startPref = App.getAppContext().getSharedPreferences("Flags", Context.MODE_PRIVATE);
    }

    public SharedPref(Context context) {
        startPref = context.getSharedPreferences("Flags", Context.MODE_PRIVATE);
    }

    public static SharedPref getPrefInstance(Context context) {
        if (prefInstance == null) {
            prefInstance = new SharedPref(context);
        }
        return prefInstance;
    }

    private void openEditor() {
        startPrefEditor = startPref.edit();
    }


    public boolean getLogInFlag() {

        return startPref.getBoolean("LogInFlag", false);
    }

    public void setLogInFlag(boolean logInFlag) {
        openEditor();
        startPrefEditor.putBoolean("LogInFlag", logInFlag);
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
}
