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
            sendRemoteViewNotification()
        }
        ring_bell_and_vibrator.setOnClickListener {
            sendSuspensionNotification()
        }
    }

    /**
     * 发送通知
     */
    fun sendCommonNotification(){
        openSuspensionWindow()

        val builder = getNotificationBuilder("普通标题", "普通内容普通内容普通内容普通内容普通内容普通内容",channel1.id)

        //notificationManager发送通知
        mNotifiManager.notify(101, builder.build())
    }

    /**
     * 发送折叠式通知
     */
    fun sendRemoteViewNotification(){
        val builder = getNotificationBuilder("折叠标题","折叠内容折叠内容折叠内容折叠内容折叠内容折叠内容",channel2.id)
        //设置优先级
        builder.setPriority(NotificationCompat.PRIORITY_MAX)

        mNotifiManager.notify(102, builder.build())
    }

    /**
     * 发送悬挂式通知
     */
    fun sendSuspensionNotification(){
        val builder = getNotificationBuilder("悬挂标题","悬挂内容悬挂内容悬挂内容悬挂内容悬挂内容悬挂内容",channel3.id)
        //设置优先级
        builder.setVisibility(Notification.VISIBILITY_PUBLIC)
        builder.setPriority(NotificationCompat.PRIORITY_MAX)
        mNotifiManager.notify(103, builder.build())
    }

    /**
     * 设置通知
     * @param title 标题
     */
    fun getNotificationBuilder(title : String, content : String, channel : String) : NotificationCompat.Builder{
        val builder : NotificationCompat.Builder = NotificationCompat.Builder(this, channel)
        //设置内容
        builder.setContentText(content)
        //设置标题
        builder.setContentTitle(title)
        //设置小图标，右边时间下面的图标
        builder.setSmallIcon(R.mipmap.ic_launcher)
        //设置大图标，左边大图标
        builder.setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
        //
        builder.setAutoCancel(true)

        //设置通知的点击相应
        val intent = Intent(this@MainActivity, NotificationActivity::class.java)
        val pendIntent = PendingIntent.getActivity(this, 0,
            intent,0)
        builder.setContentIntent(pendIntent)
        //设置通知的发出时间
        builder.setWhen(System.currentTimeMillis())

        return builder
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
