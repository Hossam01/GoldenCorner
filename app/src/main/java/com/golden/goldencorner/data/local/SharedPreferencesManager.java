package com.golden.goldencorner.data.local;

/**
 *          How to Use This Class
 *          ---------------------
 *  1 -  Use initialize methods to initialize the mContext and shared preferences
 *  2 -  Call needed method based on your need directly without take object from the class
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.golden.goldencorner.BuildConfig;
import com.golden.goldencorner.data.Utils.AppConstant;

import java.io.File;
import java.util.Map;
import java.util.Set;

public class SharedPreferencesManager {

    private static Context mContext = null;
    private static String tag = getDefaultSharedPreferencesTag();
    private static SharedPreferences sharedPreferences = null;

    public static boolean initialize(Context mContext) {
        getSharedPreferences(mContext);
        SharedPreferencesManager.mContext = mContext;
        return mContext == null ? false : true;
    }
    public static boolean initialize(Context mContext, String tag) {
        getSharedPreferences(mContext);
        SharedPreferencesManager.tag = getTag(tag);
        SharedPreferencesManager.mContext = mContext;
        return mContext == null ? false : true;
    }
    public static boolean isInitialize() {
        return sharedPreferences == null ? false : true;
    }

    private static String getTag(String tag) {
        if (!tag.isEmpty()) return tag;
        return getDefaultSharedPreferencesTag();
    }
    private static String getDefaultSharedPreferencesTag() {
        return BuildConfig.APPLICATION_ID.substring(
                BuildConfig.APPLICATION_ID.lastIndexOf(".")+1);
    }
//    private static String getDefaultSharedPreferencesTag() {
//        EncryptedSharedPreferences sharedPreferences = EncryptedSharedPreferences
//                .create(
//                        fileName,
//                        masterKeyAlias,
//                        context,
//                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//                );
//
//        SharedPreferences.Editor sharedPrefsEditor = sharedPreferences.edit();
//    }
    private static SharedPreferences getSharedPreferences(Context mContext)  throws NullPointerException {
        if (sharedPreferences == null && mContext != null)
            sharedPreferences =  mContext.getSharedPreferences(tag, Context.MODE_PRIVATE);
        return sharedPreferences;
    }
    private static SharedPreferences getSharedPreferences()  throws NullPointerException {
        if (sharedPreferences == null && mContext != null)
            sharedPreferences =  mContext.getSharedPreferences(tag, Context.MODE_PRIVATE);
        return sharedPreferences;
    }
    private static SharedPreferences.Editor getEditor() throws NullPointerException {
        return SharedPreferencesManager.getSharedPreferences().edit();
    }
    public static boolean put(String key, int value) { return getEditor().putInt(key, value).commit(); }
    public static boolean put(String key, long value) { return getEditor().putLong(key, value).commit(); }
    public static boolean put(String key, float value) { return getEditor().putFloat(key, value).commit(); }
    public static boolean put(String key, String value) { return getEditor().putString(key, value).commit(); }
    public static boolean saveCurrentLang( String value) { return getEditor().putString(AppConstant.FLAG_CURRENT_LANGUAGE, value).commit(); }
    public static boolean put(String key, boolean value) { return getEditor().putBoolean(key, value).commit(); }
    public static boolean put(String key, Set<String> stringSetValue) { return getEditor().putStringSet(key, stringSetValue).commit(); }

    public static int getInt(String key) { return getSharedPreferences().getInt(key, 0);  }
    public static long getLong(String key) { return getSharedPreferences().getLong(key, 0); }
    public static Float getFloat(String key) { return getSharedPreferences().getFloat(key, -1); }
    public static String getString(String key) { return getSharedPreferences().getString(key, null); }
    public static String getCurrentLang() { return getSharedPreferences().getString(AppConstant.FLAG_CURRENT_LANGUAGE, "ar"); }
    public static Boolean getBoolean(String key) { return getSharedPreferences().getBoolean(key, false); }
    public static Set<String> getStringSet(String key) { return getSharedPreferences().getStringSet(key, null); }
    public static Map<String, ?> getStringSet()  throws NullPointerException { return getSharedPreferences().getAll(); }

    public static boolean remove(String key) { return getEditor().remove(key).commit(); }
    public static boolean clear() { return getEditor().clear().commit(); }
    public static void clearAppData() {
        try {
            if (mContext != null) {
                Runtime runtime = Runtime.getRuntime();
                runtime.exec("pm clear "+ mContext.getPackageName());
            }
        } catch (Exception e) {
            Log.d("SharedPrefManager : ", "clearAppData() : "+e.getMessage());
        }
    }
    public static void clearAppCash() {
        try {
            if (mContext != null) {
                File cache = mContext.getCacheDir();
                File appDir = new File(cache.getParent());
                if (appDir.exists()) {
                    String[] children = appDir.list();
                    for (String s : children) {
                        if (!s.equals("lib")) {
                            deleteAppDir(new File(appDir, s));
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.d("SharedPrefManager : ", "clearAppCash() : "+e.getMessage());
        }
    }
    private static boolean deleteAppDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                if (!deleteAppDir(new File(dir, children[i]))) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}

