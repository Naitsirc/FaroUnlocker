package es.devtr.farounlocker.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    private final static String PREFERENCES_NAME = "Preferences";

    public static void save(String key, String value, Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String read(String key, String defaul, Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPref.getString(key, defaul);
    }
}
