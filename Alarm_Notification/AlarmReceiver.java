package com.example.alarmapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    public static MediaPlayer mediaPlayer;
    private static final String CHANNEL_ID = "alarm_channel";
    public static final String ACTION_DISMISS_ALARM = "DISMISS_ALARM";

    @Override
    public void onReceive(Context context, Intent intent) {


        if (intent != null && ACTION_DISMISS_ALARM.equals(intent.getAction())) {

            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.release();
                mediaPlayer = null;
            }

            NotificationManager manager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (manager != null) {
                manager.cancel(1);
            }

            return;
        }

        try {

            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }

            Uri alarmUri = Uri.parse(
                    "android.resource://" +
                            context.getPackageName() +
                            "/" +
                            R.raw.alarm_sound
            );

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(context, alarmUri);

            mediaPlayer.setAudioAttributes(
                    new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_ALARM)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
            );

            mediaPlayer.setLooping(true);
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (Exception e) {
            e.printStackTrace();
        }


        Intent openIntent = new Intent(context, MainActivity.class);

        PendingIntent openPendingIntent = PendingIntent.getActivity(
                context,
                0,
                openIntent,
                PendingIntent.FLAG_UPDATE_CURRENT |
                        PendingIntent.FLAG_IMMUTABLE
        );

        Intent dismissIntent = new Intent(context, AlarmReceiver.class);
        dismissIntent.setAction(ACTION_DISMISS_ALARM);

        PendingIntent dismissPendingIntent = PendingIntent.getBroadcast(
                context,
                1,
                dismissIntent,
                PendingIntent.FLAG_UPDATE_CURRENT |
                        PendingIntent.FLAG_IMMUTABLE
        );


        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Alarm Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );

            channel.setDescription("Alarm notification channel");
            channel.enableVibration(true);

            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                        .setContentTitle("Alarm Ringing!")
                        .setContentText("Wake up! Your alarm is active.")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_ALARM)
                        .setOngoing(true)
                        .setAutoCancel(false)
                        .setContentIntent(openPendingIntent)
                        .addAction(
                                android.R.drawable.ic_menu_close_clear_cancel,
                                "Dismiss",
                                dismissPendingIntent
                        );

        notificationManager.notify(1, builder.build());
    }
}
