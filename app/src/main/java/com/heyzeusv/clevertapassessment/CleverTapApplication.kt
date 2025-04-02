package com.heyzeusv.clevertapassessment

import android.app.Application
import com.clevertap.android.sdk.ActivityLifecycleCallback
import com.heyzeusv.clevertapassessment.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CleverTapApplication : Application() {
	override fun onCreate() {
		ActivityLifecycleCallback.register(this)

		super.onCreate()

		startKoin {
			androidLogger()
			androidContext(this@CleverTapApplication)
			modules(appModule)
		}
	}
}