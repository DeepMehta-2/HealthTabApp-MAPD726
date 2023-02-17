package drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.ReceiverAndService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Log.e(AlarmUtill.LOGATAG, "SystemBootReceiver");
        if (intent != null) {
            String action = intent.getAction();
            if (!(action == null || action.isEmpty())) {
                String str = AlarmUtill.LOGATAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onReceive: AlarmReceiverRebootAll");
                stringBuilder.append(action);
                Log.d(str, stringBuilder.toString());
                int z = 1;
                switch (action.hashCode()) {
                    case -810471698:
                        if (action.equals("android.intent.action.PACKAGE_REPLACED")) {
                            z = 1;
                            break;
                        }
                        break;
                    case -757780528:
                        if (action.equals("android.intent.action.PACKAGE_RESTARTED")) {
                            z = 1;
                            break;
                        }
                        break;
                    case 505380757:
                        if (action.equals("android.intent.action.TIME_SET")) {
                            z = 1;
                            break;
                        }
                        break;
                    case 525384130:
                        if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
                            z = 1;
                            break;
                        }
                        break;
                    case 798292259:
                        if (action.equals("android.intent.action.BOOT_COMPLETED")) {
                            z = 0;
                            break;
                        }
                        break;
                    case 1544582882:
                        if (action.equals("android.intent.action.PACKAGE_ADDED")) {
                            z = 1;
                            break;
                        }
                        break;
                    case 1737074039:
                        if (action.equals("android.intent.action.MY_PACKAGE_REPLACED")) {
                            z = 1;
                            break;
                        }
                        break;
                }
                switch (z) {
                    case 0:
                        AlarmUtill.alarmList(context, false);
                        break;
                    case 1:
                        AlarmUtill.cancelAlarm(context);
                        AlarmUtill.alarmList(context, false);
                        break;
                    case 2:
                        AlarmUtill.alarmList(context, false);
                        break;
                    case 3:
                        AlarmUtill.alarmList(context, false);
                        break;
                }
            }
        }
        AlarmUtill.remind3hour(context);
        AlarmUtill.remind24(context);
    }
}
