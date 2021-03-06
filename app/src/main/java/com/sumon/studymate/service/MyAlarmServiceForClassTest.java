package com.sumon.studymate.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import com.sumon.studymate.activity.ClassTestNotificationActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Md Tajmul Alam Sumon on 11/4/2016.
 */

public class MyAlarmServiceForClassTest extends Service {

    private NotificationManager mManager;

    private MediaPlayer player;
    private AssetFileDescriptor descriptor;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            descriptor = getAssets().openFd("that-look.mp3");
            player = new MediaPlayer();

            long start = descriptor.getStartOffset();
            long end = descriptor.getLength();

            player.setDataSource(this.descriptor.getFileDescriptor(), start, end);
            player.prepare();

            player.setVolume(1.0f, 1.0f);
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (player != null) {
                    if (player.isPlaying()) {
                        player.stop();
                    }

                    player.release();
                    player = null;
                }
            }
        }, 10000);


        Intent intent1 = new Intent(this.getApplicationContext(), ClassTestNotificationActivity.class);


        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this.getApplicationContext(), 0, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
        String today = getDateTime();
        String topic = "You Have a Class Test Today";
        addNotification(pendingNotificationIntent, today, topic);
        // If we get killed, after returning from here, restart
        return START_STICKY;

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void addNotification(PendingIntent contentIntent, String title, String msg) {


        NotificationCompat.Builder builder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this.getApplicationContext())
                        .setSmallIcon(android.R.drawable.ic_menu_my_calendar)
                        .setContentTitle(title)
                        .setContentText(msg)
                        .setTicker(msg);


        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) this.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
