package com.heyzeusv.clevertapassessment.di

import com.clevertap.android.sdk.CleverTapAPI
import com.clevertap.android.sdk.CleverTapAPI.LogLevel
import com.clevertap.android.sdk.CleverTapAPI.setDebugLevel
import com.heyzeusv.clevertapassessment.BuildConfig
import com.heyzeusv.clevertapassessment.ui.MainViewModel
import com.heyzeusv.clevertapassessment.ui.eventform.EventFormViewModel
import com.heyzeusv.clevertapassessment.ui.features.FeaturesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
	single {
		CleverTapAPI.getDefaultInstance(androidContext())?.apply {
			// set Log level depending on build variant
			if (BuildConfig.DEBUG) {
				setDebugLevel(LogLevel.VERBOSE)
			} else {
				setDebugLevel(LogLevel.OFF)
			}
		}
	}
	viewModelOf(::MainViewModel)
	viewModelOf(::FeaturesViewModel)
	viewModelOf(::EventFormViewModel)
}