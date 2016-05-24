package com.h3c.afastproject.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.h3c.afastproject.AFastProject;

/**
 * 用于纪录App的SP,不会因为退出登录而清空
 * Created by H3c on 16/5/24.
 */

public class AppSPreferences {
    private static AppSPreferences instance;
    private SharedPreferences mSharedPreferences;

    private AppSPreferences() {
        mSharedPreferences = AFastProject.getApplicationContext()
                .getSharedPreferences("app_sp_data", Context.MODE_PRIVATE);
    }

    public static AppSPreferences getInstance() {
        if(instance == null) {
            instance = new AppSPreferences();
        }

        return instance;
    }

    public void putString(String key, String value) {
        mSharedPreferences.edit().putString(key, value).apply();
    }

    public String getString(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    public void putLong(String key, Long value) {
        mSharedPreferences.edit().putLong(key, value).apply();
    }

    public Long getLong(String key, Long defaultValue) {
        return mSharedPreferences.getLong(key, defaultValue);
    }

    public void putFloat(String key, Float value) {
        mSharedPreferences.edit().putFloat(key, value).apply();
    }

    public Float getFloat(String key, Float defaultValue) {
        return mSharedPreferences.getFloat(key, defaultValue);
    }

    public void putInt(String key, Integer value) {
        mSharedPreferences.edit().putInt(key, value).apply();
    }

    public Integer getInt(String key, Integer defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }

    public void clear(String key) {
        mSharedPreferences.edit().remove(key).apply();
    }

    public void clearAll() {
        mSharedPreferences.edit().clear().apply();
    }
}
