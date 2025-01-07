package fr.uha.ensisa.lacassagne.ultimateherdassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.navigation.BottomNavigationBar
import fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.theme.UltimateHerdAssistantTheme
import fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel.AnimalViewModel
import fr.uha.ensisa.lacassagne.ultimateherdassistant.ExpandableFAB

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UltimateHerdAssistantTheme {
                val navController = rememberNavController()

                Scaffold(

                    topBar = {
                        androidx.compose.material.TopAppBar(title = { androidx.compose.material3.Text("Ultimate Herd Assistant") })
                    },
                    bottomBar = {
                        BottomNavigationBar(navController = navController)
                    },
                    floatingActionButton = {
                        val viewModel: AnimalViewModel = viewModel()
                        ExpandableFAB(navController, viewModel)
                    },

                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    AppNavigation(navController = navController, modifier = Modifier.padding(innerPadding))
                }
            }

        }
    }
}
