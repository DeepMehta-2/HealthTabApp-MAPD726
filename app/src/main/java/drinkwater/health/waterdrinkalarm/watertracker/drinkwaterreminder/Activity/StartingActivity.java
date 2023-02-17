package drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Activity;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import com.u.securekeys.SecureEnvironment;

import java.util.ArrayList;
import java.util.List;

import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper.AppPref;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.R;

public class StartingActivity extends AppCompatActivity {
    private Handler handler;
    Runnable runnable;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView((int) R.layout.activity_starting);

        if (VERSION.SDK_INT >= 23) {
            checkAndRequestPermissions();
        }

        GoToMainScreen();
        handler = new Handler();


    }

    private boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE");
        int locationPermission = ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
        List<String> listPermissionsNeeded = new ArrayList();
        if (locationPermission != 0) {
            listPermissionsNeeded.add("android.permission.WRITE_EXTERNAL_STORAGE");
        }
        if (permissionSendMessage != 0) {
            listPermissionsNeeded.add("android.permission.READ_EXTERNAL_STORAGE");
        }
        if (listPermissionsNeeded.isEmpty()) {
            return true;
        }
        ActivityCompat.requestPermissions(this, (String[]) listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        return false;
    }

    public void GoToMainScreen() {
        if (AppPref.isFirstLaunch(this)) {
            startActivity(new Intent(this, HomeActivity.class));
        } else {
            startActivity(new Intent(this, FirstguideActivity.class));
        }
        finish();
    }
}
