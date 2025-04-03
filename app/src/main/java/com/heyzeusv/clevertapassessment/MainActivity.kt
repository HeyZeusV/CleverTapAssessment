package com.heyzeusv.clevertapassessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.heyzeusv.clevertapassessment.ui.theme.CleverTapAssessmentTheme
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		enableEdgeToEdge()
		setContent {
			CleverTapAssessmentTheme {
				KoinAndroidContext {
					MainScreen()
				}
			}
		}
	}
}

@Composable
fun MainScreen(mainVM: MainViewModel = koinViewModel()) {
	val cleverTapId by mainVM.cleverTapId.collectAsState()

	var productId by remember { mutableStateOf("1") }
	var productName by remember { mutableStateOf("CleverTap") }
	var emailId by remember { mutableStateOf("jesus") }

	Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
		Column(
			modifier = Modifier
				.padding(innerPadding)
				.padding(horizontal = 8.dp)
				.fillMaxSize(),
			verticalArrangement = Arrangement.spacedBy(8.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			Text(
				text = "Current AccountId:\n$cleverTapId",
				textAlign = TextAlign.Center,
			)
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.spacedBy(8.dp),
				verticalAlignment = Alignment.CenterVertically,
			) {
				Button(
					onClick = { mainVM.logIntoAccountWithIdentity("TestUserOne") },
					enabled = cleverTapId == "__49260b0655ff41fea8673cd49857805d",
				) {
					Text("Log into Test User One")
				}
				Button(
					onClick = { mainVM.logIntoAccountWithIdentity("TestUserTwo") },
					enabled = cleverTapId == "__7b5d4f0e434546f59ff23324b37bd730",
				) {
					Text("Log into Test User Two")
				}
			}
			Button(
				onClick = { mainVM.updateMyStuff() },
				enabled = cleverTapId != "Loading",
			) {
				Text("Update dummy stuff")
			}
			OutlinedTextField(
				value = productId,
				onValueChange = { productId = it },
				modifier = Modifier.fillMaxWidth(),
				label = { Text("Product Id") },
				keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
			)
			OutlinedTextField(
				value = productName,
				onValueChange = { productName = it },
				modifier = Modifier.fillMaxWidth(),
				label = { Text("Product Name") },
			)
			OutlinedTextField(
				value = emailId,
				onValueChange = { emailId = it },
				modifier = Modifier.fillMaxWidth(),
				label = { Text("Email Id") },
			)
			Button(
				onClick = { mainVM.productViewedEvent(productId, productName, emailId) },
				enabled = cleverTapId != "Loading"
			) {
				Text("Product Viewed Event")
			}
		}
	}
}