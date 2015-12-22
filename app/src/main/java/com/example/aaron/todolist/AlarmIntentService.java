package com.example.aaron.todolist;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by aaron on 12/9/15.
 */
public class AlarmIntentService extends IntentService {

    private static final int NOTIFY_ID = 1;

    public AlarmIntentService(){
        super("AlarmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("flow", "HANDLER STARTED FOR INTENT");
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        long when = System.currentTimeMillis();
            Notification notification = new Notification(R.mipmap.ic_launcher, "Task Reminder", when);
            notification.defaults |= Notification.DEFAULT_SOUND;
            notification.flags |= notification.FLAG_AUTO_CANCEL;

            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
            notification.setLatestEventInfo(getApplicationContext(), "Check Me Out", "Yo, open the app and check out your task", contentIntent);
            notificationManager.notify(NOTIFY_ID, notification);

    }
}
