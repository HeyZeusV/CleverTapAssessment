package com.heyzeusv.clevertapassessment.di

import com.clevertap.android.sdk.CleverTapAPI
import com.heyzeusv.clevertapassessment.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
	single { CleverTapAPI.getDefaultInstance(androidContext()) }
	viewModelOf(::MainViewModel)
}