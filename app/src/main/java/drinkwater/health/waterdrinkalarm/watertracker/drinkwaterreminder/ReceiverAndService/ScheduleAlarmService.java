package drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.ReceiverAndService;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.Nullable;

public class ScheduleAlarmService extends IntentService {
    /* access modifiers changed from: protected */
    public void onHandleIntent(@Nullable Intent intent) {
    }

    public ScheduleAlarmService() {
        this("ScheduleReminderService");
    }

    public ScheduleAlarmService(String str) {
        super(str);
    }
}
