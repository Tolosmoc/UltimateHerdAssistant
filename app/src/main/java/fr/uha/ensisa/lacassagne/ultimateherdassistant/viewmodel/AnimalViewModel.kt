package fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.Animal
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.AnimalType
import fr.uha.ensisa.lacassagne.ultimateherdassistant.database.dao.AnimalDao
import fr.uha.ensisa.lacassagne.ultimateherdassistant.database.dao.ActiviteDao
import fr.uha.ensisa.lacassagne.ultimateherdassistant.database.DatabaseProvider

import kotlinx.coroutines.launch

class AnimalViewModel(application: Application) : AndroidViewModel(application) {
    private val animalDao: AnimalDao = DatabaseProvider.getDatabase(application).animalDao()
    private val activiteDao: ActiviteDao = DatabaseProvider.getDatabase(application).activiteDao()
    val animals: LiveData<List<Animal>> = animalDao.getAll().asLiveData()


    fun addAnimal(animal: Animal) {
        viewModelScope.launch {
            animalDao.insert(animal)
        }
    }

    fun deleteAnimal(animal: Animal) {
        viewModelScope.launch {
            activiteDao.deleteActivitiesByAnimalId(animal.id)
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

    fun getAllAnimals(): LiveData<List<Animal>> {
        return animalDao.getAll().asLiveData()
    }

    fun updateWeight(animalId: Int, newWeight: Float) {
        viewModelScope.launch {
            animalDao.updateWeight(animalId, newWeight)
        }
    }

    fun getAnimalsByType(type: AnimalType): LiveData<List<Animal>> {
        return animalDao.getByType(type).asLiveData()
    }
}