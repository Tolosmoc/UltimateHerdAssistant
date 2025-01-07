package fr.uha.ensisa.lacassagne.ultimateherdassistant

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel.AnimalViewModel

@Composable
fun ExpandableFAB(navController: NavHostController, viewModel: AnimalViewModel) {
    var expanded by remember { mutableStateOf(false) }
    var showDialogSelectAnimal by remember { mutableStateOf(false) }
    var dropDownMenuSelectAnimal by remember { mutableStateOf(false) }

    var selectedAnimalId by remember { mutableStateOf<Int?>(null) }
    var selectedAnimalName by remember { mutableStateOf<String>("No animal selected") }
    val animals by viewModel.getAllAnimals().observeAsState(emptyList())


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            AnimatedVisibility(
                visible = expanded,
                enter = androidx.compose.animation.expandVertically(
                    animationSpec = tween(durationMillis = 300, easing = FastOutLinearInEasing)
                ),
                exit = androidx.compose.animation.shrinkVertically(
                    animationSpec = tween(durationMillis = 300, easing = FastOutLinearInEasing)
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SmallFloatingActionButton(
                        onClick = {
                            navController.navigate("add_animal")
                            expanded = false
                        },
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = Color.White
                    ) {
                        Icon(Icons.Default.Pets, contentDescription = "Add Animal")
                    }
                    SmallFloatingActionButton(
                        onClick = {
                            navController.navigate("add_stock")
                            expanded = false
                        },
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = Color.White
                    ) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Add Stock")
                    }
                    SmallFloatingActionButton(
                        onClick = {
                            showDialogSelectAnimal = true
                            //navController.navigate("add_activity/{$animalID}")
                            expanded = false
                        },
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = Color.White
                    ) {
                        Icon(Icons.Default.Event, contentDescription = "Add Activity")
                    }
                }
            }
            FloatingActionButton(
                onClick = { expanded = !expanded },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Default.Close else Icons.Default.Add,
                    contentDescription = if (expanded) "Close" else "Add"
                )
            }

            if (showDialogSelectAnimal) {
                AlertDialog(
                    onDismissRequest = { showDialogSelectAnimal = false },
                    title = { Text("Select an animal") },
                    text = {
                        Column {
                            Button( onClick = {dropDownMenuSelectAnimal = true }) {
                                Text("$selectedAnimalName")
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            DropdownMenu(
                                expanded = dropDownMenuSelectAnimal,
                                onDismissRequest = { /* No-op */ }
                            ) {
                                animals.forEach { animal ->
                                    DropdownMenuItem(
                                        text = { Text(animal.name) },
                                        onClick = {
                                            selectedAnimalId = animal.id
                                            selectedAnimalName = animal.name
                                            dropDownMenuSelectAnimal = false
                                        }
                                    )
                                }
                            }
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                selectedAnimalId?.let {
                                    navController.navigate("add_activity/$it")
                                }
                                showDialogSelectAnimal = false
                            }
                        ) {
                            Text("Select")
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = { showDialogSelectAnimal = false }
                        ) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    }
}
