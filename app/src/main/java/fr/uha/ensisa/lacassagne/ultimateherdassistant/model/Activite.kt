package fr.uha.ensisa.lacassagne.ultimateherdassistant.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "activities")
data class Activite (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var animal_id: Int = 0,
    var type: String = "", // "Food" OR "Medical" OR {activity.name}
    var description: String = "",
    var duration: Int = 0, // For {activity.name}
    var date: String = SimpleDateFormat("dd-MM-YYYY", Locale.getDefault()).format(Date()), // Default to today for "Food" OR "Medical"
    var comment: String = "" // for "Food" OR "Medical"
)