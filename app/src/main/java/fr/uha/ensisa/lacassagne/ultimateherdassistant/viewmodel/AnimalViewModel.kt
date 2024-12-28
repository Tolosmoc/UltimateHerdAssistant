package fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.Animal
import fr.uha.ensisa.lacassagne.ultimateherdassistant.database.dao.AnimalDao
import fr.uha.ensisa.lacassagne.ultimateherdassistant.database.DatabaseProvider

import kotlinx.coroutines.launch

class AnimalViewModel(application: Application) : AndroidViewModel(application) {
    private val animalDao: AnimalDao = DatabaseProvider.getDatabase(application).animalDao()
    val animals: LiveData<List<Animal>> = animalDao.getAll().asLiveData()

    fun addAnimal(animal: Animal) {
        viewModelScope.launch {
            animalDao.insert(animal)
        }
    }

    fun deleteAnimal(animal: Animal) {
        viewModelScope.launch {
            animalDao.delete(animal)
        }
    }

    fun updateAnimal(animal: Animal) {
        viewModelScope.launch {
            animalDao.update(animal)
        }
    }

    fun getAnimalById(id: Int): LiveData<Animal> {
        return animalDao.getById(id).asLiveData()
    }
}