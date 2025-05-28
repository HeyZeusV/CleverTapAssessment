package com.heyzeusv.clevertapassessment.ui.eventform

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clevertap.android.sdk.CleverTapAPI
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date


class EventFormViewModel(private val cleverTapAPI: CleverTapAPI) : ViewModel() {

	// Used to display switching between test users
	private val _cleverTapId = MutableStateFlow(cleverTapAPI.cleverTapID)
	val cleverTapId: StateFlow<String> get() = _cleverTapId.asStateFlow()

	private val _eventName = MutableStateFlow("")
	val eventName: StateFlow<String> get() = _eventName
	fun updateEventName(newValue: String) { _eventName.update { newValue } }

	private val _eventProperties = MutableStateFlow(mutableListOf(Pair("", "")))
	val eventProperties: StateFlow<List<Pair<String, String>>> get() = _eventProperties
	fun updateEventProperties(index: Int, newKey: String, newValue: String) {
		_eventProperties.update { it.apply { this[index] = Pair(newKey, newValue) } }
	}
	fun addEventProperty() { _eventProperties.update { it.plus(Pair("", "")).toMutableList() } }

	/**
	 * 	Creates new account with hard coded values.
	 */
	fun createAccountWithIdentity(identity: String) {
		if (!cleverTapAPI.isPushPermissionGranted) {
			cleverTapAPI.promptForPushPermission(true)
		} else {
			viewModelScope.launch {
				val profileUpdate = mapOf(
					"Name" to identity,
					"Identity" to identity,
					"Email" to "$identity@gmail.com",
					"DOB" to Date(),
					"MyStuff" to listOf("Random", "Stuff"),
				)

				// Used to check if user has changed by difference in CleverTap ids.
				val currentId = _cleverTapId.value
				cleverTapAPI.onUserLogin(profileUpdate)
				// Logging in is not instant, so add delay of 1 second between checks.
				while (currentId == cleverTapAPI.cleverTapID) {
					_cleverTapId.value = "Loading"
					delay(1000)
				}
				_cleverTapId.value = cleverTapAPI.cleverTapID
			}
		}
	}

	/**
	 * 	Log into user with given [identity] and update [_cleverTapId].
	 */
	fun logIntoAccountWithIdentity(identity: String) {
		viewModelScope.launch {
			val profileUpdate = mapOf("Identity" to identity)
			// Used to check if user has changed by difference in CleverTap ids.
			val currentId = _cleverTapId.value

			cleverTapAPI.onUserLogin(profileUpdate)
			// Logging in is not instant, so add delay of 1 second between checks.
			while (currentId == cleverTapAPI.cleverTapID) {
				_cleverTapId.value = "Loading"
				delay(1000)
			}
			_cleverTapId.value = cleverTapAPI.cleverTapID
		}
	}

	fun pushEvent() {
		val customEventProperties = mutableMapOf<String, Any>()
		_eventProperties.value.forEach {
			if (!it.first.isNotBlank() && !it.second.isNotBlank())
			customEventProperties.put(it.first, it.second)
		}
		cleverTapAPI.pushEvent(eventName.value, customEventProperties)
	}

	fun resetForm() {
		_eventName.update { "" }
		viewModelScope.launch {
			_eventProperties.update { mutableListOf() }
			delay(100)
			_eventProperties.update { mutableListOf(Pair("", "")) }
		}
	}
}