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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BluePillScreen() {
	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					Text(text = "Blue Pill")
				},
				colors = TopAppBarDefaults.topAppBarColors().copy(
					containerColor = Color.Blue,
					titleContentColor = Color.White
				),
			)
		},
		modifier = Modifier.fillMaxSize(),
	) { innerPadding ->
		Box(
			modifier = Modifier
				.padding(innerPadding)
				.fillMaxSize(),
			contentAlignment = Alignment.Center,
		) {
			Text(
				text = "You take the blue pill... the story ends, you wake up in your bed and believe whatever you want to believe.",
				textAlign = TextAlign.Center,
			)
		}
	}
}