package com.example.aquaritual.utils;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

import com.example.aquaritual.R;
import com.example.aquaritual.receiver.WaterReminderReceiver;

import java.util.Calendar;

public class NotificationHelper {

    public static final String CHANNEL_ID = "water_channel";
    private static final int REQ_CODE = 1001;

    public static void createChannel(Context ctx) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            Uri sound = Uri.parse("android.resource://" + ctx.getPackageName() + "/" + R.raw.alarm_sound);

            AudioAttributes attrs = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build();

            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Water Reminder",
                    NotificationManager.IMPORTANCE_HIGH
            );

            channel.setSound(sound, attrs);
            channel.enableVibration(true);

            NotificationManager nm = ctx.getSystemService(NotificationManager.class);
            nm.createNotificationChannel(channel);
        }
    }


    public static void startSmartReminder(Context ctx, int wakeHour, int sleepHour, int intervalMin) {

        Calendar now = Calendar.getInstance();

        Calendar wakeTime = Calendar.getInstance();
        wakeTime.set(Calendar.HOUR_OF_DAY, wakeHour);
        wakeTime.set(Calendar.MINUTE, 0);

        Calendar sleepTime = Calendar.getInstance();
        sleepTime.set(Calendar.HOUR_OF_DAY, sleepHour);
        sleepTime.set(Calendar.MINUTE, 0);


        if (now.before(wakeTime)) {
            now = wakeTime;
        }


        if (now.after(sleepTime)) return;

        Intent i = new Intent(ctx, WaterReminderReceiver.class);
        i.putExtra("sleepHour", sleepHour);

        PendingIntent pi = PendingIntent.getBroadcast(
                ctx, REQ_CODE, i,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);

        am.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                now.getTimeInMillis(),
                intervalMin * 60 * 1000L,
                pi
        );
    }

    public static void stopReminder(Context ctx) {
        Intent i = new Intent(ctx, WaterReminderReceiver.class);

        PendingIntent pi = PendingIntent.getBroadcast(
                ctx, REQ_CODE, i,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pi);
    }
}