package fr.uha.ensisa.lacassagne.ultimateherdassistant.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animals")
class Animal {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String = ""
    var type: String = "" // TODO: enum
    var age: Int = 0
    var weight: Float = 0.0f
    var height: Float = 0.0f
}