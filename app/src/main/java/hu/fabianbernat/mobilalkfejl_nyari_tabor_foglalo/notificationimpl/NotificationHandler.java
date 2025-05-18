package hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.notificationimpl;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.R;
import hu.fabianbernat.mobilalkfejl_nyari_tabor_foglalo.activities.BrowseCampsActivity;

public class NotificationHandler {
    private static final String CHANNEL_ID = "camp_notification_channel";
    private final int NOTIFICATION_ID = 001;

    private NotificationManager notificationManager;
    private Context context;

    public NotificationHandler(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }

        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Camp Notification Channel",
                NotificationManager.IMPORTANCE_DEFAULT
        );

        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(Color.RED);
        channel.setDescription("FIGYELEM! A táborod jóvá lett hagyva a Nyári Tábor Foglalón!");
        this.notificationManager.createNotificationChannel(channel);
    }

    public void send(String message) {
        Intent intent = new Intent(context, BrowseCampsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Nyári tábor foglaló")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_blackandwhite_camp_icon)
                .setContentIntent(pendingIntent);

        this.notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public void cancel() {
        this.notificationManager.cancel(NOTIFICATION_ID);
    }
}
