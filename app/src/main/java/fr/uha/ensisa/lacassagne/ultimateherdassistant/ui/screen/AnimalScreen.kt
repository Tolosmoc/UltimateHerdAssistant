package fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import java.time.LocalDate
import java.time.temporal.ChronoUnit

import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.Animal
import fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel.AnimalViewModel
import fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel.TrackerViewModel
import java.time.ZoneId
import java.util.Date


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
        Spacer(modifier = Modifier.height(16.dp))
        RecentTrackersSection(animalId = animal.id, viewModel = viewModel(), navController = navController)
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

// Extension function for readability
fun Date.toLocalDate(): LocalDate = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

@Composable
fun RecentTrackersSection(animalId: Int, viewModel: TrackerViewModel, navController: NavController) {
    val trackers by viewModel.getTrackersByAnimalId(animalId).observeAsState(emptyList())

    val recentTrackers = trackers
        .filter { it.date.toLocalDate().isAfter(LocalDate.now().minusWeeks(2)) }
        .sortedBy { it.date.toLocalDate() }

    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.LightGray)
        .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Recent Trackers", style = MaterialTheme.typography.headlineMedium.copy(color = Color.Black))
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { navController.navigate("add_track/${animalId}") }) {
                    Text("Add track")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                TrackerGraph(
                    data = recentTrackers.map { it.date.toLocalDate() to it.temperature },
                    title = "Temp / Date",
                    color = Color.Red,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                TrackerGraph(
                    data = recentTrackers.map { it.date.toLocalDate() to it.weight },
                    title = "Weight / Date",
                    color = Color.Blue,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun TrackerGraph(data: List<Pair<LocalDate, Float>>, title: String, color: Color, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = title, style = MaterialTheme.typography.headlineSmall.copy(color = Color.Black))
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.White)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val maxX = size.width
                val maxY = size.height
                val maxDataX = data.maxOfOrNull { it.first.toEpochDay() } ?: 0L
                val minDataX = data.minOfOrNull { it.first.toEpochDay() } ?: 0L
                val maxDataY = data.maxOfOrNull { it.second } ?: 0f
                val minDataY = data.minOfOrNull { it.second } ?: 0f

                // Draw grid
                val gridSpacing = 20.dp.toPx()
                for (x in 0..maxX.toInt() step gridSpacing.toInt()) {
                    drawLine(
                        color = Color.Gray,
                        start = Offset(x.toFloat(), 0f),
                        end = Offset(x.toFloat(), maxY),
                        strokeWidth = 1.dp.toPx()
                    )
                }
                for (y in 0..maxY.toInt() step gridSpacing.toInt()) {
                    drawLine(
                        color = Color.Gray,
                        start = Offset(0f, y.toFloat()),
                        end = Offset(maxX, y.toFloat()),
                        strokeWidth = 1.dp.toPx()
                    )
                }

                // Draw axes
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, maxY),
                    end = Offset(maxX, maxY),
                    strokeWidth = 2.dp.toPx()
                )
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, 0f),
                    end = Offset(0f, maxY),
                    strokeWidth = 2.dp.toPx()
                )

                // Draw x-axis labels
                val xLabelCount = 5
                val xStep = (maxDataX - minDataX) / xLabelCount
                for (i in 0..xLabelCount) {
                    val xValue = minDataX + i * xStep
                    val x = ((xValue - minDataX).toFloat() / (maxDataX - minDataX)) * maxX
                    drawContext.canvas.nativeCanvas.drawText(
                        LocalDate.ofEpochDay(xValue).toString(),
                        x,
                        maxY + 20f,
                        android.graphics.Paint().apply {
                            textSize = 30f
                        }
                    )
                }

                // Draw y-axis labels
                val yLabelCount = 5
                val yStep = (maxDataY - minDataY) / yLabelCount
                for (i in 0..yLabelCount) {
                    val yValue = minDataY + i * yStep
                    val y = maxY - ((yValue - minDataY) / (maxDataY - minDataY)) * maxY
                    drawContext.canvas.nativeCanvas.drawText(
                        yValue.toString(),
                        -50f,
                        y,
                        android.graphics.Paint().apply {
                            textSize = 30f
                        }
                    )
                }

                // Draw graph
                val path = Path().apply {
                    data.forEachIndexed { index, pair ->
                        val x = ((pair.first.toEpochDay() - minDataX).toFloat() / (maxDataX - minDataX)) * maxX
                        val y = maxY - ((pair.second - minDataY) / (maxDataY - minDataY)) * maxY
                        if (index == 0) moveTo(x, y) else lineTo(x, y)
                    }
                }

                drawPath(path, color = color, style = Stroke(width = 4.dp.toPx()))
            }
        }
    }
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