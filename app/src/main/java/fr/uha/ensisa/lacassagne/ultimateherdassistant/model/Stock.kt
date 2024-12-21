package fr.uha.ensisa.lacassagne.ultimateherdassistant.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stocks")
data class Stock (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var name: String = "", // Ex : "Fruits", "Légumes", "Viande", ...
    var quantity: Int = 0,
    var comment: String = ""
)