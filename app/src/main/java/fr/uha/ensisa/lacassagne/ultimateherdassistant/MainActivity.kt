package fr.uha.ensisa.lacassagne.ultimateherdassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize // ???
import androidx.compose.foundation.layout.padding // ???
import androidx.compose.material3.Scaffold // ???
import androidx.compose.material3.Text // ???
import androidx.compose.runtime.Composable // ???
import androidx.compose.ui.Modifier // ???
import androidx.compose.ui.tooling.preview.Preview // ???
import fr.uha.ensisa.lacassagne.ultimateherdassistant.database.DatabaseProvider
import fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.theme.UltimateHerdAssistantTheme
//import fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel.AnimalViewModel
import fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.screen.AnimalScreen
import fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.screen.AddAnimalScreen



import androidx.compose.material.TopAppBar // if material3 - warning : This material API is experimental and is likely to change or to be removed in the future.
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


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
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            navController.navigate("addAnimal")
                        }) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Animal")
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "animalList",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("animalList") {
                            AnimalScreen()
                        }
                        composable("addAnimal") {
                            AddAnimalScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimalScreenPreview() {
    UltimateHerdAssistantTheme {
        AnimalScreen()
    }
}

/*
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UltimateHerdAssistantTheme {
        Greeting("Android")
    }
}
*/