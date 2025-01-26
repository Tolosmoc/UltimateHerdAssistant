package fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel.AnimalViewModel
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.Animal
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.AnimalType

@Composable
fun AnimalListScreen(modifier: Modifier = Modifier, viewModel: AnimalViewModel = viewModel(), navController: NavController) {
    val animauxState = viewModel.animals.observeAsState(initial = emptyList())
    val animaux = animauxState.value
    var selectedAnimal by remember { mutableStateOf<Animal?>(null) }
    var selectedType by remember { mutableStateOf<AnimalType?>(null) }

    Column(modifier = modifier.padding(16.dp)) {
        // Dropdown menu for selecting animal type
        var expanded by remember { mutableStateOf(false) }
        Box(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
            Text(
                text = selectedType?.displayName ?: "Select Animal Type",
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = true }
                    .background(Color.LightGray)
                    .padding(16.dp)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("All") },
                    onClick = {
                        selectedType = null
                        expanded = false
                    }
                )
                AnimalType.values().forEach { type ->
                    DropdownMenuItem(
                        text = { Text(type.displayName) },
                        onClick = {
                            selectedType = type
                            expanded = false
                        }
                    )
                }
            }
        }

        val filteredAnimaux = if (selectedType != null) {
            animaux.filter { it.type == selectedType }
        } else {
            animaux
        }

        LazyColumn(modifier = modifier.padding(16.dp)) {
            items(filteredAnimaux.size) { index ->
                val animal = filteredAnimaux[index]
                //Text("${animal.name} \n - ${animal.type} \n - ${animal.age} ans \n - ${animal.weight} \n - ${animal.height}")

                SwipeToManageItem(
                    animal = animal,
                    onClick = { selectedAnimal = animal },
                    onUpdate = {
                        selectedAnimal = null
                        navController.navigate("modifyAnimal/${animal.id}")
                    },
                    onDelete = { viewModel.deleteAnimal(animal) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        selectedAnimal?.let {
            selectedAnimal = null
            navController.navigate("animal/${it.id}")
        }
    }
}


@Composable
fun SwipeToManageItem(
    animal: Animal,
    onClick: () -> Unit,
    onUpdate: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier
) {
    var currentWidth by remember { mutableStateOf(1f) }
    var current = currentWidth
    var isDragging by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        // Snap to closest state when the drag ends
                        currentWidth = when {
                            currentWidth < current - 0.05f -> 0.5f
                            currentWidth > current + 0.05f -> 1f
                            else -> currentWidth
                        }
                        current = currentWidth
                        isDragging = false
                    },
                    onDragStart = {
                        isDragging = true
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        if (isDragging) {
                            currentWidth = (currentWidth + dragAmount / 2000).coerceIn(0.5f, 1f)
                        }
                    }
                )
            }
    ) {
        if (currentWidth < 0.9f) {
            // Action buttons only visible when the row width is significantly reduced
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(1f - currentWidth)
                    .align(Alignment.CenterEnd),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clickable { onUpdate() }
                        .background(Color.Blue)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Update", color = Color.White)
                }
                Box(
                    modifier = Modifier
                        .clickable { onDelete() }
                        .background(Color.Red)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Delete", color = Color.White)
                }
            }
        }

        // Main content remains fixed at the left side
        Row(
            modifier = Modifier
                .fillMaxWidth(fraction = currentWidth)
                .background(Color.LightGray)
                .padding(16.dp)
                .clickable { onClick() },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = animal.name,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
        }
    }
}





@Composable
fun AnimalDetailDialog2(animal: Animal, onDismiss: () -> Unit, onModify: () -> Unit) {
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
