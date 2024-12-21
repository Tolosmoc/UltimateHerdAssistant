package fr.uha.ensisa.lacassagne.ultimateherdassistant.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activities")
data class Activite (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var type: String = "", // TODO: enum _ ex : "visite médicale", "sortie", ...
    var description: String = "",
    var duration: Int = 0,
    var date: String = "",
    var comment: String = ""
)