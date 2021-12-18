package com.example.qrcovax.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    //Void Saving
    public static void save(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SESSION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    //Get Value return String
    public static String read(Context context, String name, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SESSION, Context.MODE_PRIVATE);
        return sharedPreferences.getString(name, defaultValue);
    }
}
