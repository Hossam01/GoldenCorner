package com.golden.goldencorner.ui.base;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentActivity;

import com.golden.goldencorner.data.Utils.AppConstant;
import com.golden.goldencorner.data.Utils.Utils;
import com.golden.goldencorner.data.local.SharedPreferencesManager;
import com.golden.goldencorner.data.receiver.NetworkReceiver;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;

public class BaseActivity extends AppCompatActivity implements NetworkReceiver.NetworkReceiverListener {

    private static final String TAG = BaseActivity.class.getSimpleName();

    private CoordinatorLayout mCoordinatorLayout;

    private NetworkReceiver mReceiver;
    @Inject
    public BaseActivity() {}
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (this instanceof MainActivity) {
//            permissionsObserver(this);
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//            if (mReceiver == null) {
//                mReceiver = NetworkReceiver.getInstance();
//                mReceiver.registerReceiver(this, mReceiver);
//                mReceiver.setNetworkReceiverListener(this);
//            }
      //  hideNavigation();
        if (NetworkReceiver.getInstance() != null && !NetworkReceiver.getInstance().isOrderedBroadcast()) {
            NetworkReceiver.getInstance().registerReceiver(this, NetworkReceiver.getInstance());
        }
        NetworkReceiver.getInstance().setNetworkReceiverListener(this);

    }

    private void hideNavigation() {
        // Removal of status and navigation bar
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    public CoordinatorLayout getCoordinatorLayout() {
        return mCoordinatorLayout;
    }

    public void setCoordinatorLayout(CoordinatorLayout mCoordinatorLayout) {
        this.mCoordinatorLayout = mCoordinatorLayout;
    }

    public void permissionsObserver(final FragmentActivity mActivity) {
        new RxPermissions(mActivity)
                .request(
                        Manifest.permission.INTERNET,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
//                        Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                        Manifest.permission.ACCESS_WIFI_STATE)
                .subscribe(new DisposableObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean granted) {
                        if (granted) {
                            // All requested permissions are granted
                            Log.e(TAG, "PERMISSIONS ARE GRANTED");
                        } else {
                            // At least one permission is denied
                            Log.e(TAG, "PERMISSIONS ARE DENIED");
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }
                    @Override
                    public void onComplete() {}
                });
    }

    @Override
    public void onNetworkReceiverChange(boolean flag) {
//        if (!flag){
//            if (getCoordinatorLayout() != null)
//                showSnackBar(getCoordinatorLayout(), getString(R.string.no_internet_connection));
//            else
//                Utils.getInstance().showToast(this, getString(R.string.no_internet_connection));
//        } else {
//            hideSnackBar();
//        }
    }
    private  Snackbar snackbar = null;
    public void showSnackBar(@NonNull View view,
                             @NonNull String msg){
        if (view instanceof CoordinatorLayout || view instanceof AppBarLayout) {
            snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE);
            snackbar.setBackgroundTint(Color.RED);
            snackbar.show();
        } else {
            Utils.getInstance().showToast(this, msg);
        }
    }
    public void showSnackBar(@NonNull View view,
                             @NonNull String msg,
                             @NonNull String actionTitle,
                             @NonNull View.OnClickListener action){
        if (view != null && view instanceof CoordinatorLayout || view instanceof AppBarLayout) {
            snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_INDEFINITE);
            snackbar.setBackgroundTint(Color.RED);
            snackbar.setAction(actionTitle, action);
            snackbar.show();
        } else {
            Utils.getInstance().showToast(this, msg);
        }
    }
    public void hideSnackBar() {
        if (snackbar != null && snackbar.isShown())
            snackbar.dismiss();
    }
    public void showToast(@NonNull String msg){
        Toast.makeText( this, msg, Toast.LENGTH_SHORT).show();
    }


    public final void restartApp() {
        startActivity(getPackageManager()
                .getLaunchIntentForPackage(getPackageName())
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

}