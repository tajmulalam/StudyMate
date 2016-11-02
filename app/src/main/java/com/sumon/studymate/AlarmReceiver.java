package com.sumon.studymate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by Md Tajmul Alam Sumon on 11/3/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm fired", Toast.LENGTH_SHORT).show();
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone ringtone = RingtoneManager.getRingtone(context, uri);
        ringtone.play();
        if (!ringtone.isPlaying())
            context.startActivity(new Intent(context.getApplicationContext(), LauncherActivity.class));
    }
}
