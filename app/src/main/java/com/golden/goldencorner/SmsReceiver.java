package com.golden.goldencorner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        Log.i("Receiver", "Broadcast received: " + action);

        if(action.equals("my.action.string")){
            String state = intent.getExtras().getString("extra");
            Intent i = new Intent("my.action.string");
            i.putExtra("message",state);

            context.sendBroadcast(i);

        }
    }
}
