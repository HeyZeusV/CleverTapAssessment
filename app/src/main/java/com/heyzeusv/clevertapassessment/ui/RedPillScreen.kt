package com.heyzeusv.clevertapassessment.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RedPillScreen() {
	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					Text(text = "Red Pill")
				},
				colors = TopAppBarDefaults.topAppBarColors().copy(
					containerColor = Color.Red,
					titleContentColor = Color.White
				),
			)
		},
		modifier = Modifier.fillMaxSize(),
	) { innerPadding ->
		Box(
			modifier = Modifier
				.padding(innerPadding)
				.fillMaxSize()
		) {
			Text(
				text = "You take the red pill... you stay in Wonderland, and I show you how deep the rabbit hole goes.",
				textAlign = TextAlign.Center,
			)
		}
	}
}