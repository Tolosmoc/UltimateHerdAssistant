package fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel.ActiviteViewModel
import fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel.AnimalViewModel

@Composable
fun ActivityScreen(navController: NavController, activiteViewModel: ActiviteViewModel = viewModel(), animalViewModel: AnimalViewModel = viewModel()) {
    val activities by activiteViewModel.getActivities().observeAsState(initial = emptyList())
    val animals by animalViewModel.animals.observeAsState(initial = emptyList())

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