package com.heyzeusv.clevertapassessment.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.heyzeusv.clevertapassessment.R

@Composable
fun MainScreen(
	featuresOnClick: () -> Unit,
	eventFormOnClick: () -> Unit,
	pushTemplatesOnClick: () -> Unit,
) {
	Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
		Column(
			modifier = Modifier
				.padding(innerPadding)
				.fillMaxSize(),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			Image(
				painterResource(R.drawable.clevertaplogo),
				contentDescription = null,
				modifier = Modifier.size(200.dp),
			)
			Button(onClick = featuresOnClick) {
				Text("Features")
			}
			Button(onClick = eventFormOnClick) {
				Text("Event Form")
			}
			Button(onClick = pushTemplatesOnClick) {
				Text("Push Templates")
			}
		}
	}
}