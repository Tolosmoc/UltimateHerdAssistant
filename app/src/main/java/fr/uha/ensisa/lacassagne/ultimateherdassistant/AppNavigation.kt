package fr.uha.ensisa.lacassagne.ultimateherdassistant

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import fr.uha.ensisa.lacassagne.ultimateherdassistant.database.DatabaseProvider
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.Animal
import fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.screen.*
import fr.uha.ensisa.lacassagne.ultimateherdassistant.MainActivity

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
        composable("animal_list") {
            AnimalScreen(navController = navController)
        }
        composable("add_animal") {
            AddAnimalScreen(navController = navController)
        }
        composable("modifyAnimal/{animalID}") { backStackEntry ->
            val animalId = backStackEntry.arguments?.getString("animalID")?.toIntOrNull() ?: return@composable
            val database = DatabaseProvider.getDatabase(navController.context)
            val animalState = produceState<Animal?>(initialValue = null, key1 = animalId) {
                value = database.animalDao().getById(animalId)
            }
            val animal = animalState.value

            if (animal != null) {
                ModifyAnimalScreen(
                    animal = animal,
                    onCancel = { navController.popBackStack() }
                )
            } else {
                Text("Animal not found", color = Color.Red, modifier = Modifier.padding(16.dp))
            }
        }
    }
}