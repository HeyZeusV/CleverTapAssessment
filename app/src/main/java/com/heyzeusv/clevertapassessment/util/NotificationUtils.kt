package com.heyzeusv.clevertapassessment.util

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.util.Log

object NotificationUtils {

	//Require to close notification on action button click
	fun dismissNotification(intent: Intent?, applicationContext: Context){
		intent?.extras?.apply {
			var autoCancel = true
			var notificationId = -1

			getString("actionId")?.let {
				Log.d("ACTION_ID", it)
				autoCancel = getBoolean("autoCancel", true)
				notificationId = getInt("notificationId", -1)
			}


			if (autoCancel && notificationId > -1) {
				val notifyMgr: NotificationManager =
					applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
				notifyMgr.cancel(notificationId)
			}
		}
	}
}