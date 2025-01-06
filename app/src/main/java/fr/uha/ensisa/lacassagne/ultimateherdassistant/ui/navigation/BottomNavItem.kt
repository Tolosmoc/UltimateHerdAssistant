package fr.uha.ensisa.lacassagne.ultimateherdassistant.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    // object Home : BottomNavItem("main_screen", Icons.Default.Home, "Home")
    object Home : BottomNavItem("dashboard", Icons.Default.Home, "Home")
    object Profile : BottomNavItem("animal_list", Icons.Default.Pets, "Profile")
    object Information : BottomNavItem("information", Icons.Default.Info, "Info")
}