package com.heyzeusv.clevertapassessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
	Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
		Column(
			modifier = Modifier
				.padding(innerPadding)
				.fillMaxSize(),
			verticalArrangement = Arrangement.spacedBy(8.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
		) {
			Text(text = "AccountId: ${mainVM.getCleverTapAccountId()}")
			Button(onClick = { mainVM.createDummyAccount() }) {
				Text("Create/reset dummy account")
			}
			Button(onClick = { mainVM.updateDummyAccountMyStuff() }) {
				Text("Update dummy stuff")
			}
		}
	}
}