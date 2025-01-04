package fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.Activite
import fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel.ActiviteViewModel

@Composable
fun AddActivityScreen(animalId: Int, navController: NavController, viewModel: ActiviteViewModel = viewModel()) {
    var type by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf(TextFieldValue()) }
    var date by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }

    var typeError by remember { mutableStateOf<String?>(null) }
    var durationError by remember { mutableStateOf<String?>(null) }
    var dateError by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextField(
            value = type,
            onValueChange = { type = it },
            label = { Text("Type") },
            modifier = Modifier.fillMaxWidth(),
            isError = typeError != null
        )
        typeError?.let { Text(it, color = MaterialTheme.colors.error) }
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = duration,
            onValueChange = { duration = it },
            label = { Text("Duration (minutes)") },
            modifier = Modifier.fillMaxWidth(),
            isError = durationError != null
        )
        durationError?.let { Text(it, color = MaterialTheme.colors.error) }
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Date (dd-MM-yyyy)") },
            modifier = Modifier.fillMaxWidth(),
            isError = dateError != null
        )
        dateError?.let { Text(it, color = MaterialTheme.colors.error) }
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = comment,
            onValueChange = { comment = it },
            label = { Text("Comment") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                var isValid = true

                if (type.isBlank()) {
                    typeError = "Type cannot be empty"
                    isValid = false
                } else {
                    typeError = null
                }

                if (duration.text.toIntOrNull() == null) {
                    durationError = "Duration must be a valid number"
                    isValid = false
                } else if (duration.text.toInt() <= 0) {
                    durationError = "Duration must be greater than 0"
                    isValid = false
                } else {
                    durationError = null
                }

                if (date.isBlank()) {
                    dateError = "Date cannot be empty"
                    isValid = false
                } else {
                    dateError = null
                }


                if (isValid) {
                    viewModel.addActivity(
                        Activite(
                            type = type,
                            description = description,
                            duration = duration.text.toInt(),
                            date = date,
                            comment = comment
                        )
                    )
                    navController.popBackStack()
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add Activity")
        }
    }
}