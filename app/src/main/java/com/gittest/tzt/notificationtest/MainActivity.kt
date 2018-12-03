package com.gittest.tzt.notificationtest

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.net.Uri
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : BaseActivity() {
    private var state : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openSuspensionWindow()
        initData()
        bindListener()
    }

    private fun initData(){ }

    private fun bindListener(){
        ring_bell.setOnClickListener {
            sendCommonNotification()
        }
        vibrator.setOnClickListener {
            sendCommonNotification()
        }
        ring_bell_and_vibrator.setOnClickListener {
            sendCommonNotification()
        }
    }

    /**
     * 发送通知
     */
    fun sendCommonNotification(){
        openSuspensionWindow()

        val intent = Intent(this@MainActivity, NotificationActivity::class.java)
        val pendIntent = PendingIntent.getActivity(this, 0,
            intent,0)

        val xuanPendIntent = PendingIntent.getActivity(this, 0,
            intent,PendingIntent.FLAG_CANCEL_CURRENT)

        val notifi : NotificationCompat.Builder = NotificationCompat.Builder(this, "alarminfo")
        notifi.setContentText("tbb或3M胶贴已脱落")
        notifi.setContentTitle("tbb报警")
        notifi.setPriority(NotificationCompat.PRIORITY_MAX)
        notifi.setSmallIcon(R.mipmap.ic_launcher)
        notifi.setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
        notifi.setAutoCancel(true)
        notifi.setContentIntent(pendIntent)
        notifi.setFullScreenIntent(xuanPendIntent, true)
        notifi.setWhen(System.currentTimeMillis())

        mNotifiManager.notify(100, notifi.build())
    }

    /**
     * 悬浮窗权限申请
     */
    private fun openSuspensionWindow(){
        if (Build.VERSION.SDK_INT >= 23){
            if (!Settings.canDrawOverlays(this)){
                val openIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + packageName))
                startActivity(openIntent)
            }
        }
    }
}
