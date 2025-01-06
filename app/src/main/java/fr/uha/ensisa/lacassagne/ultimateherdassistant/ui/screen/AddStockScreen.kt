package fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.StockType
import fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel.StockViewModel

@Composable
fun AddStockScreen(navController: NavController, viewModel: StockViewModel = viewModel()) {
    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf(TextFieldValue()) }
    var minQuantity by remember { mutableStateOf(TextFieldValue()) }
    var type by remember { mutableStateOf(StockType.FOOD) }
    var dropdownExpanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name", style = TextStyle(color = Color.Blue)) },
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Quantity", style = TextStyle(color = Color.Blue)) },
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = minQuantity,
            onValueChange = { minQuantity = it },
            label = { Text("Min Quantity", style = TextStyle(color = Color.Blue)) },
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Box {
            androidx.compose.material3.Button(onClick = { dropdownExpanded = true }) {
                androidx.compose.material3.Text(
                    text = type?.displayName?.let { "$it ⌄" } ?: "Select Type ⌄"
                )
            }
            DropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false },
            ) {
                StockType.values().forEach { stockType ->
                    DropdownMenuItem(onClick = {
                        type = stockType
                        dropdownExpanded = false
                    }) {
                        Text(stockType.name)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.addStock(name, quantity.text.toInt(), minQuantity.text.toInt(), type)
                navController.popBackStack()
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add Stock")
        }
    }
}