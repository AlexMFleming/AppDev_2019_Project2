package com.example.hiker;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.Calendar;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class EmergencyNotificationIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String RETURN_TIME_MILLIS = "EXTRA_RETURN_MILLIS";
    private final String CHANNEL_ID_TIMER = "channel_timer";
    private final int NOTIFICATION_ID = 0;
    long returnTime;
    long trailId;
    boolean awaitingconfirmation= false;
    boolean returnconfirmed = false;
    boolean restartService=true;

    public EmergencyNotificationIntentService() {
        super("EmergencyNotificationIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        returnconfirmed = intent.getBooleanExtra("RETURN_CONFIRMED", false);
        restartService = intent.getBooleanExtra("RESART_SERVICE", true);
        awaitingconfirmation = intent.getBooleanExtra("AWAITING_CONFIRMATION", false);
        returnTime = intent.getLongExtra(RETURN_TIME_MILLIS, 0);
        trailId = intent.getLongExtra("TRAILID",0);
        createTimerNotificationChannel();
        if (!awaitingconfirmation) {
            while (Calendar.getInstance().getTimeInMillis() < returnTime) {
                try {
                    Log.d("EmergencyNotificationIntentService", "onHandleIntent: current time= " + Calendar.getInstance().getTimeInMillis() + " return time = " + returnTime);
                    Log.d("EmergencyNotificationIntentService", "onHandleIntent: current time = " + Calendar.getInstance().getTime() + " month " + Calendar.getInstance().getTimeZone());
                    Thread.sleep(10000);


                } catch (InterruptedException e) {
                    Log.d("EmergencyNotificationIntentService", "onHandleIntent: Thread.sleep interruption: " + e);
                }
            }
            Log.d("EmergencyNotificationIntentService", "onHandleIntent: timer complete");
            createTimerNotification("have you returned from " + TrailDatabase.getInstance(getApplicationContext()).getTrailById(trailId).getTrail_name() + "?");
            awaitingconfirmation = true;
        }else if (!returnconfirmed) {

                while (Calendar.getInstance().getTimeInMillis() < (returnTime + 20000)) { //grace period between return time and emergency notification. could be + 21600000 for 6 hours
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        Log.d("EmergencyNotificationIntentService", "onHandleIntent: Thread.sleep interruption: " + e);
                    }
                }


            if (TrailDatabase.getInstance(getApplicationContext()).getTrip(trailId).getTripCompleted()==0) {
                //sendEmergencySNS()
                createTimerNotification("Emergency SNS Sent!");
                Log.d("EmergencyNotificationIntentService", "onHandleIntent: Emergency SNS sent!");
            }
            restartService=false;
        } else{
            createTimerNotification("return confirmed");
            restartService=false;
        }
        //restartService=false;
    }

    private void createTimerNotification(String text){
        Intent resultIntent = new Intent(this, Trip_Activity.class);
        if (text.length()>1) {
            resultIntent.putExtra("TRAILID", trailId);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addNextIntentWithParentStack(resultIntent);
            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID_TIMER)
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText(text)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .build();

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

            notificationManager.notify(NOTIFICATION_ID, notification);
            startForeground(1, notification);
        }else{
            startForeground(0,null);
        }
    }

    private void createTimerNotificationChannel() {
        if (Build.VERSION.SDK_INT < 26){
            return;
        }
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID_TIMER, name, importance);
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    public void onDestroy(){

        super.onDestroy();

        if (restartService) {
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("restartservice");
            broadcastIntent.setClass(this, RestartEmergencyNotificationService.class);
            broadcastIntent.putExtra("RESTART_SERVICE", restartService);
            broadcastIntent.putExtra("RETURN_CONFIRMED", returnconfirmed);
            broadcastIntent.putExtra("AWAITING_CONFIRMATION", awaitingconfirmation);
            broadcastIntent.putExtra("EXTRA_RETURN_MILLIS", returnTime);
            broadcastIntent.putExtra("TRAILID", trailId);
            this.sendBroadcast(broadcastIntent);
            Log.d("emergencynotificationintenteservice", "onDestroy: service restart");
        }
        Log.d("emergencynotificationintenteservice", "onDestroy: restartService =" + restartService + " awaitingConfirmation= " + awaitingconfirmation + " returnConfirmed = " + returnconfirmed);

    }
    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
