package com.golden.goldencorner.data.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import androidx.annotation.NonNull;
import com.golden.goldencorner.data.Utils.Utils;
import javax.inject.Singleton;

@Singleton
public class NetworkReceiver extends BroadcastReceiver {

    public static final String TAG = NetworkReceiver.class.getSimpleName();

    private NetworkReceiverListener networkReceiverListener;
    public static NetworkReceiver mBroadcastReceiver;
    public static NetworkReceiver getInstance() {
        if (mBroadcastReceiver == null) {
            mBroadcastReceiver = new NetworkReceiver();
        }
        return mBroadcastReceiver;
    }

    public NetworkReceiverListener getNetworkReceiverListener() {
        return networkReceiverListener;
    }
    public void setNetworkReceiverListener(NetworkReceiverListener networkReceiverListener) {
        this.networkReceiverListener = networkReceiverListener;
    }

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        return  super.peekService(myContext, service);
    }
    @Override
    public void onReceive(Context mContext, Intent intent) {
        if (intent == null) return;
        if (intent.getAction() == null) return;
        String action = intent.getAction();
        if (action.equalsIgnoreCase(ConnectivityManager.CONNECTIVITY_ACTION)) {
            if (mContext != null)
                networkReceiverListener = (NetworkReceiverListener) mContext;
            if (networkReceiverListener != null) {
                if (Utils.getInstance().isNetworkConnected(mContext)) {
                    networkReceiverListener.onNetworkReceiverChange(true);
                } else {
                    networkReceiverListener.onNetworkReceiverChange(false);
                }
            }
        }
    }

    private static boolean isRegistered = false;
    public IntentFilter getReceiversIntentFilter() {
        return new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
    }
    public void registerReceiver(@NonNull final Context context, @NonNull final NetworkReceiver receiver) {
        try {
            if (isRegistered){
                unRegisterReceiver(context);
                isRegistered = false;
            }
            if (context != null && receiver != null/*&& !isRegistered*/){
                isRegistered = true;
                context.registerReceiver(receiver, getReceiversIntentFilter());
            }
        } catch (Exception e) {
            Utils.getInstance().LogError(TAG, e.getMessage());
        }
    }

    public void unRegisterReceiver(@NonNull final Context context/*, @NonNull final NetworkReceiver receiver*/) {
        try {
            if (context == null )
                return;
            context.unregisterReceiver(getInstance());
            /*if (getInstance().isOrderedBroadcast()) {
                context.unregisterReceiver(getInstance());
                //networkReceiverListener = null;
                mBroadcastReceiver = null;
            }*/
        } catch (Exception e) {
            Utils.getInstance().LogError(TAG, e.getMessage());
        }
    }

    public interface NetworkReceiverListener {
        // network available if flag is true
        void onNetworkReceiverChange(boolean flag);
    }
}

