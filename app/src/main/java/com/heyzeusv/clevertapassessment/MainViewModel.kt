package com.heyzeusv.clevertapassessment

import android.icu.text.DecimalFormat
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clevertap.android.sdk.CleverTapAPI
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel(private val cleverTapAPI: CleverTapAPI) : ViewModel() {

	private val _cleverTapId = MutableStateFlow(cleverTapAPI.cleverTapID)
	val cleverTapId: StateFlow<String> get() = _cleverTapId.asStateFlow()

	fun logIntoAccountWithIdentity(identity: String) {
		viewModelScope.launch {
			val profileUpdate = mapOf("Identity" to identity)
			val currentId = _cleverTapId.value

			cleverTapAPI.onUserLogin(profileUpdate)
			while (currentId == cleverTapAPI.cleverTapID) {
				_cleverTapId.value = "Loading"
				delay(1000)
			}
			_cleverTapId.value = cleverTapAPI.cleverTapID
		}
	}

	fun productViewedEvent(productId: Int, productName: String, emailId: String) {
		val df = DecimalFormat("#.##")
		val productViewedAction = mapOf(
			"Product Id" to productId,
			"Product Name" to productName,
			"Product Price" to df.format(Random.nextDouble(1.00, 100.00)),
		)

		cleverTapAPI.pushEvent("Product Viewed", productViewedAction)

		val profileUpdate = mapOf("Email" to "clevertap+$emailId@gmail.com")
		cleverTapAPI.pushProfile(profileUpdate)
	}

	fun updateMyStuff() {
		val stuff = mutableListOf<String>()
		repeat(Random.nextInt(0, 5)) {
			stuff.add(randomString())
		}
		Log.i("CleverTapAssessment", "Updated MyStuff: $stuff")

		val profileUpdate = mapOf("MyStuff" to stuff)
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