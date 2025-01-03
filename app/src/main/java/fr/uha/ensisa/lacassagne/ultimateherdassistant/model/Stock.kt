package fr.uha.ensisa.lacassagne.ultimateherdassistant.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.StockType

@Entity(tableName = "stock")
data class Stock (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var name: String, // Ex : "Fruits", "Légumes", "Viande", "Medicament", ...
    var quantity: Int,
    var minQuantity: Int,
    var type: StockType
)