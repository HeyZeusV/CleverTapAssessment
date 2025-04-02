package com.heyzeusv.clevertapassessment

import androidx.lifecycle.ViewModel
import com.clevertap.android.sdk.CleverTapAPI

class MainViewModel(private val cleverTapAPI: CleverTapAPI) : ViewModel() {

	fun getCleverTapAccountId(): String = cleverTapAPI.accountId
}