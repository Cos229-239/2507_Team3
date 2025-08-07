package com.ila.settings;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

import com.ila.preferences.PreferencesDataCarriage;

public class SettingsHandler{
    private final PreferencesDataCarriage helper;
    public SettingsHandler(Context context){
        helper = new PreferencesDataCarriage(context,"SettingsPref");
    }
    public void setNightMode()
    {
        if (!isNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);//turn off nightMode
        }
    }
    //TODO:Make this happen
    public void loadPreferances()
    {
        String savedName = helper.getString("user_name", "Guest");
        int savedAge = helper.getInt("user_age", 0);
    }
    public void setNightMode(boolean set) //for preferances
    {
        if (set) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);//turn off nightMode
        }
    }
    private static boolean isNightMode() {
        int currentNightMode = AppCompatDelegate.getDefaultNightMode();
        return currentNightMode == AppCompatDelegate.MODE_NIGHT_YES;
    }


}
