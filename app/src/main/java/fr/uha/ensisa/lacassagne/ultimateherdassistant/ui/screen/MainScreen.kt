package fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.navigation.BottomNavigationBar

@Composable
fun MainScreen(navController: NavController) {
    Scaffold(
        /*
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
        */
    ) { innerPadding ->
        Spacer(modifier = Modifier.padding(32.dp))
        Text(
            text = "Les choses à faire :"
                + "\n - Certains input de type Int n'ont pas été corrigés"
                + "\n - Les graph du dashboard ne sont pas encore implémentés"
                + "\n - Il manque des modifications sur les animaux (ça peut créer des incohérences)"
                + "\n - Choix kg/g et m/cm (puis *1000 dans la base) lors de l'ajout d'un animal"
                + "\n - Mettre plus en évidence pour supprimer un animal"
                + "\n - /"
                + "\n - /"
                + "\n - Avoir un filtre dans la liste des animaux et dans la liste des filtres ( et celle des stocks ?)"
                + "\n - Theme ? (darkTheme)"
                + "\n - Uniformiser couleur des input (ex: addTrack != addAnimal)"
                + "\n - Et bien d'autres choses je suppose...",
            modifier = Modifier.padding(innerPadding).padding(16.dp)
        )
    }
}