# NotificationTest
Notification 通知栏详解(Kotlin)

Notificaiton 是显示在状态栏上的信息，它默认情况下不占用任何空间，只有当用户需要的时候用手指在状态栏上向下滑动，通知栏的内容才会显示出来，
这在智能手机发展的初期极大地解决了手机屏幕过小，内容展示区域不足的问题。

notification 先来看下基本构成

![图片不存在](https://github.com/tzt1994/imageLib/blob/master/NotificationTest/38056771.jpg)

上面的组成元素依次是：

Icon/Photo：大图标

Title/Name：标题

Message：内容信息

Timestamp：通知时间，默认是系统发出通知的时间，也可以通过setWhen()来设置

Secondary Icon：小图标

这是基本的notificaiton，相信大家也见到过可以下拉的通知，这就要使用到RemoteView，后面会有使用方法。

来看下notificaiton的基本使用。

通知的使用涉及到两个类，Notificaiton 和 NotificationManager。
其中NotificationManager 使用来管理通知的类，包括发送通知，清楚通知；Notification是通知类，通过Notificaition.Builder类的build方法来获取。

使用步骤：
1.获取NotificationManager对象， lateinit varmNotifiManager = getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager;
2.创建一个通知栏的Bulider构造对象，val notifi : NotificationCompat.Builder = NotificationCompat.Builder(this, "alarminfo")
3.对builder进行设置，例如图片，文字，动作，铃声，振动效果等
4.调用builder的build()为Notification赋值
5.使用NotificationManager的notify()发送通知
ps:还可以使用NotificationManager的cancael()来取消通知
