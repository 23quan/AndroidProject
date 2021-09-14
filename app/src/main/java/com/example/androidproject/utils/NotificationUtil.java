package com.example.androidproject.utils;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.example.androidproject.BaseApplication;
import com.example.androidproject.R;


/**
 * 消息通知栏
 */
@SuppressLint("NotificationTrampoline")
public class NotificationUtil {
    //渠道名称
    private final static CharSequence CHANNEL_NAME = "zhuoniu";
    //渠道唯一id
    private final static String CHANNEL_ID = "102102102";
    //播放器通知栏id
    private final static int NOTIFY_ID = 108108108;

    //点击进入app标识常量
    public final static String CLICK_APP = "click_app";
    //点击取消通知栏标识常量
    public final static String CLICK_CANCEL = "click_cancel";
    //点击上一首标识常量
    public final static String CLICK_LAST = "click_last";
    //点击暂停/播放标识常量
    public final static String CLICK_PLAY = "click_play";
    //点击下一首标识常量
    public final static String CLICK_NEXT = "click_next";

    //是否销毁通知栏
    public static boolean isCreateNotification = false;
    //通知管理器
    private static NotificationManager notificationManager;

    /**
     * 创建通知栏
     */
    @SuppressLint("WrongConstant")
    public static void createNotification(String title, boolean isPlay) {
        notificationManager = (NotificationManager) BaseApplication.getContext().getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//适配一下高版本
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            //关了通知默认提示音
            notificationChannel.setSound(null, null);
            //设置锁屏时候的可见性
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            //设置通知渠道的重要性级别
            notificationChannel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(BaseApplication.getContext(), CHANNEL_ID);
        //设置小图标
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //设置是否正在通知
        builder.setOngoing(true);
        //点击不让消失
        builder.setAutoCancel(false);
        //设置只提醒一次
        builder.setOnlyAlertOnce(true);
        //任何情况下都显示
        builder.setVisibility(Notification.VISIBILITY_PUBLIC);
        //设置优先级
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        //自定义默认view
        builder.setCustomContentView(getRemoteViews(title, isPlay));
        //自定义扩展view
        builder.setCustomBigContentView(getRemoteViews(title, isPlay));
        //整个点击跳转activity
        builder.setContentIntent(getPendingIntent(CLICK_APP));

        Notification notification = builder.build();
        notificationManager.notify(NOTIFY_ID, notification);
        isCreateNotification = true;
    }

    /**
     * 取消通知栏
     */
    public static void cancelNotification() {
        if (isCreateNotification) {
            if (notificationManager != null) {
                notificationManager.cancel(NOTIFY_ID);
                isCreateNotification = false;
            }
        }
    }

    /**
     * 自定义view
     *
     * @param title
     * @param isPlay
     * @return
     */
    private static RemoteViews getRemoteViews(String title, boolean isPlay) {
        //自定义界面
        RemoteViews remoteViews = new RemoteViews(BaseApplication.getContext().getPackageName(), R.layout.notification_player);
        //点击取消通知栏
        remoteViews.setOnClickPendingIntent(R.id.player_cancel, getPendingIntent(CLICK_CANCEL));
        //点击上一首
        remoteViews.setOnClickPendingIntent(R.id.player_last, getPendingIntent(CLICK_LAST));
        //点击播放暂停
        remoteViews.setOnClickPendingIntent(R.id.player_play, getPendingIntent(CLICK_PLAY));
        //点击下一首
        remoteViews.setOnClickPendingIntent(R.id.player_next, getPendingIntent(CLICK_NEXT));

        //消息图片
        remoteViews.setImageViewResource(R.id.player_icon, R.mipmap.ic_launcher_round);
        //标题
        remoteViews.setTextViewText(R.id.player_title, title);
        //暂停、播放图标
        if (isPlay) {
            remoteViews.setImageViewResource(R.id.player_play, R.mipmap.icon_stop);
        } else {
            remoteViews.setImageViewResource(R.id.player_play, R.mipmap.icon_stop);
        }
        return remoteViews;
    }

    /**
     * 封装PendingIntent
     *
     * @param action
     * @return
     */
    private static PendingIntent getPendingIntent(String action) {
        Intent intent = new Intent(BaseApplication.getContext(), NotificationClickReceiver.class);
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(BaseApplication.getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }
}
