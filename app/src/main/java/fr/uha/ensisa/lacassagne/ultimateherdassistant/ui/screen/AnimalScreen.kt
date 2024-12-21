package fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier


import com.monzoo.app.presentation.viewmodel.AnimalViewModel

@Composable
fun AnimalScreen(modifier: Modifier = Modifier, viewModel: AnimalViewModel = viewModel()) {
    val animauxState = viewModel.animals.observeAsState(initial = emptyList())
    val animaux = animauxState.value

    LazyColumn(modifier = modifier) {
        items(animaux.size) { index ->
            val animal = animaux[index]
            Text("${animal.name} \n - ${animal.type} \n - ${animal.age} ans \n - ${animal.weight} \n - ${animal.height}")
        }
    }
}
