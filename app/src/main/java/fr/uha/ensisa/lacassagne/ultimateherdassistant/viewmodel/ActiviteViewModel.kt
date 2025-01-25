package fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.Activite
import fr.uha.ensisa.lacassagne.ultimateherdassistant.database.dao.ActiviteDao
import fr.uha.ensisa.lacassagne.ultimateherdassistant.database.DatabaseProvider
import kotlinx.coroutines.launch

class ActiviteViewModel(application: Application) : AndroidViewModel(application) {
    private val activiteDao: ActiviteDao = DatabaseProvider.getDatabase(application).activiteDao()

    fun getActivities(): LiveData<List<Activite>> {
        return activiteDao.getAll().asLiveData()
    }

    fun getActivitiesByAnimalId(animalId: Int): LiveData<List<Activite>> {
        return activiteDao.getActivitiesByAnimalId(animalId).asLiveData()
    }

    fun getActivitiesByFilter(filter: String): LiveData<List<Activite>> {
        return when (filter) {
            "Food" -> activiteDao.getFoodActivities().asLiveData()
            "Medical" -> activiteDao.getMedicalActivities().asLiveData()
            "Others" -> activiteDao.getOtherActivities().asLiveData()
            else -> activiteDao.getAll().asLiveData()
        }
    }

    fun getActivitiesByAnimalByFilter(animalId: Int, filter: String): LiveData<List<Activite>> {
        return when (filter) {
            "Food" -> activiteDao.getFoodActivitiesByAnimalId(animalId).asLiveData()
            "Medical" -> activiteDao.getMedicalActivitiesByAnimalId(animalId).asLiveData()
            "Others" -> activiteDao.getOtherActivitiesByAnimalId(animalId).asLiveData()
            else -> activiteDao.getActivitiesByAnimalId(animalId).asLiveData()
        }
    }

    fun addActivity(activity: Activite) {
        viewModelScope.launch {
            try {
                activiteDao.insert(activity)
            } catch (e: SQLiteConstraintException) {
                Log.e("ActiviteViewModel", "Foreign key constraint failed: ${e.message}")
            }
        }
    }

    fun updateActivity(activity: Activite) {
        viewModelScope.launch {
            activiteDao.update(activity)
        }
    }

    fun deleteActivity(activity: Activite) {
        viewModelScope.launch {
            activiteDao.delete(activity)
        }
    }
}