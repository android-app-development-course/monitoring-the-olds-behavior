package com.phemie.scnu.laolekang.Fragment2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Calendar;

public class MedicineAlarmService extends Service {
    AlarmManager manager=(AlarmManager)
            getSystemService(ALARM_SERVICE);
    PendingIntent pi;
    public class AlarmBinder extends Binder{
        public void setTime(int y,int m,int d,int h,int mm){

            //设置闹铃的时间
            Calendar calendar = Calendar.getInstance();
            calendar.set(y, m, d, h, mm, 0);
            long timer= calendar.getTimeInMillis();


            //long timer=System.currentTimeMillis()+ 1000*10;
            Intent i=new Intent(MedicineAlarmService.this,AddMedicinePage.class);
            pi=PendingIntent.getActivity(MedicineAlarmService.this, 0, i, 0);
            manager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, timer, pi);
            //manager.set(AlarmManager.RTC_WAKEUP, timer, pi);

        }

    }

    public MedicineAlarmService() {
    }

    public void onCreate(){
        super.onCreate();

    }

    @Override
    public void onDestroy(){
        //manager.cancel(pi);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       return new AlarmBinder();
    }
}
