package fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.uha.ensisa.lacassagne.ultimateherdassistant.database.DatabaseProvider
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
fun InfoCardsSection(navController: NavController) {
    val database = DatabaseProvider.getDatabase(navController.context)
    val animalCountState = produceState(initialValue = 0) {
        value = database.animalDao().getCount()
    }
    val animalCount = animalCountState.value

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
            InfoCard(title = "Recent Activities", value = "15 recent", modifier = Modifier.weight(1f))
        }
        InfoCard(title = "Critical Stock", value = "Yes (2 products on alert)", isAlert = true, modifier = Modifier.fillMaxWidth())
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