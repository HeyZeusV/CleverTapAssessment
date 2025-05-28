package com.heyzeusv.clevertapassessment.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CreateLoginForm(
	cleverTapId: String,
	createOnClick: (String) -> Unit,
	loginOnClick: (String) -> Unit,
) {
	var identity by remember { mutableStateOf("") }

	Text(
		text = "Current AccountId:\n$cleverTapId",
		textAlign = TextAlign.Center,
	)
	OutlinedTextField(
		value = identity,
		onValueChange = { identity = it },
		modifier = Modifier.fillMaxWidth(),
		label = { Text("Identity") },
	)
	Row(
		modifier = Modifier.fillMaxWidth(),
		horizontalArrangement = Arrangement.spacedBy(8.dp),
		verticalAlignment = Alignment.CenterVertically,
	) {
		Button(
			onClick = { createOnClick(identity) },
			modifier = Modifier.weight(1f),
		) {
			Text(
				text = "Create Account With Identity",
				textAlign = TextAlign.Center,
			)
		}
		Button(
			onClick = { loginOnClick(identity) },
			modifier = Modifier.weight(1f),
		) {
			Text(
				text = "Log Into Account With Identity",
				textAlign = TextAlign.Center,
			)
		}
	}
}