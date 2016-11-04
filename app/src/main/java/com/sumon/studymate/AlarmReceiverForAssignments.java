package com.sumon.studymate;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Md Tajmul Alam Sumon on 11/3/2016.
 */
public class AlarmReceiverForAssignments extends BroadcastReceiver {
    private Notification notification;
    private  Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm fired", Toast.LENGTH_SHORT).show();

        Intent service1 = new Intent(context, MyAlarmServiceForAssignments.class);

        context.startService(service1);
        Toast.makeText(context, "Service fired", Toast.LENGTH_SHORT).show();
    }

}
