package fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.screen
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel


import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.Animal
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.AnimalType
import fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel.AnimalViewModel

@Composable
fun ModifyAnimalScreen(
    animal: Animal,
    onCancel: () -> Unit,
    viewModel: AnimalViewModel = viewModel()
) {
    var name by remember { mutableStateOf(animal.name) }
    var type by remember { mutableStateOf(animal.type) }
    var age by remember { mutableStateOf(animal.age) }
    var weight by remember { mutableStateOf(animal.weight) }
    var height by remember { mutableStateOf(animal.height) }

    var nameError by remember { mutableStateOf<String?>(null) }
    var ageError by remember { mutableStateOf<String?>(null) }
    var weightError by remember { mutableStateOf<String?>(null) }
    var heightError by remember { mutableStateOf<String?>(null) }
    var dropdownExpanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = name,
            onValueChange = { name = it.trim() },
            label = { Text("Name") },
            isError = nameError != null,
            modifier = Modifier.fillMaxWidth()
        )
        nameError?.let { Text(text = it, color = Color.Red) }

        Box(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { dropdownExpanded = true }, modifier = Modifier.fillMaxWidth()) {
                Text(text = type.displayName ?: "Select Type ⌄")
            }
            DropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false }
            ) {
                AnimalType.values().forEach { animalType ->
                    DropdownMenuItem(
                        text = { Text(animalType.displayName) },
                        onClick = {
                            type = animalType
                            dropdownExpanded = false
                        }
                    )
                }
            }
        }

        TextField(
            value = age.toString(),
            onValueChange = { value -> age = value.toIntOrNull() ?: -1 },
            label = { Text("Age") },
            isError = ageError != null,
            modifier = Modifier.fillMaxWidth()
        )
        ageError?.let { Text(text = it, color = Color.Red) }

        TextField(
            value = weight.toString(),
            onValueChange = { value -> weight = value.toFloatOrNull() ?: -1f },
            label = { Text("Weight") },
            isError = weightError != null,
            modifier = Modifier.fillMaxWidth()
        )
        weightError?.let { Text(text = it, color = Color.Red) }

        TextField(
            value = height.toString(),
            onValueChange = { value -> height = value.toFloatOrNull() ?: -1f },
            label = { Text("Height") },
            isError = heightError != null,
            modifier = Modifier.fillMaxWidth()
        )
        heightError?.let { Text(text = it, color = Color.Red) }

        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { onCancel() }) { Text("Cancel") }
            Button(onClick = {
                val validationErrors = validateAnimalInputs(name, age, weight, height)
                nameError = validationErrors["name"]
                ageError = validationErrors["age"]
                weightError = validationErrors["weight"]
                heightError = validationErrors["height"]

                if (validationErrors.isEmpty()) {
                    // Create a new animal object with updated values
                    val updatedAnimal = animal.copy(
                        name = name,
                        type = type,
                        age = age,
                        weight = weight,
                        height = height
                    )
                    // Update the animal through ViewModel
                    viewModel.updateAnimal(updatedAnimal)
                    onCancel()
                }
            }) {
                Text("Save")
            }
        }
    }
}

fun validateAnimalInputs(
    name: String,
    age: Int,
    weight: Float,
    height: Float
): Map<String, String?> {
    val errors = mutableMapOf<String, String?>()
    if (name.isBlank()) errors["name"] = "Name cannot be empty"
    if (age <= 0) errors["age"] = "Age must be greater than 0"
    if (weight <= 0) errors["weight"] = "Weight must be greater than 0"
    if (height <= 0) errors["height"] = "Height must be greater than 0"
    return errors
}
