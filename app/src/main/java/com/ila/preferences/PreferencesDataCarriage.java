package com.ila.preferences;

import android.content.Context;
import android.content.SharedPreferences;
public class PreferencesDataCarriage {
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    public PreferencesDataCarriage(Context context, String FileName) {
        this.sharedPreferences = context.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void saveBool(String key , boolean data)
    {
        editor.putBoolean(key,data);
        editor.apply();
    }
    public void saveInt(String key, int data)
    {
        editor.putInt(key, data);
        editor.apply();
    }
    public void saveString(String key, String data)
    {
        editor.putString(key,data);
        editor.apply();
    }
    public void saveFloat(String key, float data)
    {
        editor.putFloat(key,data);
        editor.apply();
    }
    public void saveLong(String key, long data)
    {
        editor.putLong(key,data);
        editor.apply();
    }
    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }
    public boolean getBool(String key, boolean defaultValue)
    {
        return sharedPreferences.getBoolean(key, defaultValue);
    }
    public long getLong(String key, long defaultValue)
    {
        return sharedPreferences.getLong(key,defaultValue);
    }

}