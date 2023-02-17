package drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Activity;

import android.content.Intent;
import androidx.fragment.app.FragmentActivity;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityManager {
    private static ActivityManager activityController;
    private AppCompatActivity currentActivity;
    private FragmentActivity fragmentActivity;

    private ActivityManager() {
    }

    public static ActivityManager getInstance() {
        if (activityController == null) {
            activityController = new ActivityManager();
        }
        return activityController;
    }

    public FragmentActivity getCurrentFragmentActivity() {
        return this.fragmentActivity;
    }

    public void setCurrentFragmentActivity(FragmentActivity fragmentActivity) {
        this.fragmentActivity = fragmentActivity;
    }

    public AppCompatActivity getCurrentActivity() {
        return this.currentActivity;
    }

    public void setCurrentActivity(AppCompatActivity appCompatActivity) {
        this.currentActivity = appCompatActivity;
    }

    public void openNewActivity(Class cls, boolean z) {
        if (this.currentActivity != null) {
            Intent intent = new Intent(this.currentActivity, cls);
            if (z) {
                this.currentActivity.startActivity(intent);
                return;
            }
            this.currentActivity.startActivity(intent);
            this.currentActivity.finish();
        }
    }
}
