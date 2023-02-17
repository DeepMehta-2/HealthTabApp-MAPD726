package drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.u.securekeys.annotation.SecureKey;
import com.u.securekeys.annotation.SecureKeys;

import java.util.List;

import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.R;

@SecureKeys({
        @SecureKey(key = "appname", value = "application_name"),
        @SecureKey(key = "applink", value = "application_link"),
        @SecureKey(key = "icon", value = "icon_link"),
        @SecureKey(key = "banner", value = "banner"),
        @SecureKey(key = "package", value = "package_name"),
        @SecureKey(key = "privacy", value = "privacy_link"),
        @SecureKey(key = "account", value = "account_link"),
        @SecureKey(key = "approve", value = "is_approve"),
//        @SecureKey(key = "app_id", value = "137"),
        @SecureKey(key = "SPLASH_JSON", value = "splash_json"),
        @SecureKey(key = "EXIT_JSON", value = "exit_json"),
        @SecureKey(key = "api_sender", value = "dsasdsa"),
        @SecureKey(key = "api_splash", value = "http://pixelpluslab.com/admin/service/app_list_link/notisaver_1"),
        @SecureKey(key = "api_exit", value = "http://pixelpluslab.com/admin/service/app_list_link/electron_media_exit"),
        @SecureKey(key = "api_gcm", value = "http://pixelpluslab.com/admin/service/storeGCM/other"),
        @SecureKey(key = "spadmob_inter", value = "ca-app-pub-1782512705695738/4671105818"),
        @SecureKey(key = "startadmob_inter", value = "ca-app-pub-1782512705695738/4986800581"),
        @SecureKey(key = "setadmob_banner", value = "ca-app-pub-1782512705695738/2968454981"),
        @SecureKey(key = "admob_app_id", value = "ca-app-pub-1782512705695738~9844730814"),
        @SecureKey(key = "backadmob_native", value = "ca-app-pub-1782512705695738/9855983882"),
        @SecureKey(key = "gotitadmob_native", value = "ca-app-pub-1782512705695738/9731860804")
})

public class AppConstants {

    public static Boolean CheckNet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}

