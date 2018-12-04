package com.gittest.tzt.notificationtest

import android.os.Bundle

class NotificationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        mNotifiManager.cancel(102)
    }
}
