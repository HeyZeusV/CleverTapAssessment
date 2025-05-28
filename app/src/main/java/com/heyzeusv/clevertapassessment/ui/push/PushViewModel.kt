package com.heyzeusv.clevertapassessment.ui.push

import androidx.lifecycle.ViewModel
import com.clevertap.android.sdk.CleverTapAPI

class PushViewModel(private val cleverTapAPI: CleverTapAPI) : ViewModel() {

	fun timerNotification() {
		cleverTapAPI.pushEvent("Timer Notification")
	}
}