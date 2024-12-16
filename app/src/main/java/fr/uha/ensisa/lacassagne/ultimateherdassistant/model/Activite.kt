package fr.uha.ensisa.lacassagne.ultimateherdassistant.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activities")
class Activite {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String = ""
    var type: String = "" // TODO: enum
    var duration: Int = 0
    var date: String = ""
    var comment: String = ""
}