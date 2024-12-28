package fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.screen

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.navigation.BottomNavigationBar

@Composable
fun MainScreen(navController: NavController) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        Text(
            text = "Welcome to the Main Screen",
            modifier = Modifier.padding(innerPadding).padding(16.dp)
        )
    }
}