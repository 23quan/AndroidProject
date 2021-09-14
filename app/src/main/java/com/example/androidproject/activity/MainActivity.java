package com.example.androidproject.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidproject.R;
import com.example.androidproject.utils.NotificationClickReceiver;
import com.example.androidproject.utils.NotificationUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NotificationClickReceiver.OnNotificationClickListener {
    //通知栏消息标题
    private String title = "新闻联播";
    //是否播放
    private boolean isPlay;

    //播放通知栏消息
    private TextView mainNotificationPlay;
    //新闻通知栏消息
    private TextView mainNotificationNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        mainNotificationPlay = (TextView) findViewById(R.id.main_notification_play);
        mainNotificationNews = (TextView) findViewById(R.id.main_notification_news);

        NotificationClickReceiver.setOnNotificationClickListener(this);

        mainNotificationPlay.setOnClickListener(this);
        mainNotificationNews.setOnClickListener(this);
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_notification_play:
                NotificationUtil.createNotification(title, false);
                break;
            case R.id.main_notification_news:
                break;
        }
    }

    /**
     * 上一首
     */
    @Override
    public void onNotificationLast() {
        Toast.makeText(this, "上一首", Toast.LENGTH_SHORT).show();
    }

    /**
     * 暂停or播放
     */
    @Override
    public void onNotificationPlay() {
        if (isPlay) {
            isPlay = false;
            NotificationUtil.createNotification(title, false);
            Toast.makeText(this, "暂停", Toast.LENGTH_SHORT).show();
        } else {
            isPlay = true;
            NotificationUtil.createNotification(title, true);
            Toast.makeText(this, "播放", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 下一首
     */
    @Override
    public void onNotificationNext() {
        Toast.makeText(this, "上一首", Toast.LENGTH_SHORT).show();
    }
}