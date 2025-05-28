package com.heyzeusv.clevertapassessment

import android.app.Application
import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler
import com.clevertap.android.sdk.ActivityLifecycleCallback
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.android.sdk.interfaces.NotificationHandler
import com.heyzeusv.clevertapassessment.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CleverTapApplication : Application() {
	override fun onCreate() {
		ActivityLifecycleCallback.register(this)
		CleverTapAPI.setNotificationHandler(PushTemplateNotificationHandler() as NotificationHandler)
		super.onCreate()

		startKoin {
			androidLogger()
			androidContext(this@CleverTapApplication)
			modules(appModule)
		}
	}
}