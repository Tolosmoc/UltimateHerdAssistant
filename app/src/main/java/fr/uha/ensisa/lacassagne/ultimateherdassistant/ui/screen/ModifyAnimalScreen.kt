package fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.screen
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel


import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.Animal
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.AnimalType
import fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel.AnimalViewModel
import java.text.SimpleDateFormat
import java.util.Date

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
    var picture by remember { mutableStateOf<String?>(animal.picture) }
    var birthday by remember { mutableStateOf<Date?>(animal.birthDate) }
    var isExactBirthdayUnknown by remember { mutableStateOf(birthday == null) }
    var species by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf<String?>(null) }
    var ageError by remember { mutableStateOf<String?>(null) }
    var weightInput by remember { mutableStateOf(animal.weight.toString()) }
    var weightError by remember { mutableStateOf<String?>(null) }
    var heightInput by remember { mutableStateOf(animal.height.toString()) }
    var heightError by remember { mutableStateOf<String?>(null) }
    // TODO : Add errir check for (type,) picture, birthday (& age), species
    var dropdownExpanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

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
            onValueChange = { value -> age = value.toIntOrNull() ?: 0 },
            label = { Text("Age") },
            isError = ageError != null,
            modifier = Modifier.fillMaxWidth()
        )
        ageError?.let { Text(text = it, color = Color.Red) }

        TextField(
            value = species,
            onValueChange = { species = it.trim() },
            label = { Text("Species") },
            modifier = Modifier.fillMaxWidth()
        )
        // ERROR : Missing

        /* TODO
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isExactBirthdayUnknown) {
                TextField(
                    value = age,
                    onValueChange = { age = it },
                    label = { Text("Age") },
                    isError = ageError != null,
                    modifier = Modifier.weight(1f)
                )
                ageError?.let { Text(it, color = Color.Red) }
            } else {
                Button(onClick = { datePickerDialog.show() }, modifier = Modifier.weight(1f)) {
                    Text(text = birthday?.let { SimpleDateFormat("dd/MM/yyyy").format(it) }
                        ?: "Select Birthday")
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Checkbox(
                checked = isExactBirthdayUnknown,
                onCheckedChange = { isExactBirthdayUnknown = it }
            )
            Text("Unknown exact birthdate")
        }
        */
        // ERROR : Missing

        TextField(
            value = weightInput,
            onValueChange = { value ->
                weightInput = value
                weightError = null
                weight = when {
                    value.isEmpty() -> 0f
                    value.toFloatOrNull() != null -> value.toFloat()
                    else -> {
                        weightError = "Invalid input"
                        weight
                    }
                }
            },
            label = { Text("Weight (g)") },
            isError = weightError != null,
            modifier = Modifier.fillMaxWidth()
        )
        weightError?.let { Text(text = it, color = Color.Red) }

        TextField(
            value = heightInput,
            onValueChange = { value ->
                heightInput = value
                heightError = null
                height = when {
                    value.isEmpty() -> 0f
                    value.toFloatOrNull() != null -> value.toFloat()
                    else -> {
                        heightError = "Invalid input"
                        height
                    }
                }
            },
            label = { Text("Height (cm)") },
            isError = heightError != null,
            modifier = Modifier.fillMaxWidth()
        )
        heightError?.let { Text(text = it, color = Color.Red) }

        TextField(
            value = picture ?: "",
            onValueChange = { picture = it.trim() },
            label = { Text("Picture") },
            modifier = Modifier.fillMaxWidth()
        )
        // ERROR : Missing
        // Diszplay pic missing

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
                    if (weight != animal.weight || height != animal.height) {
                        showDialog = true
                    } else {
                        viewModel.updateAnimal(updateAnimal(animal, name, type, age, weight, height, picture, birthday, species))
                        onCancel()
                    }
                }
            }) {
                Text("Save")
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Warning") },
            text = { Text("If you wish to modify the height or weight of an animal, you should add a track.") },
            dismissButton = {
                Button(
                    onClick = {
                        weight = animal.weight
                        height = animal.height
                        weightInput = animal.weight.toString()
                        heightInput = animal.height.toString()
                        showDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Cancel")
                }
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.updateAnimal(updateAnimal(animal, name, type, age, weight, height, picture, birthday, species))
                    showDialog = false
                    onCancel()
                }) {
                    Text("Save Anyway")
                }
            }
        )
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
    if (age < 0) errors["age"] = "Age must be greater than 0"
    if (weight <= 0) errors["weight"] = "Weight must be greater than 0"
    if (height <= 0) errors["height"] = "Height must be greater than 0"
    return errors
}

fun updateAnimal(
    animal: Animal,
    name: String,
    type: AnimalType,
    age: Int,
    weight: Float,
    height: Float,
    picture: String?,
    birthDate: Date?,
    species: String
): Animal {
    return animal.copy(
        name = name,
        type = type,
        age = age,
        weight = weight,
        height = height,
        picture = picture,
        birthDate = birthDate,
        species = species
    )
}