package drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Helper;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import androidx.core.app.NotificationCompat;
import android.widget.RemoteViews;

import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Activity.HomeActivity;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Activity.DatabaseHelper;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.Activity.Homefragment;
import drinkwater.health.waterdrinkalarm.watertracker.drinkwaterreminder.R;

public class NotificationHelper {
    private static final int DONE_ID = 101;
    public static final String NOTIFICATION_CHANNEL_ID = "channe1";
    private static final int NOTIFICATION_ID = 100;
    private static final int SKIPPED_ID = 102;
    String TAG = toString();
    Context context;
    DatabaseHelper db;
    Notification notification;

    public NotificationHelper(Context context) {
        this.context = context;
        this.db = new DatabaseHelper(context);
    }

    public void getnotification(Context context) {
        try {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
            PendingIntent activity = PendingIntent.getActivity(context, 0, new Intent(context, HomeActivity.class), 134217728);
            if (VERSION.SDK_INT >= 26) {
                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Reminder", 2);
                notificationChannel.setLightColor(-16776961);
                notificationChannel.setLockscreenVisibility(1);
                notificationManager.createNotificationChannel(notificationChannel);
                this.notification = new Builder(context, NOTIFICATION_CHANNEL_ID).setVisibility(1).setSmallIcon(R.mipmap.ic_launcher).setContent(createNotificationPlayerView(context, "title", "")).setContentIntent(activity).setAutoCancel(false).setCategory(NotificationCompat.CATEGORY_SOCIAL).setOngoing(true).build();
                notificationManager.notify(100, this.notification);
                return;
            }
            notificationManager.notify(100, new NotificationCompat.Builder(context).setSmallIcon(R.mipmap.ic_launcher).setContent(createNotificationPlayerView(context, "title", "")).setAutoCancel(false).setOngoing(true).setContentIntent(activity).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private RemoteViews createNotificationPlayerView(Context context, String str, String str2) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notificationdialog);
        int ml;
        StringBuilder stringBuilder;
        CharSequence valueOf;
        CharSequence valueOf2;
        if (AppPref.isKglb(context)) {
            ml = this.db.getMl(context) - this.db.getCurrentDrinkedWater();
            if (ml < 0) {
                ml = 0;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(ml);
            stringBuilder.append(" ml");
            valueOf = String.valueOf(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(this.db.getMl(context));
            stringBuilder.append(" ml");
            valueOf2 = String.valueOf(stringBuilder.toString());
            remoteViews.setTextViewText(R.id.title, valueOf);
            remoteViews.setTextViewText(R.id.titledaily, valueOf2);
        } else {
            ml = Homefragment.returnfloz(this.db.getMl(context)) - Homefragment.returnfloz(this.db.getCurrentDrinkedWater());
            if (ml < 0) {
                ml = 0;
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(ml);
            stringBuilder.append(" fl oz");
            valueOf = String.valueOf(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(Homefragment.returnfloz(this.db.getMl(context)));
            stringBuilder.append(" fl oz");
            valueOf2 = String.valueOf(stringBuilder.toString());
            remoteViews.setTextViewText(R.id.title, valueOf);
            remoteViews.setTextViewText(R.id.titledaily, valueOf2);
        }
        return remoteViews;
    }

    public void cancelNotification() {
        ((NotificationManager) this.context.getSystemService("notification")).cancel(100);
    }
}
