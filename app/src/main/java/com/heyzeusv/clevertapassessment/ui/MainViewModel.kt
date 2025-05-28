package com.heyzeusv.clevertapassessment.ui

import androidx.core.bundle.Bundle
import androidx.lifecycle.ViewModel
import com.clevertap.android.sdk.CTInboxListener
import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.android.sdk.InAppNotificationButtonListener
import com.heyzeusv.clevertapassessment.util.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class MainViewModel(private val cleverTapAPI: CleverTapAPI) : ViewModel() {

	// used to determine which screen to navigate to
	private val _navigateTo = MutableStateFlow<Screen?>(null)
	val navigateTo: StateFlow<Screen?> get() = _navigateTo.asStateFlow()

	// In-App set up
	fun setInAppNotificationButtonListener(listener: InAppNotificationButtonListener) {
		cleverTapAPI.setInAppNotificationButtonListener(listener)
	}

	// App Inbox set up
	fun setCTNotificationInboxListener(listener: CTInboxListener) {
		cleverTapAPI.ctNotificationInboxListener = listener
		cleverTapAPI.initializeInbox()
	}

	/**
	 *	Due to Android 12 update, have to manually handle push notification actions when Activity is
	 *	in Activity stack. Afterwards, pass call to action value (if any) for further processing.
	 */
	fun handleIntent(extras: Bundle?) {
		cleverTapAPI.pushNotificationClickedEvent(extras)

		extras?.let {
			it.getString("wzrk_c2a")?.let { value ->
				handleCallToAction(value)
			}
		}
	}

	/**
	 * 	Update currently shown screen depending on passed [value].
	 */
	fun handleCallToAction(value: String) {
		when (value) {
			"Red Pill" -> _navigateTo.value = Screen.RedPill
			"Blue Pill" -> _navigateTo.value = Screen.BluePill
			else -> _navigateTo.value = null
		}
	}
}