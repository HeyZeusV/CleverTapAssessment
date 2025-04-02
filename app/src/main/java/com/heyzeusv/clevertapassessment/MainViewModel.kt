package com.heyzeusv.clevertapassessment

import android.util.Log
import androidx.lifecycle.ViewModel
import com.clevertap.android.sdk.CleverTapAPI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

class MainViewModel(private val cleverTapAPI: CleverTapAPI) : ViewModel() {

	private val _cleverTapId = MutableStateFlow(cleverTapAPI.cleverTapID)
	val cleverTapId: StateFlow<String> get() = _cleverTapId.asStateFlow()

	fun logIntoAccountWithEmail(email: String) {
		val profileUpdate = HashMap<String, Any>()
		profileUpdate["Email"] = email

		cleverTapAPI.onUserLogin(profileUpdate)
		_cleverTapId.value = cleverTapAPI.cleverTapID
	}

	fun updateMyStuff() {
		val profileUpdate = HashMap<String, Any>()

		val stuff = mutableListOf<String>()
		repeat(Random.nextInt(0, 5)) {
			stuff.add(randomString())
		}
		Log.i("CleverTapAssessment", "Updated MyStuff: $stuff")
		profileUpdate["MyStuff"] = stuff
		cleverTapAPI.pushProfile(profileUpdate)
		cleverTapAPI.pushEvent("Update MyStuff")
	}

	private fun randomString(): String {
		val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

		return (1..10)
			.map { Random.nextInt(0, charPool.size).let { charPool[it] } }
			.joinToString("")
	}
}