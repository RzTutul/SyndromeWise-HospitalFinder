package com.example.hospitalfinder.worker;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.hospitalfinder.MainActivity;
import com.example.hospitalfinder.MedicineReminderFragment;
import com.example.hospitalfinder.R;

public class NotificaitonWorker extends Worker {
    Context context;
    private final String CHANNEL_ID = "my_channel_01";
    public NotificaitonWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }
    @NonNull
    @Override
    public Result doWork() {
        String msg = getInputData().getString("msg");
        sendNotification(msg);

        return Result.success();
    }



    private void sendNotification(String msg) {

        Bundle bundle = new Bundle();
        bundle.putString("id",msg);

        PendingIntent intent = new NavDeepLinkBuilder(context).setGraph(R.navigation.nav_graph).setDestination(R.id.notificationFragment).setArguments(bundle).createPendingIntent();
   /*     PendingIntent pendingIntent =
                PendingIntent.getActivity(context,123, intent,

                       PendingIntent.FLAG_UPDATE_CURRENT)*/;

        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Bitmap pic = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.imagelogo);


        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setContentTitle("Hello");
        builder.setContentText(msg);
        builder.setSmallIcon(R.drawable.ic_notifications_active_black_24dp);
        builder.setContentTitle("Take Your Medicine");
        builder.setLargeIcon(BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.imagelogo));
        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(pic));
        builder.setSound(notificationSound);
        builder.setTicker("Heath SYndrome");
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        builder.setVibrate(new long[]{1000,1000,1000,1000});
        builder.setLights(Color.RED,3000,3000);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
       // builder.setContentIntent(pendingIntent);

        builder.setContentIntent(intent);
        builder.addAction(R.drawable.button_shape,"Show Activity",intent);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            Uri notificationSound1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            AudioAttributes attributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH).build();

            NotificationChannel channel =
                    new NotificationChannel(CHANNEL_ID, "some description",
                            NotificationManager.IMPORTANCE_HIGH);
            channel.setSound(notificationSound1,attributes);
            channel.setLightColor(Color.RED);
            builder.setContentTitle("Take Your Medicine");
            builder.setLargeIcon(BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.imagelogo));
            builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(pic));
            channel.setVibrationPattern(new long[]{0,1000,500,1000,100,200,300,400});
            channel.enableVibration(true);
            channel.enableLights(true);
            channel.setLockscreenVisibility(NotificationCompat.VISIBILITY_PUBLIC);
            assert manager != null;
            builder.setChannelId(CHANNEL_ID);
            manager.createNotificationChannel(channel);
        }
        assert manager != null;
        manager.notify(123, builder.build());

    }
}
