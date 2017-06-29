package com.Polynt.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by Alex on 9/19/2014.
 */
public class SharePref {
    public static final String PREFNAME = "Polynt";

    public static final String FIRST_FREE = "firstFree";
    public static final String SAVED_PRODUCTS = "saved_products";
    public static final String SAVED_CLIPBOARDS = "saved_clipboards";
    public static final String TEMP_CLIPBOARD = "temp_clipboard";

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences pref = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences pref = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences pref = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public static void putStringSet(Context context, String key, Set<String> value){
        SharedPreferences pref = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putStringSet(key, value);
        editor.commit();
    }

    public static Set<String> getStringSet(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        return sp.getStringSet(key, null);
    }

    public static void clear(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    public static void putFloat(Context context, String key, float value) {
        SharedPreferences pref = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public static float getInt(Context context, String key, float defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        return sp.getFloat(key, defaultValue);
    }

    public static void putLong(Context context, String key, long value){
        SharedPreferences pref = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static long getLong(Context context, String key, long defaultValue){
        SharedPreferences sp = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        return sp.getLong(key, defaultValue);
    }

    public static void remove(Context context, String key){
        SharedPreferences sp = context.getSharedPreferences(PREFNAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }
}
