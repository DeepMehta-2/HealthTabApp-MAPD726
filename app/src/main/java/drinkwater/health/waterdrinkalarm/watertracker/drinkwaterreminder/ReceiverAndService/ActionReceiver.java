package drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.ReceiverAndService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ActionReceiver extends BroadcastReceiver {
    public void performAction1() {
    }

    public void performAction2() {
    }

    public void onReceive(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra("action");
        if (stringExtra.equals("action1")) {
            performAction1();
        } else if (stringExtra.equals("action2")) {
            performAction2();
        }
        context.sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
    }
}
