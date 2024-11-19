package com.jozistreet.user.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AlarmService extends Service {

    AlarmReceiver alarm = new AlarmReceiver();
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        alarm.setAlarm(this);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
