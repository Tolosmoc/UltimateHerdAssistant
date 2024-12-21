package fr.uha.ensisa.lacassagne.ultimateherdassistant.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feeding")
data class Nourissage (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val animalId: Int, // Clé étrangère vers Animal
    val stockId: Int, // Clé étrangère vers Stock
    var quantity: Int = 0,
    var date: String = "",
    var comment: String = ""
)