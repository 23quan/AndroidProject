package com.example.androidproject.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.androidproject.BaseApplication;
import com.example.androidproject.activity.MainActivity;

/**
 * 通知栏跳转广播监听
 */
public class NotificationClickReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case NotificationUtil.CLICK_APP: //通知栏进入app
                /*Intent intent1 = new Intent(BaseApplication.getContext(), MainActivity.class);
                BaseApplication.getContext().startActivity(intent1);*/
                break;
            case NotificationUtil.CLICK_CANCEL://通知栏取消
                NotificationUtil.cancelNotification();
                break;
            case NotificationUtil.CLICK_LAST://通知栏上一首
                if (onNotificationClickListener != null) {
                    onNotificationClickListener.onNotificationLast();
                }
                break;
            case NotificationUtil.CLICK_PLAY://通知栏播放和暂停
                if (onNotificationClickListener != null) {
                    onNotificationClickListener.onNotificationPlay();
                }
                break;
            case NotificationUtil.CLICK_NEXT://通知栏下一首
                if (onNotificationClickListener != null) {

                    onNotificationClickListener.onNotificationNext();
                }
                break;
        }
    }

    private static OnNotificationClickListener onNotificationClickListener;

    public static void setOnNotificationClickListener(OnNotificationClickListener onNotificationClickListener) {
        NotificationClickReceiver.onNotificationClickListener = onNotificationClickListener;
    }

    public interface OnNotificationClickListener {
        //上一首
        void onNotificationLast();

        //播放暂停
        void onNotificationPlay();

        //下一首
        void onNotificationNext();
    }
}
