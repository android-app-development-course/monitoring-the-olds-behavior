package com.phemie.scnu.laolekang.Fragment2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DateUpdateReceiver extends BroadcastReceiver {

    private static final String ACTION_DATE_CHANGED = Intent.ACTION_DATE_CHANGED;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String action = intent.getAction();

        if (ACTION_DATE_CHANGED.equals(action)) {



        }
    }
}
