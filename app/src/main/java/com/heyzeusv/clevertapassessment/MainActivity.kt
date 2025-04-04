package com.heyzeusv.clevertapassessment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.clevertap.android.sdk.CTInboxListener
import com.clevertap.android.sdk.InAppNotificationButtonListener
import com.heyzeusv.clevertapassessment.ui.BluePillScreen
import com.heyzeusv.clevertapassessment.ui.PillScreen
import com.heyzeusv.clevertapassessment.ui.RedPillScreen
import com.heyzeusv.clevertapassessment.ui.theme.CleverTapAssessmentTheme
import com.heyzeusv.clevertapassessment.util.NotificationUtils
import com.heyzeusv.clevertapassessment.util.Pill.BLUE
import com.heyzeusv.clevertapassessment.util.Pill.RED
import com.heyzeusv.clevertapassessment.util.Screen
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel
import java.util.HashMap

class MainActivity : ComponentActivity(), InAppNotificationButtonListener, CTInboxListener {

	private val mainVM: MainViewModel by inject()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		mainVM.setInAppNotificationButtonListener(this)
		mainVM.setCTNotificationInboxListener(this)
		enableEdgeToEdge()
		setContent {
			CleverTapAssessmentTheme {
				KoinAndroidContext {
					CleverTapAssessmentApp(
						mainVM = mainVM,
						navController = rememberNavController(),
					)
				}
			}
		}
	}

	override fun onNewIntent(intent: Intent) {
		super.onNewIntent(intent)

		// On Android 12, Raise notification clicked event when Activity is already running in activity backstack
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
			mainVM.handleIntent(intent.extras)
			NotificationUtils.dismissNotification(intent, applicationContext)
		}
	}

	override fun onAttachedToWindow() {
		super.onAttachedToWindow()

		// Clear notification when intent contains extras.
		this.intent.extras?.let {
			mainVM.handleIntent(it)
			NotificationUtils.dismissNotification(this.intent, applicationContext)
		}
	}

	override fun onInAppButtonClick(p0: HashMap<String, String>?) {
		p0?.let {
			mainVM.handleCallToAction(p0["In-App Selected Pill"] ?: "")
		}
	}

	override fun inboxDidInitialize() {
		mainVM.updateInboxInitialized(true)
	}

	override fun inboxMessagesDidUpdate() {
		Log.i("CleverTapAssessment", "inboxMessagesDidUpdate() called")
	}
}

@Composable
fun CleverTapAssessmentApp(
	mainVM: MainViewModel,
	navController: NavHostController,
) {
	val pillSelection by mainVM.pillSelection.collectAsState()

	LaunchedEffect(pillSelection) {
		if (pillSelection != Screen.Home) {
			navController.navigate(pillSelection)
		}
	}
	NavHost(
		navController = navController,
		startDestination = Screen.Home,
	) {
		composable<Screen.Home> { MainScreen(mainVM) }
		composable<Screen.RedPill> { RedPillScreen() }
		composable<Screen.BluePill> { BluePillScreen() }
		composable<Screen.Pill>(
			deepLinks = listOf(
				navDeepLink<Screen.Pill>(basePath = "https://www.clevertap-jesus.com")
			),
		) { backStackEntry ->
			val pillSelected = backStackEntry.toRoute<Screen.Pill>().pill
			when (pillSelected) {
				"red-pill" -> PillScreen(RED)
				else -> PillScreen(BLUE)
			}
		}
	}
}

@Composable
fun MainScreen(mainVM: MainViewModel = koinViewModel()) {
	val cleverTapId by mainVM.cleverTapId.collectAsState()
	val inboxInitialized by mainVM.inboxInitialized.collectAsState()
	val remoteConfig by mainVM.remoteConfig.collectAsState()

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
				enabled = cleverTapId != "Loading",
			) {
				Text("Product Viewed Event")
			}
			Button(
				onClick = mainVM::selectPillEvent,
				enabled = cleverTapId != "Loading",
			) {
				Text("Select Pill Event")
			}
			Card {
				Column(modifier = Modifier.padding(all = 8.dp)) {
					Row(
						modifier = Modifier.fillMaxWidth(),
						horizontalArrangement = Arrangement.spacedBy(8.dp),
						verticalAlignment = Alignment.CenterVertically,
					) {
						Button(
							onClick = mainVM::inAppBasicEvent,
							modifier = Modifier.weight(1f),
							enabled = cleverTapId != "Loading",
						) {
							Text("Basic")
						}
						Text(
							text = "In-App",
							style = MaterialTheme.typography.titleMedium.copy(
								textAlign = TextAlign.Center,
							),
						)
						Button(
							onClick = mainVM::inAppDeepLinkEvent,
							modifier = Modifier.weight(1f),
							enabled = cleverTapId != "Loading",
						) {
							Text("Deep Link")
						}
					}
					Row(
						modifier = Modifier.fillMaxWidth(),
						horizontalArrangement = Arrangement.spacedBy(8.dp),
						verticalAlignment = Alignment.CenterVertically,
					) {
						Button(
							onClick = mainVM::inAppResume,
							modifier = Modifier.weight(1f),
							enabled = cleverTapId != "Loading",
						) {
							Text("Resume")
						}
						Button(
							onClick = mainVM::inAppSuspend,
							modifier = Modifier.weight(1f),
							enabled = cleverTapId != "Loading",
						) {
							Text("Suspend")
						}
						Button(
							onClick = mainVM::inAppDiscard,
							modifier = Modifier.weight(1f),
							enabled = cleverTapId != "Loading",
						) {
							Text("Discard")
						}
					}
				}
			}
			Button(
				onClick = { if (inboxInitialized) { mainVM.showAppInbox() } }
			) {
				Text("Show App Inbox")
			}
			Card {
				Column(modifier = Modifier.padding(all = 8.dp)) {
					Row(
						modifier = Modifier.fillMaxWidth(),
						horizontalArrangement = Arrangement.spacedBy(8.dp),
						verticalAlignment = Alignment.CenterVertically,
					) {
						Text(
							text = "Remote Config",
							modifier = Modifier.weight(1f),
							style = MaterialTheme.typography.titleMedium.copy(
								textAlign = TextAlign.Center,
							),
						)
						Button(
							onClick = mainVM::fetchVariables,
							modifier = Modifier.weight(1f),
						) {
							Text("Fetch Variables")
						}
					}
					Row(
						modifier = Modifier.fillMaxWidth(),
						horizontalArrangement = Arrangement.spacedBy(8.dp),
						verticalAlignment = Alignment.CenterVertically,
					) {
						Text(
							text = "Int: ${remoteConfig.int}\nLong: ${remoteConfig.long}\nFloat: ${remoteConfig.float}",
							modifier = Modifier.weight(1f),
						)
						Text(
							text = "Double: ${remoteConfig.double}\nString: ${remoteConfig.string}\n${remoteConfig.boolean}",
							modifier = Modifier.weight(1f),
						)
					}
				}
			}
		}
	}
}