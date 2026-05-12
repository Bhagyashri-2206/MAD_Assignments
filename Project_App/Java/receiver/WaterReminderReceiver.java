package com.example.aquaritual.receiver;

import android.app.*;
import android.content.*;
import android.media.MediaPlayer;

import androidx.core.app.NotificationCompat;

import com.example.aquaritual.R;
import com.example.aquaritual.utils.NotificationHelper;

import java.util.Calendar;

public class WaterReminderReceiver extends BroadcastReceiver {

    public static MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {

        int sleepHour = intent.getIntExtra("sleepHour", 22);
        int intervalMin = intent.getIntExtra("intervalMin", 15);

        Calendar now = Calendar.getInstance();


        if (now.get(Calendar.HOUR_OF_DAY) >= sleepHour) {
            NotificationHelper.stopReminder(context);
            stopSound();
            return;
        }

        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.alarm_sound);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }

        Intent dismissIntent = new Intent(context, DismissReceiver.class);
        PendingIntent dismissPI = PendingIntent.getBroadcast(
                context, 2001, dismissIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NotificationHelper.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_water)
                .setContentTitle("💧 Drink Water")
                .setContentText("Tap dismiss to stop alarm")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(false)
                .addAction(R.drawable.ic_close, "Dismiss", dismissPI);

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1, builder.build());


        scheduleNext(context, intervalMin, sleepHour);
    }

    private void scheduleNext(Context context, int intervalMin, int sleepHour) {

        Calendar next = Calendar.getInstance();
        next.add(Calendar.MINUTE, intervalMin);

        if (next.get(Calendar.HOUR_OF_DAY) >= sleepHour) return;

        Intent i = new Intent(context, WaterReminderReceiver.class);
        i.putExtra("sleepHour", sleepHour);
        i.putExtra("intervalMin", intervalMin);

        PendingIntent pi = PendingIntent.getBroadcast(
                context, 1001, i,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        am.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                next.getTimeInMillis(),
                pi
        );
    }

    public static void stopSound() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}