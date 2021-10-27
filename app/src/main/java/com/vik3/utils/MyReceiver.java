package com.vik3.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.media.session.MediaButtonReceiver;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        MediaButtonReceiver.handleIntent(new MediaSessionCompat(context, "tt"), intent);
    }
}