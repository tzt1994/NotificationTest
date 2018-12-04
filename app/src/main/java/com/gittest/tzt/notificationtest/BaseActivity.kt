package com.gittest.tzt.notificationtest

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {
    lateinit var mNotifiManager : NotificationManager
    lateinit var mContext: Context
    lateinit var channel1: NotificationChannel
    lateinit var channel2: NotificationChannel

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        mNotifiManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            channel1 = NotificationChannel("common","我的普通通知消息",NotificationManager.IMPORTANCE_LOW)
            channel1.description = "普通通知消息"
            mNotifiManager.createNotificationChannel(channel1)

            channel2 = NotificationChannel("flod","我的折叠式通知消息",NotificationManager.IMPORTANCE_DEFAULT)
            channel2.description = "折叠通知消息"
            channel2.setShowBadge(true)
            mNotifiManager.createNotificationChannel(channel2)

        }
    }
}