// StockScreen.kt
package fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.Stock
import fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel.StockViewModel

@Composable
fun StockScreen(navController: NavController, viewModel: StockViewModel = viewModel()) {
    val stockList by viewModel.stockList.observeAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var selectedStock by remember { mutableStateOf<Stock?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = { navController.navigate("add_stock") }) {
            Text("Add")
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(stockList) { stock ->
                Text(
                    text = "${stock.name} - ${stock.quantity} g",
                    color = if (stock.quantity < stock.minQuantity) MaterialTheme.colors.error else Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedStock = stock
                            showDialog = true
                        }
                        .padding(8.dp)
                )
            }
        }
    }

    if (showDialog && selectedStock != null) {
        StockDetailDialog(
            stock = selectedStock!!,
            onDismiss = { showDialog = false },
            onModify = { name, minQuantity ->
                viewModel.updateStock(selectedStock!!.copy(name = name, minQuantity = minQuantity))
                showDialog = false
            },
            onAddStock = { quantity ->
                viewModel.reStock(selectedStock!!.id, quantity)
                showDialog = false
            }
        )
    }
}

@Composable
fun StockDetailDialog(
    stock: Stock,
    onDismiss: () -> Unit,
    onModify: (String, Int) -> Unit,
    onAddStock: (Int) -> Unit
) {
    var name by remember { mutableStateOf(stock.name) }
    var minQuantity by remember { mutableStateOf(stock.minQuantity) }
    var addQuantity by remember { mutableStateOf(0) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Stock Details") },
        text = {
            Column {
                TextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
                TextField(
                    value = minQuantity.toString(),
                    onValueChange = { minQuantity = it.toIntOrNull() ?: 0 },
                    label = { Text("Min Quantity") }
                )
                TextField(
                    value = addQuantity.toString(),
                    onValueChange = { addQuantity = it.toIntOrNull() ?: 0 },
                    label = { Text("Add Quantity") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Current Quantity: ${stock.quantity} g")
            }
        },
        confirmButton = {
            Button(onClick = { onModify(name, minQuantity) }) {
                Text("Modify")
            }
        },
        dismissButton = {
            Button(onClick = { onAddStock(addQuantity) }) {
                Text("Add Stock")
            }
        }
    )
}