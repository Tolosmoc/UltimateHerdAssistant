package fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fr.uha.ensisa.lacassagne.ultimateherdassistant.R
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.Animal
import fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel.AnimalViewModel

@Composable
fun AnimalScreen(animal: Animal, viewModel: AnimalViewModel = viewModel(), navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        HeaderSection(animal = animal)
        Button(onClick = { showDialog = true }) {
            Text("Show Details")
        }
        if (showDialog) {
            AnimalDetailDialog(
                animal = animal,
                onDismiss = { showDialog = false },
                onModify = {
                    navController.navigate("modifyAnimal/${animal.id}")
                }
            )
        }
    }
}

@Composable
fun HeaderSection(animal: Animal) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        /* TODO: Add image
        Image(

            painter = painterResource(id = R.drawable.animal_photo_placeholder),
            contentDescription = "Animal Photo",
            modifier = Modifier.size(128.dp)
        )
        */
        Text(text = "🐾 ${animal.name} (${animal.type})", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Type: ${animal.type}", style = MaterialTheme.typography.bodyMedium)
        // Text(text = "Born on: ${animal.dateOfBirth}", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun AnimalDetailDialog(animal: Animal, onDismiss: () -> Unit, onModify: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Animal Details") },
        text = {
            Column {
                Text("ID: ${animal.id}")
                Text("Name: ${animal.name}")
                Text("Type: ${animal.type}")
                Text("Age: ${animal.age} years")
                Text("Weight: ${animal.weight} kg")
                Text("Height: ${animal.height} cm")
            }
        },
        confirmButton = {
            Row {
                // Modify Button
                TextButton(onClick = onModify) {
                    Text("Modify")
                }

                // Close Button
                TextButton(onClick = onDismiss) {
                    Text("Close")
                }
            }
        }
    )
}

/* TODO: Add ActivityHistorySection, FeedingHistorySection and FollowUpHistorySection
@Composable
fun ActivityHistorySection(animal: Animal) {
    Column {
        Text(text = "Recent Activities", style = MaterialTheme.typography.h6)
        LazyColumn {
            items(animal.activities.size) { index ->
                val activity = animal.activities[index]
                Text(text = "${activity.description} (${activity.date})", style = MaterialTheme.typography.body2)
            }
        }
    }
}
*/

/* TODO: Add FeedingHistorySection and FollowUpHistorySection
@Composable
fun FeedingHistorySection(animal: Animal) {
    Column {
        Text(text = "Recent Feedings", style = MaterialTheme.typography.h6)
        LazyColumn {
            items(animal.feedings.size) { index ->
                val feeding = animal.feedings[index]
                Text(text = "${feeding.food} (${feeding.amount}kg) (${feeding.date})", style = MaterialTheme.typography.body2)
            }
        }
    }
}

@Composable
fun FollowUpHistorySection(animal: Animal) {
    Column {
        Text(text = "Recent Follow-ups", style = MaterialTheme.typography.h6)
        LazyColumn {
            items(animal.followUps.size) { index ->
                val followUp = animal.followUps[index]
                Text(text = "${followUp.description}: ${followUp.value} (${followUp.date})", style = MaterialTheme.typography.body2)
            }
        }
    }
}
*/