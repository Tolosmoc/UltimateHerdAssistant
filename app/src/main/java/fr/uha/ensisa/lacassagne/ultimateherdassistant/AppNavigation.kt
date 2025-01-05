package fr.uha.ensisa.lacassagne.ultimateherdassistant

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fr.uha.ensisa.lacassagne.ultimateherdassistant.database.DatabaseProvider
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.Animal
import fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.screen.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "splash_screen",
        modifier = modifier
    ) {
        composable("splash_screen") {
            SplashScreen(navController = navController)
        }
        composable("main_screen") {
            MainScreen(navController = navController)
        }
        composable("dashboard") {
            DashboardScreen(navController = navController)
        }
        composable("information") {
            InformationScreen(navController = navController)
        }
        composable("animal_list") {
            AnimalListScreen(navController = navController)
        }
        composable("add_animal") {
            AddAnimalScreen(navController = navController)
        }
        composable("modifyAnimal/{animalID}") { backStackEntry ->
            val animalId = backStackEntry.arguments?.getString("animalID")?.toIntOrNull() ?: return@composable
            val database = DatabaseProvider.getDatabase(navController.context)
            var animal by remember { mutableStateOf<Animal?>(null) }
            val scope = rememberCoroutineScope()

            LaunchedEffect(animalId) {
                scope.launch(Dispatchers.IO) {
                    animal = database.animalDao().getById(animalId)
                }
            }

            if (animal != null) {
                ModifyAnimalScreen(
                    animal = animal!!,
                    onCancel = { navController.popBackStack() }
                )
            } else {
                Text("Animal not found", color = Color.Red, modifier = Modifier.padding(16.dp))
            }
        }
        composable("animal/{animalID}") { backStackEntry ->
            val animalId = backStackEntry.arguments?.getString("animalID")?.toIntOrNull() ?: return@composable
            val database = DatabaseProvider.getDatabase(navController.context)
            var animal by remember { mutableStateOf<Animal?>(null) }
            val scope = rememberCoroutineScope()

            LaunchedEffect(animalId) {
                scope.launch(Dispatchers.IO) {
                    animal = database.animalDao().getById(animalId)
                }
            }

            if (animal != null) {
                AnimalScreen(
                    animal = animal!!,
                    navController = navController)
            } else {
                Text("Animal not found", color = Color.Red, modifier = Modifier.padding(16.dp))
            }
        }
        composable("add_track/{animalID}") { backStackEntry ->
            val animalId = backStackEntry.arguments?.getString("animalID")?.toIntOrNull() ?: return@composable
            AddTrackScreen(animalId = animalId, navController = navController)
        }

        composable("activity") {
            ActivityScreen(navController = navController)
        }

        composable("add_activity/{animalID}") { backStackEntry ->
            val animalId = backStackEntry.arguments?.getString("animalID")?.toIntOrNull() ?: return@composable
            AddActivityScreen(animalId = animalId, navController = navController)
        }

        composable("stock") {
            StockScreen(navController = navController)
        }

        composable("add_stock") {
            AddStockScreen(navController = navController)
        }
    }
}