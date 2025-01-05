package fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import fr.uha.ensisa.lacassagne.ultimateherdassistant.database.DatabaseProvider
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.Stock
import fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel.ActiviteViewModel
import fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel.StockViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Dashboard") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            HeaderSection()
            Spacer(modifier = Modifier.height(16.dp))
            InfoCardsSection(navController)
            Spacer(modifier = Modifier.height(16.dp))
            ChartsSection()
        }
    }
}

@Composable
fun HeaderSection() {
    val currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))
    Text(
        text = currentDate,
        style = MaterialTheme.typography.headlineMedium
    )
}

@Composable
fun InfoCardsSection(navController: NavController, viewModel: StockViewModel = viewModel(), activiteViewModel: ActiviteViewModel = viewModel()) {
    val database = DatabaseProvider.getDatabase(navController.context)

    val animalCountState = produceState(initialValue = 0) {
        value = database.animalDao().getCount()
    }
    val animalCount = animalCountState.value

    val activities by activiteViewModel.getActivities().observeAsState(emptyList())
    val recentActivitiesCount = activities.filter {
        LocalDate.parse(it.date, DateTimeFormatter.ofPattern("dd-MM-yyyy")).isAfter(LocalDate.now().minusWeeks(2))
    }.size

    val stockList by viewModel.stockList.observeAsState(initial = emptyList())
    val criticalStock = stockList.filter { it.quantity < it.minQuantity }

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoCard(
                title = "Animals",
                value = animalCount.toString(),
                modifier = Modifier
                    .weight(1f)
                    .clickable { navController.navigate("animal_list") }
            )
            InfoCard(
                title = "Recent Activities",
                value = "$recentActivitiesCount recent",
                modifier = Modifier
                    .weight(1f)
                    .clickable { navController.navigate("activity") }
            )
        }
        InfoCard(
            title = "Critical Stock",
            value = if (criticalStock.isNotEmpty()) {
                if (criticalStock.size > 1) {
                    "Yes (${criticalStock.size} products on alert)"
                } else {
                    "Yes (${criticalStock[0].name} on alert)"
                }
            } else {
                "No critical stock"
            },
            isAlert = criticalStock.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navController.navigate("stock") }
        )
    }
}

@Composable
fun InfoCard(title: String, value: String, isAlert: Boolean = false, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(8.dp),
        colors = if (isAlert) CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.error) else CardDefaults.cardColors()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = value, style = MaterialTheme.typography.headlineSmall)
        }
    }
}

@Composable
fun ChartsSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ChartCard(title = "Activities Chart", modifier = Modifier.weight(1f))
        ChartCard(title = "Stocks Chart", modifier = Modifier.weight(1f))
    }
}

@Composable
fun ChartCard(title: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .height(200.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            // Placeholder for chart
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Chart Placeholder")
            }
        }
    }
}