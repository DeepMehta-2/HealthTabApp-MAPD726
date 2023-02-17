package drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.ReceiverAndService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    int code = 0;

    public void onReceive(Context context, Intent intent) {
        this.code = intent.getIntExtra("alarmRequestCode", 0);
        intent.getLongExtra("alarmTime", 0);
        if (this.code == 111) {
            AlarmUtill.setDataBy24Hour(context);
            AlarmUtill.cancelAlarm(context);
            AlarmUtill.alarmList(context, false);
            AlarmUtill.remind3hour(context);
        } else if (this.code == 112) {
            Log.i("Sandip", "onReceive:  112");
            AlarmUtill.remind24(context);
        }
    }
}
