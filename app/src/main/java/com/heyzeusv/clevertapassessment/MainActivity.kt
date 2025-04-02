package com.heyzeusv.clevertapassessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.heyzeusv.clevertapassessment.ui.theme.CleverTapAssessmentTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		enableEdgeToEdge()
		setContent {
			CleverTapAssessmentTheme {
				MainScreen()
			}
		}
	}
}

@Composable
fun MainScreen(mainVM: MainViewModel = koinViewModel()) {
	Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
		Text(
			text = "AccountId: ${mainVM.getCleverTapAccountId()}",
			modifier = Modifier.padding(innerPadding)
		)
	}
}