package com.heyzeusv.clevertapassessment.ui.eventform

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.heyzeusv.clevertapassessment.ui.CreateLoginForm
import org.koin.androidx.compose.koinViewModel

@Composable
fun EventFormScreen(eventVM: EventFormViewModel = koinViewModel()) {
	val cleverTapId by eventVM.cleverTapId.collectAsState()

	Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
		Column(
			modifier = Modifier
				.padding(innerPadding)
				.fillMaxSize()
				.verticalScroll(rememberScrollState()),
			verticalArrangement = Arrangement.spacedBy(8.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			CreateLoginForm(
				cleverTapId = cleverTapId,
				createOnClick = eventVM::createAccountWithIdentity,
				loginOnClick = eventVM::logIntoAccountWithIdentity,
			)
		}
	}

}