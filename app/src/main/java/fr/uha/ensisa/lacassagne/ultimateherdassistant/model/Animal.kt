package fr.uha.ensisa.lacassagne.ultimateherdassistant.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.AnimalType

@Entity(tableName = "animals")
data class Animal(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var name: String = "",
    var type: AnimalType,
    var age: Int = 0,
    var weight: Float = 0.0f,
    var height: Float = 0.0f
) {
    fun asLiveData(): LiveData<Animal> {
        return MutableLiveData(this)
    }
}