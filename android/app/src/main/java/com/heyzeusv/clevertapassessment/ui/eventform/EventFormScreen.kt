package com.heyzeusv.clevertapassessment.ui.eventform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.heyzeusv.clevertapassessment.ui.CreateLoginForm
import org.koin.androidx.compose.koinViewModel

@Composable
fun EventFormScreen(eventVM: EventFormViewModel = koinViewModel()) {
	val cleverTapId by eventVM.cleverTapId.collectAsState()
	val eventName by eventVM.eventName.collectAsState()
	val eventProperties by eventVM.eventProperties.collectAsState()

	Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
		Column(
			modifier = Modifier
				.padding(innerPadding)
				.padding(horizontal = 8.dp)
				.fillMaxSize(),
			verticalArrangement = Arrangement.spacedBy(8.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			CreateLoginForm(
				cleverTapId = cleverTapId,
				createOnClick = eventVM::createAccountWithIdentity,
				loginOnClick = eventVM::logIntoAccountWithIdentity,
			)
			OutlinedTextField(
				value = eventName,
				onValueChange = { eventVM.updateEventName(it) },
				modifier = Modifier.fillMaxWidth(),
				label = { Text("Event Name") },
			)
			LazyColumn(
				modifier = Modifier
					.weight(1f)
					.fillMaxWidth()
			) {
				itemsIndexed(eventProperties) { index, item ->
					var key by remember { mutableStateOf(item.first) }
					var value by remember { mutableStateOf(item.second) }

					Row(
						horizontalArrangement = Arrangement.spacedBy(8.dp),
						verticalAlignment = Alignment.CenterVertically,
					) {
						OutlinedTextField(
							value = key,
							onValueChange = {
								key = it
								eventVM.updateEventProperties(index, it, value)
							},
							modifier = Modifier.weight(1f),
							label = { Text("Property Key") },
						)
						OutlinedTextField(
							value = value,
							onValueChange = {
								value = it
								eventVM.updateEventProperties(index, key, it)
							},
							modifier = Modifier.weight(1f),
							label = { Text("Property Value") },
						)
						if (index == eventProperties.size - 1) {
							IconButton(onClick = eventVM::addEventProperty) {
								Icon(
									imageVector = Icons.Default.AddCircle,
									contentDescription = null,
								)
							}
						}
					}
				}
			}
			Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
				Button(
					onClick = eventVM::pushEvent,
					modifier = Modifier.weight(1f)
				) {
					Text("Push Event")
				}
				Button(
					onClick = eventVM::resetForm,
					modifier = Modifier.weight(1f),
				) {
					Text("Reset")
				}
			}
		}
	}

}