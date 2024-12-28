package fr.uha.ensisa.lacassagne.ultimateherdassistant.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "tracker")
data class Tracker(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val animalId: Int,
    val date: Date,
    val temperature: Float,
    val weight: Float
)