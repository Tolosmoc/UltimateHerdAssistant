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
                + "\n 8- Theme ? (darkTheme)"
                + "\n 6- Uniformiser couleur des input (ex: addTrack != addAnimal)"
                + "\n 2- Ajouter la gestion d'erreurs à chaque input (AddActicityScreen, ModifyScreen,..."
                + "\n /- Certains input de type Int n'ont pas été corrigés (AddStock et ModifyStock (?))"
                + "\n ?- Choix kg/g et m/cm (puis *1000 dans la base) lors de l'ajout d'un animal"
                + "\n 3- Il manque des modifications sur les animaux (peut créer des incohérences)"
                + "\n De même, empêcher modification poid/taille => à faire depuis 'Add Track'"
                + "\n ?- Mettre plus en évidence pour supprimer un animal"
                + "\n 7- Cacher les graph de animalScreen comme un menu déroulant ?"
                + "\n 4- Les graph du dashboard ne sont pas encore implémentés"
                + "\n 1- Activities dans l'odre croissant de date (depuis dashboard -> list activité"
                + "\n 5- Avoir un filtre dans la liste des animaux (par type) et dans la liste des activités (par animaux) ( et celle des stocks ?)"
                + "\n - Filtre 'Other' dans la liste des animaux PB ! => All"
                + "\n - Menu dérouland sur la gauche (trois _)"+ "\n delete activity if animal deleted"
                + "\n - Add Tab to seperate track and activities below header in animalScreen"
                + "\n - A tester sur 33 ou !34!"
                + "\n - Et bien d'autres choses je suppose..."
                + "\n THEH CHANGE THIS SCREEN TO A 'Welcome' AND INDICATION ABOUT BottomNavigationBar",
            modifier = Modifier.padding(innerPadding).padding(16.dp)
        )
    }
}