package com.heyzeusv.clevertapassessment

import android.util.Log
import androidx.core.bundle.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clevertap.android.sdk.CleverTapAPI
import com.heyzeusv.clevertapassessment.util.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.random.Random

class MainViewModel(private val cleverTapAPI: CleverTapAPI) : ViewModel() {

	private val _cleverTapId = MutableStateFlow(cleverTapAPI.cleverTapID)
	val cleverTapId: StateFlow<String> get() = _cleverTapId.asStateFlow()

	private val _pillSelection = MutableStateFlow<Screen>(Screen.Home)
	val pillSelection: StateFlow<Screen> get() = _pillSelection.asStateFlow()

	fun handleIntent(extras: Bundle?) {
		cleverTapAPI.pushNotificationClickedEvent(extras)

		extras?.let {
			it.getString("wzrk_c2a")?.let { value ->
				when (value) {
					"Red Pill" -> _pillSelection.value = Screen.RedPill
					"Blue Pill" -> _pillSelection.value = Screen.BluePill
					else -> { }
				}
			}
		}
	}

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

	fun productViewedEvent(productId: String, productName: String, emailId: String) {
		val checkedId = if (productId.isBlank()) 1 else productId.toInt()
		val productPrice = (Random.nextDouble(1.00, 100.00) * 100.0).roundToInt() / 100.0
		val productViewedAction = mapOf(
			"Product Id" to checkedId,
			"Product Name" to productName,
			"Product Price" to productPrice,
		)

		cleverTapAPI.pushEvent("Product Viewed", productViewedAction)

		val profileUpdate = mapOf("Email" to "clevertap+$emailId@gmail.com")
		cleverTapAPI.pushProfile(profileUpdate)
		Log.i("CleverTapAssessment", "Product Viewed: Id = $productId, Name = $productName, Price = $productPrice")
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

	fun selectPillEvent() {
		cleverTapAPI.pushEvent("Select Pill")
	}

	private fun randomString(): String {
		val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

		return (1..10)
			.map { Random.nextInt(0, charPool.size).let { charPool[it] } }
			.joinToString("")
	}
}