package fr.uha.ensisa.lacassagne.ultimateherdassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.theme.UltimateHerdAssistantTheme

import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar // if material3 - warning : This material API is experimental and is likely to change or to be removed in the future.

import fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.navigation.BottomNavigationBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            UltimateHerdAssistantTheme {
                val navController = rememberNavController()

                Scaffold(
                    topBar = {
                        TopAppBar(title = { androidx.compose.material3.Text("Ultimate Herd Assistant") })
                    },
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            navController.navigate("addAnimal")
                        }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Animal")
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    AppNavigation(navController = navController, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}