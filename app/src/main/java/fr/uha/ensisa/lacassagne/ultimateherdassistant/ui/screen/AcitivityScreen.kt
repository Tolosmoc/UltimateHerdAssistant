package fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel.ActiviteViewModel
import fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel.AnimalViewModel

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem

@Composable
fun ActivityScreen(navController: NavController, activiteViewModel: ActiviteViewModel = viewModel(), animalViewModel: AnimalViewModel = viewModel()) {
    val animals by animalViewModel.animals.observeAsState(initial = emptyList())
    var selectedAnimalId by remember { mutableStateOf<Int?>(null) }
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        // Dropdown menu for selecting animal
        Box(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
            Button(onClick = { expanded = true }) {
                val selectedAnimalName = animals.find { it.id == selectedAnimalId }?.name ?: "Select Animal"
                Text(selectedAnimalName)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("All") },
                    onClick = {
                        selectedAnimalId = null
                        expanded = false
                    }
                )
                animals.forEach { animal ->
                    DropdownMenuItem(
                        text = { Text(animal.name) },
                        onClick = {
                            selectedAnimalId = animal.id
                            expanded = false
                        }
                    )
                }
            }
        }

        val activitiesLiveData = if (selectedAnimalId != null) {
            activiteViewModel.getActivitiesByAnimalId(selectedAnimalId!!)
        } else {
            activiteViewModel.getActivities()
        }
        val activities by activitiesLiveData.observeAsState(initial = emptyList())

        val activityDetails = activities.map { activity ->
            val animal = animals.find { it.id == activity.animal_id }
            "${activity.date} - ${animal?.name ?: "Unknown"} - ${activity.type}"
        }

        LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(activityDetails) { detail ->
                Text(text = detail, modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}