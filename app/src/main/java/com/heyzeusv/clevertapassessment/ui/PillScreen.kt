package com.heyzeusv.clevertapassessment.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.heyzeusv.clevertapassessment.util.Pill

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PillScreen(pill: Pill) {
	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					Text(text = pill.title)
				},
				colors = TopAppBarDefaults.topAppBarColors().copy(
					containerColor = pill.color,
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
				text = pill.text,
				style = MaterialTheme.typography.headlineMedium.copy(
					textAlign = TextAlign.Center,
				),
			)
		}
	}
}