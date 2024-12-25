package fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.screen

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SplashScreen(navController: NavController) {
    var textU by remember { mutableStateOf("U") }
    var textH by remember { mutableStateOf("H") }
    var textA by remember { mutableStateOf("A") }

    LaunchedEffect(Unit) {
        delay(2000) // Delay for 2 seconds
        textU = "Ultimate"
        textH = "Herd"
        textA = "Assistant"
        delay(2000) // Delay for another 2 seconds
        navController.navigate("main_screen") {
            popUpTo("splash_screen") { inclusive = true }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedContent(targetState = textU, transitionSpec = {
                fadeIn(animationSpec = tween(1000)) with fadeOut(animationSpec = tween(1000))
            }) { targetText ->
                Text(
                    text = targetText,
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 30.sp),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            AnimatedContent(targetState = textH, transitionSpec = {
                fadeIn(animationSpec = tween(1000)) with fadeOut(animationSpec = tween(1000))
            }) { targetText ->
                Text(
                    text = targetText,
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 30.sp),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            AnimatedContent(targetState = textA, transitionSpec = {
                fadeIn(animationSpec = tween(1000)) with fadeOut(animationSpec = tween(1000))
            }) { targetText ->
                Text(
                    text = targetText,
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 30.sp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}