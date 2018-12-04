package com.gittest.tzt.notificationtest

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.*
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.widget.RemoteViews
import kotlinx.android.synthetic.main.activity_main.*

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : BaseActivity() {

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
            Thread.sleep(3000)
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
        val builder = NotificationCompat.Builder(this, channel2.id)
        val remoteView = RemoteViews(packageName, R.layout.remote_notifi)
        //设置通知的点击响应，这里是跳转到NotificationActivity
        val intent = Intent(this@MainActivity, NotificationActivity::class.java)
        val pendIntent = PendingIntent.getActivity(this, 0,
            intent,0)
        //设置内容
        builder.setPriority(NotificationManager.IMPORTANCE_HIGH)
        builder.setContent(remoteView)
        builder.setCustomBigContentView(remoteView)
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setNumber(33)
        remoteView.setImageViewResource(R.id.iv_tupian, R.mipmap.tupian1111)
        remoteView.setTextViewText(R.id.tv_title,"自定义通知标题")
        remoteView.setTextViewText(R.id.tv_content,"自定义通知内容自定义通知内容自定义通知内容自定义通知内容自定义通知内容")
        remoteView.setTextViewText(R.id.tv_sure,"确认")
        remoteView.setTextViewText(R.id.tv_cancel,"取消")
        //确认的点击事件
        remoteView.setOnClickPendingIntent(R.id.tv_sure, pendIntent)
        builder.setAutoCancel(true)

        mNotifiManager.notify(102, builder.build())
    }

    /**
     * 发送自定义声音，通知
     *
     *
     */
    @SuppressLint("WrongConstant")
    fun sendSuspensionNotification(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel3 = NotificationChannel("suspension ","我的悬挂式通知消息",NotificationManager.IMPORTANCE_MAX)
            //设置振动
            channel3.enableVibration(true)
            channel3.vibrationPattern = longArrayOf(300, 400, 300, 400, 300, 400)
            //设置提示音,url也可以设置为 "android.resource://"+ packageName + R.raw.girl_water
            channel3.setSound(Uri.parse("android.resource://"+ packageName +"/raw/girl_water"), Notification.AUDIO_ATTRIBUTES_DEFAULT)
            //设置闪光灯
            channel3.enableLights(true)
            channel3.lightColor = Color.BLUE
            //设置任何情况下都会显示
            channel3.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
            channel3.description = "悬挂通知消息"
            mNotifiManager.createNotificationChannel(channel3)
            val builder = getNotificationBuilder("悬挂标题","悬挂内容悬挂内容悬挂内容悬挂内容悬挂内容悬挂内容",channel3.id)
            mNotifiManager.notify(103, builder.build())
        }
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

        //设置通知的点击响应，这里是跳转到NotificationActivity
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
