package com.heyzeusv.clevertapassessment

import android.util.Log
import androidx.lifecycle.ViewModel
import com.clevertap.android.sdk.CleverTapAPI
import java.util.Date
import kotlin.random.Random

class MainViewModel(private val cleverTapAPI: CleverTapAPI) : ViewModel() {

	fun getCleverTapAccountId(): String = cleverTapAPI.accountId

	fun createDummyAccount() {
		val profileUpdate = HashMap<String, Any>()
		profileUpdate["Name"] = "Test Dummy" // String
		profileUpdate["Identity"] = 1234 // String or number
		profileUpdate["Email"] = "test@gmail.com" // Email address of the user
		profileUpdate["Phone"] = "+14155551234" // Phone (with the country code, starting with +)
		profileUpdate["Gender"] = "M" // Can be either M or F
		profileUpdate["DOB"] = Date() // Date of Birth. Set the Date object to the appropriate value first


		val otherStuff = arrayOf("Random", "Stuff")
		profileUpdate["MyStuff"] = otherStuff //String Array
		cleverTapAPI.onUserLogin(profileUpdate)
	}

	fun updateDummyAccountMyStuff() {
		val profileUpdate = HashMap<String, Any>()

		val stuff = mutableListOf<String>()
		repeat(Random.nextInt(0, 5)) {
			stuff.add(randomString())
		}
		Log.i("CleverTapAssessment", "Updated MyStuff: $stuff")
		profileUpdate["MyStuff"] = stuff
		cleverTapAPI.pushProfile(profileUpdate)
	}

	private fun randomString(): String {
		val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

		return (1..10)
			.map { Random.nextInt(0, charPool.size).let { charPool[it] } }
			.joinToString("")
	}
}