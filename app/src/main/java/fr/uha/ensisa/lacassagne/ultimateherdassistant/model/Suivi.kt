package fr.uha.ensisa.lacassagne.ultimateherdassistant.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monitoring")
data class Suivi (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val animalId: Int, // Clé étrangère vers Animal
    val activityId: Int, // Clé étrangère vers Activite
    var date: String = "",
    var temperature: Float = 0.0f,
    var weight: Float = 0.0f,
    var comment: String = "",
    )