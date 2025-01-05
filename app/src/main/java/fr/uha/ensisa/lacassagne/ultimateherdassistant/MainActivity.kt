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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.navigation.BottomNavigationBar
import fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.theme.UltimateHerdAssistantTheme

@Composable
fun ExpandableFAB(navController: NavHostController) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            AnimatedVisibility(
                visible = expanded,
                enter = androidx.compose.animation.expandVertically(
                    animationSpec = tween(durationMillis = 300, easing = FastOutLinearInEasing)
                ),
                exit = androidx.compose.animation.shrinkVertically(
                    animationSpec = tween(durationMillis = 300, easing = FastOutLinearInEasing)
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SmallFloatingActionButton(
                        onClick = {
                            navController.navigate("add_animal")
                            expanded = false
                        },
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = Color.White
                    ) {
                        Icon(Icons.Default.Pets, contentDescription = "Add Animal")
                    }
                    SmallFloatingActionButton(
                        onClick = {
                            navController.navigate("add_stock")
                            expanded = false
                        },
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = Color.White
                    ) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Add Stock")
                    }
                    SmallFloatingActionButton(
                        onClick = {
                            navController.navigate("add_activity/{animalID}")
                            expanded = false
                        },
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = Color.White
                    ) {
                        Icon(Icons.Default.Event, contentDescription = "Add Activity")
                    }
                }
            }
            FloatingActionButton(
                onClick = { expanded = !expanded },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Default.Close else Icons.Default.Add,
                    contentDescription = if (expanded) "Close" else "Add"
                )
            }
        }
    }
}

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
                    floatingActionButton = { ExpandableFAB(navController) },

                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    AppNavigation(navController = navController, modifier = Modifier.padding(innerPadding))
                }
            }

        }
    }
}
