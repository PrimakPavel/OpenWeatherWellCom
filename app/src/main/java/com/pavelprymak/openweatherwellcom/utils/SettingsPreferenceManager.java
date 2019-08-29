package com.pavelprymak.openweatherwellcom.utils;

import android.content.Context;
import android.content.SharedPreferences;

import javax.annotation.Nullable;

public class SettingsPreferenceManager {
    private static final String APP_PREFERENCES = "settingsWeatherApp";
    private static final String APP_PREFERENCES_CITY_NAME = "settingsCityName";
    private SharedPreferences mSettings;

    public SettingsPreferenceManager(Context context) {
        if (context != null) {
            mSettings = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        }
    }

    public void saveCityName(@Nullable String cityName) {
        if (mSettings != null) {
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString(APP_PREFERENCES_CITY_NAME, cityName);
            editor.apply();
        }
    }

    @Nullable
    public String getCityName() {
        if (mSettings == null) return null;
        return mSettings.getString(APP_PREFERENCES_CITY_NAME, null);
    }
}
