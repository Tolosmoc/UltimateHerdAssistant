package fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TextField
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.monzoo.app.presentation.viewmodel.AnimalViewModel

import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.Animal
//import fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel.AnimalViewModel

@Composable
fun AddAnimalScreen(viewModel : AnimalViewModel = viewModel(), navController: NavController) {
    var name by remember { mutableStateOf(TextFieldValue()) }
    var type by remember { mutableStateOf(TextFieldValue()) }
    var age by remember { mutableStateOf(TextFieldValue()) }
    var weight by remember { mutableStateOf(TextFieldValue()) }
    var height by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = type,
            onValueChange = { type = it },
            label = { Text("Type") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Weight") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Height") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                val animal = Animal(
                    name = name.text,
                    type = type.text,
                    age = age.text.toInt(),
                    weight = weight.text.toFloat(),
                    height = height.text.toFloat()
                )
                viewModel.addAnimal(animal)

                // Navigate back to the AnimalScreen
                navController.popBackStack()
            }
        ) {
            Text("Add Animal")
        }
    }
}