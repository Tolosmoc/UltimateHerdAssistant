package fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.screen

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TextField
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import androidx.compose.foundation.Image
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Checkbox
import coil.compose.rememberImagePainter

import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.Animal
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.AnimalType
import fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel.AnimalViewModel
import fr.uha.ensisa.lacassagne.ultimateherdassistant.utils.saveImageToInternalStorage
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId

@Composable
fun AddAnimalScreen(viewModel: AnimalViewModel = viewModel(), navController: NavController) {
    var name by remember { mutableStateOf(TextFieldValue()) }
    var selectedType by remember { mutableStateOf<AnimalType?>(null) }
    var age by remember { mutableStateOf(TextFieldValue()) }
    var weight by remember { mutableStateOf(TextFieldValue()) }
    var height by remember { mutableStateOf(TextFieldValue()) }
    var picture by remember { mutableStateOf<String?>(null) }
    var birthday by remember { mutableStateOf(Date()) }
    var isExactBirthdayUnknown by remember { mutableStateOf(false) }
    var species by remember { mutableStateOf("") }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var dropdownExpanded by remember { mutableStateOf(false) }

    var nameError by remember { mutableStateOf<String?>(null) }
    var typeError by remember { mutableStateOf<String?>(null) }
    var ageError by remember { mutableStateOf<String?>(null) }
    var weightError by remember { mutableStateOf<String?>(null) }
    var heightError by remember { mutableStateOf<String?>(null) }
    var speciesError by remember { mutableStateOf<String?>(null) }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            birthday = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }.time
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val filePath = saveImageToInternalStorage(context, it)
            picture = filePath
        }
    }

    LazyColumn(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                isError = nameError != null
            )
            nameError?.let { Text(it, color = Color.Red) }
            Spacer(modifier = Modifier.height(8.dp))

            // Dropdown for selecting AnimalType
            Box {
                Button(onClick = { dropdownExpanded = true }) {
                    Text(
                        text = selectedType?.displayName ?: "Select Type ⌄"
                    )
                }
                DropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }
                ) {
                    AnimalType.values().forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type.displayName) },
                            onClick = {
                                selectedType = type
                                dropdownExpanded = false
                            }
                        )
                    }
                }
            }
            typeError?.let { Text(it, color = Color.Red) }
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = species,
                onValueChange = { species = it },
                label = { Text("Species") },
                isError = speciesError != null
            )
            speciesError?.let { Text(it, color = Color.Red) }
            Spacer(modifier = Modifier.height(8.dp))

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
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = weight,
                onValueChange = { weight = it },
                label = { Text("Weight") },
                isError = weightError != null
            )
            weightError?.let { Text(it, color = Color.Red) }
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = height,
                onValueChange = { height = it },
                label = { Text("Height") },
                isError = heightError != null
            )
            heightError?.let { Text(it, color = Color.Red) }
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = { galleryLauncher.launch("image/*") }) {
                    Text("Choose Picture")
                }
                Spacer(modifier = Modifier.width(8.dp))
                picture?.let {
                    Image(
                        painter = rememberImagePainter(it),
                        contentDescription = "Selected Picture",
                        modifier = Modifier.size(100.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    var isValid = true

                    if (name.text.isBlank()) {
                        nameError = "Name cannot be empty"
                        isValid = false
                    } else {
                        nameError = null
                    }

                    if (selectedType == null) {
                        typeError = "Please select a type"
                        isValid = false
                    } else {
                        typeError = null
                    }

                    if (species.isBlank()) {
                        speciesError = "Species cannot be empty"
                        isValid = false
                    } else {
                        speciesError = null
                    }

                    if ((age.text.isBlank() || age.text.toIntOrNull() == null) && isExactBirthdayUnknown) {
                        ageError = "Please enter a valid age"
                        isValid = false
                    } else {
                        ageError = null
                    }

                    if (weight.text.isBlank() || weight.text.toFloatOrNull() == null) {
                        weightError = "Please enter a valid weight"
                        isValid = false
                    } else {
                        weightError = null
                    }

                    if (height.text.isBlank() || height.text.toFloatOrNull() == null) {
                        heightError = "Please enter a valid height"
                        isValid = false
                    } else {
                        heightError = null
                    }

                    if (isValid) {
                        val calculatedAge = if (!isExactBirthdayUnknown) {
                            val birthDate = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                            Period.between(birthDate, LocalDate.now()).years
                        } else {
                            age.text.toInt()
                        }

                        val birthDateValue = if (!isExactBirthdayUnknown) birthday else null

                        val animal = Animal(
                            name = name.text,
                            type = selectedType!!,
                            age = calculatedAge,
                            weight = weight.text.toFloat(),
                            height = height.text.toFloat(),
                            picture = picture,
                            birthDate = birthDateValue,
                            species = species
                        )
                        viewModel.addAnimal(animal)

                        // Navigate back to the AnimalScreen
                        navController.popBackStack()
                    }
                }
            ) {
                Text("Add Animal")
            }
        }
    }
}