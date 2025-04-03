package com.heyzeusv.clevertapassessment.util

import kotlinx.serialization.Serializable

sealed class Screen {
	@Serializable
	data object Home : Screen()
	@Serializable
	data object RedPill : Screen()
	@Serializable
	data object BluePill : Screen()
}