package fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.Activite
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.Date
import java.util.Locale

import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.Animal
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.AnimalType
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.Stock
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.StockType
import fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel.AnimalViewModel
import fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel.TrackerViewModel
import fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel.ActiviteViewModel
import fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel.StockViewModel
import java.time.format.DateTimeFormatter


@Composable
fun AnimalScreen(
    animal: Animal,
    viewModel: AnimalViewModel = viewModel(),
    activiteViewModel: ActiviteViewModel = viewModel(),
    stockViewModel: StockViewModel = viewModel(),
    navController: NavController
) {
    var showDialog by remember { mutableStateOf(false) }
    var showFeedDialog by remember { mutableStateOf(false) }
    var showMedicineDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            HeaderSection(animal = animal)
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Button(onClick = { showDialog = true }) {
                    Text("Show Details")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { showFeedDialog = true }) {
                    Text("Feed")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { showMedicineDialog = true }) {
                    Text("Give Medicine")
                }
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
            if (showFeedDialog) {
                ActStockDialog(
                    animalId = animal.id,
                    stockType = StockType.FOOD,
                    onDismiss = { showFeedDialog = false },
                    activiteViewModel = activiteViewModel,
                    stockViewModel = stockViewModel
                )
            }
            if (showMedicineDialog) {
                ActStockDialog(
                    animalId = animal.id,
                    stockType = StockType.MEDICINE,
                    onDismiss = { showMedicineDialog = false },
                    activiteViewModel = activiteViewModel,
                    stockViewModel = stockViewModel
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            RecentTrackersSection(animalId = animal.id, viewModel = viewModel(), navController = navController)
            Spacer(modifier = Modifier.height(16.dp))
            ActivitySection(animalId = animal.id, viewModel = viewModel(), navController = navController)
        }

        var showImageDialog by remember { mutableStateOf(false) }

        animal.picture?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .clickable { showImageDialog = true }
            )
        }

        if (showImageDialog) {
            Dialog(onDismissRequest = { showImageDialog = false }) {
                Box(modifier = Modifier
                    .wrapContentSize()
                    .background(Color.Black.copy(alpha = 0.8f))
                    .clickable { showImageDialog = false }
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(animal.picture),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun HeaderSection(animal: Animal) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val formattedDate = animal.birthDate?.let { dateFormat.format(it) } ?: "${animal.age} years"

    val emoji = when (animal.type) {
        AnimalType.MAMMAL -> "🐾"
        AnimalType.BIRD -> "🐦"
        AnimalType.REPTILE -> "\uD83E\uDD8E"
        AnimalType.FISH -> "🐟"
        AnimalType.AMPHIBIAN -> "🐸"
        else -> "🐾"
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        /* TODO: Add image
        Image(

            painter = painterResource(id = R.drawable.animal_photo_placeholder),
            contentDescription = "Animal Photo",
            modifier = Modifier.size(128.dp)
        )
        */
        Text(text = "$emoji ${animal.name} ($formattedDate)", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Species: ${animal.species}", style = MaterialTheme.typography.bodyMedium)
        // Text(text = "Picture: ${animal.picture}", style = MaterialTheme.typography.bodyMedium)
        /*
        animal.picture?.let {
            Image(painter = rememberImagePainter(it), contentDescription = null)
        }
        */
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

@Composable
fun ActStockDialog(  // Activity using Stock
    animalId: Int,
    stockType: StockType,
    onDismiss: () -> Unit,
    activiteViewModel: ActiviteViewModel,
    stockViewModel: StockViewModel
) {
    var selectedStock by remember { mutableStateOf<Stock?>(null) }
    var expandStock by remember { mutableStateOf(false) }
    var quantity by remember { mutableStateOf("") }
    val stocks by stockViewModel.getStockByType(stockType).observeAsState(emptyList())

    if (stocks.isEmpty()) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("No Stock Available") },
            text = { Text("There is currently no stock available for ${stockType.displayName}.") },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text("OK")
                }
            }
        )
    } else {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Feed Animal") },
            text = {
                Column {
                    Button(onClick = { expandStock = true }) {
                        Text(selectedStock?.name ?: "Select Type")
                    }
                    DropdownMenu(
                        expanded = expandStock,
                        onDismissRequest = { /* Handle dismiss */ }
                    ) {
                        stocks.forEach { stock ->
                            DropdownMenuItem(
                                text = { Text(stock.name) },
                                onClick = {
                                    selectedStock = stock
                                    expandStock = false
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = quantity,
                        onValueChange = { quantity = it },
                        label = { Text("Quantity") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    selectedStock?.let { stock ->
                        val chosenQuantity = quantity.toIntOrNull() ?: 0
                        if (chosenQuantity <= stock.quantity) {
                            activiteViewModel.addActivity(
                                Activite(
                                    animal_id = animalId,
                                    type = stockType.displayName,
                                    stock_id = stock.id
                                )
                            )
                            stockViewModel.reduceStock(stock.id, chosenQuantity)
                            onDismiss()
                        } else {
                            // Handle insufficient stock quantity
                        }
                    }
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }
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

@Composable
fun ActivitySection(animalId: Int, viewModel: ActiviteViewModel, navController: NavController) {
    var expanded by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf("All") }
    val activities by viewModel.getActivitiesByFilter(selectedFilter).observeAsState(emptyList())
    val sortedActivities = activities.sortedByDescending {
        LocalDate.parse(it.date, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
    }

    // Detail
    var showActivityDetailDialog by remember { mutableStateOf(false) }
    var selectedActivity by remember { mutableStateOf<Activite?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Activities", style = MaterialTheme.typography.headlineMedium.copy(color = Color.Black))
            Spacer(modifier = Modifier.width(10.dp))
            Button(onClick = { navController.navigate("add_activity/${animalId}") }) {
                Text("Add Activity")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Box {
                Button(onClick = { expanded = true }) {
                    Text("No Filter")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("No Filter") },
                        onClick = {
                            selectedFilter = "All"
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Food") },
                        onClick = {
                            selectedFilter = "Food"
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Medical") },
                        onClick = {
                            selectedFilter = "Medical"
                            expanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Other") },
                        onClick = {
                            selectedFilter = "Other"
                            expanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(sortedActivities) { activity ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            selectedActivity = activity
                            showActivityDetailDialog = true
                        }
                ) {
                    Text(text = activity.type, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = activity.date, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }

    if (showActivityDetailDialog && selectedActivity != null) {
        ActivityDetailDialog(
            activity = selectedActivity!!,
            onDismiss = { showActivityDetailDialog = false }
        )
    }
}

@Composable
fun ActivityDetailDialog(activity: Activite, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Activity Details") },
        text = {
            Column {
                Text("Type: ${activity.type}")
                Text("Date: ${activity.date}")
                if (activity.description.isNotEmpty()) {
                    Text("Description: ${activity.description}")
                }
                if (activity.duration != 0) {
                    Text("Duration: ${activity.duration} minutes")
                }
                if (activity.comment.isNotEmpty()) {
                    Text("Comment: ${activity.comment}")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
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