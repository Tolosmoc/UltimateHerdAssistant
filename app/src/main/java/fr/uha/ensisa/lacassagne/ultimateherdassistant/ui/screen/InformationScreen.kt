package fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun InformationScreen(navController: NavController) {
    Scaffold { innerPadding ->
        Spacer(modifier = Modifier.padding(32.dp))
        Text(
            text = "Projet Android promo 2024-2025 - Guilhem LACASSGNE"
                    + "\n Ultimate Herd Assistant"
                    + "\n Cette application est une application de type gestion d'animaux et elle permet d'intéragir avec quatres tables:"
                    + "\n - Animal : les différents animaux à gérer"
                    + "\n - Stock : classés en deux catégories - Aliment et Medicament"
                    + "\n - Activité : liés à un animal et classés en trois catégories - Nourrir et donner un médicament (liés à un stock) OU une activité autre"
                    + "\n - Track : lié à un animal pour en faire son suivi (poid + température)"
                    + "\n "
                    + "\n Pour plus de détails - lien du github du projet : https://github.com/Tolosmoc/UltimateHerdAssistant.git"
                    + "\n "
                    + "\n "
                    + "\n ",
            modifier = Modifier.padding(innerPadding).padding(16.dp)
        )
    }
}