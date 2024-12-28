package fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.screen

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.Tracker
import fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel.TrackerViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddTrackScreen(animalId: Int, navController: NavController) {
    val viewModel: TrackerViewModel = viewModel()
    var date by remember { mutableStateOf("") }
    var temperature by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            date = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { datePickerDialog.show() }) {
            Text(text = if (date.isEmpty()) "Select Date" else date)
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = temperature,
            onValueChange = { temperature = it },
            label = { Text("Temperature") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Weight") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val parsedDate = dateFormat.parse(date) ?: Date()
            val tracker = Tracker(
                animalId = animalId, // TODO: Get animalId from arguments
                date = parsedDate,
                temperature = temperature.toFloatOrNull() ?: 0f,
                weight = weight.toFloatOrNull() ?: 0f
            )
            viewModel.addTracker(tracker)
            navController.popBackStack()
        }) {
            Text("Save")
        }
    }
}