package com.example.hiker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;

public class RestartEmergencyNotificationService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("restart service", "onReceive: restart service = " + intent.getBooleanExtra("RESTART_SERVICE",true) + " return confirmed =" + intent.getBooleanExtra("RETURN_CONFIRMED", false) + " awaiting confirmation = " + intent.getBooleanExtra("AWAITING_CONFIRMATION", false));


        Intent serviceintent = new Intent(context, EmergencyNotificationIntentService.class);
        serviceintent.putExtra("EXTRA_RETURN_MILLIS", intent.getLongExtra("EXTRA_RETURN_MILLIS", 0));
        serviceintent.putExtra("TRAILID", intent.getLongExtra("TRAILID",0));
        serviceintent.putExtra("RESTART_SERVICE", intent.getBooleanExtra("RESTART_SERVICE",true));
        serviceintent.putExtra("RETURN_CONFIRMED", intent.getBooleanExtra("RETURN_CONFIRMED", false));
        serviceintent.putExtra("AWAITING_CONFIRMATION", intent.getBooleanExtra("AWAITING_CONFIRMATION", false));

        if (Build.VERSION.SDK_INT >= 26 && !intent.getBooleanExtra("AWAITING_CONFIRMATION", false)){

            context.startForegroundService(serviceintent);

        }else {

            context.startService(serviceintent);
        }
    }

}
