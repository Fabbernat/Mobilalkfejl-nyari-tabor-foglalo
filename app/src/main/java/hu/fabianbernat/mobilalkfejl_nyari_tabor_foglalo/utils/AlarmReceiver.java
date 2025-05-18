package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.NotificationHandler;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        new NotificationHandler(context).send("Jelentkeztél már az idei nyári táborokra?");

    }
}