package com.golden.goldencorner;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.golden.goldencorner.data.Utils.AppConstant;
import com.golden.goldencorner.data.local.SharedPreferencesManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.yariksoffice.lingver.Lingver;

public class GoldenApp extends Application {

    public static GoldenApp application;

    public static GoldenApp getInstance() {
        if (application == null)
            application = new GoldenApp();
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesManager.initialize(this);
        Lingver.init(this, SharedPreferencesManager.getCurrentLang());
    }

    private FirebaseAnalytics mFireBaseAnalytics;

    public FirebaseAnalytics getFireBaseAnalyticsInstance() {
        if (mFireBaseAnalytics == null) {
            // Obtain the FirebaseAnalytics instance.
            mFireBaseAnalytics = FirebaseAnalytics.getInstance(this);
            //mFireBaseAnalytics.setUserId(SharedPreferencesManager.getString(PREF_KEY_CURRENT_USER_ID));
        }
        return mFireBaseAnalytics;
    }

    public void logFireBaseAnalytics(String id, String name) {
        Bundle bundle = new Bundle();
        //bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        getFireBaseAnalyticsInstance().logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    //    public void logFireBaseAnalytics(String name) {
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
//        getFireBaseAnalyticsInstance().logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
//    }
    public void logScreenTracks(Activity mActivity) {
        getFireBaseAnalyticsInstance().setCurrentScreen(mActivity,
                mActivity.getClass().getSimpleName(), null);
    }
}






