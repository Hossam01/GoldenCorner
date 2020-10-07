package com.golden.goldencorner.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.golden.goldencorner.ui.base.BaseActivity;
import com.golden.goldencorner.R;
import com.golden.goldencorner.data.Utils.AppConstant;
import com.golden.goldencorner.data.local.SharedPreferencesManager;
import com.golden.goldencorner.ui.ads.AdsActivity;
import com.golden.goldencorner.ui.main.MainActivity;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {

    public static final String TAG = SplashActivity.class.getSimpleName();
    @BindView(R.id.mDilatingDotsProgressBar)
    DilatingDotsProgressBar mDilatingDotsProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        showProgressBar(true);
        hideNavigation();
        startTimerWithRx();

    }

    @Override
    protected void onDestroy() {
        showProgressBar(false);
        super.onDestroy();
    }

    private final long seconds = 1;
    private void startTimerWithRx() {
        Completable.timer(seconds, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    startActivity(new Intent(getApplicationContext(), AdsActivity.class));
//                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    SplashActivity.this.finish();
                });
    }
    private void showProgressBar(boolean isLoading) {
        if (isLoading) {
            mDilatingDotsProgressBar.show();
        } else {
            mDilatingDotsProgressBar.hideNow();
        }
    }
    private void hideNavigation() {
        // Removal of status and navigation bar
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

}
