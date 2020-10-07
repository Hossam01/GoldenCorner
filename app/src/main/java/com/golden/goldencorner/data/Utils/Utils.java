package com.golden.goldencorner.data.Utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

import com.golden.goldencorner.data.local.SharedPreferencesManager;
import com.golden.goldencorner.data.model.Languages;
import com.golden.goldencorner.BuildConfig;
import com.google.android.material.snackbar.Snackbar;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.Locale;

public class Utils {

    public static Utils utils;
    public static Utils getInstance() {
        if (utils == null)
            utils = new Utils();
        return utils;
    }

    public void logError(String tag, String msg) {
        if (BuildConfig.DEBUG) Log.d(tag, msg);
    }
    public void logInfo(String tag, String msg) {
        if (BuildConfig.DEBUG) Log.i(tag, msg);
    }
    //region Log On Debug Mode only
    public void LogError(@NonNull String TAG, @NonNull String msg){
        if (!BuildConfig.DEBUG) Log.e(TAG, msg);
    }
    public void LogInfo(@NonNull String TAG, @NonNull String msg){
        if (!BuildConfig.DEBUG) Log.i(TAG, msg);
    }
    public void LogDebug(@NonNull String TAG, @NonNull String msg){
        if (!BuildConfig.DEBUG) Log.d(TAG, msg);
    }
    public void LogWarning(@NonNull String TAG, @NonNull String msg){
        if (!BuildConfig.DEBUG) Log.w(TAG, msg);
    }
    //endregion

    public boolean isNetworkConnected(Context mContext) {
        ConnectivityManager connMgr = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()) {
//            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
//            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            return true;
        } else {
            return false;
        }
    }
    public void showToast(Context mContext, String msg) {
        new StyleableToast.Builder(mContext)
                .backgroundColor(Color.WHITE)
                .textColor(Color.BLACK)
                .solidBackground()
                .text(msg)
                .show();
    }
    public void showSnackBar(View view, String msg, String listenerMsg, View.OnClickListener listener) {
        Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(Color.RED)
                .setActionTextColor(Color.WHITE)
                .setAction(listenerMsg, listener)
                .show();
    }
    public void showSoftKeyboard(View view){
        if(view.requestFocus()){
            InputMethodManager imm =(InputMethodManager) view.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view,InputMethodManager.SHOW_IMPLICIT);
        }
    }
    public void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0/*InputMethodManager.HIDE_IMPLICIT_ONLY*/);
    }

    public boolean setNewLocale(@NonNull Context mContext, @NonNull String language) {
        Locale localeNew = new Locale(language);
        Locale.setDefault(localeNew);

        Resources res = mContext.getResources();
        Configuration newConfig = new Configuration(res.getConfiguration());
        newConfig.locale = localeNew;
        newConfig.setLayoutDirection(localeNew);
        res.updateConfiguration(newConfig, res.getDisplayMetrics());

        newConfig.setLocale(localeNew);
        mContext.createConfigurationContext(newConfig);
        SharedPreferencesManager.put(Languages.FLAG_CURRENT_LANGUAGE, language);
        return true;
    }


}
