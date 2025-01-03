package fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel

import android.app.Application
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

    fun getActivitiesByAnimalId(animalId: Int): LiveData<List<Activite>> {
        return activiteDao.getActivitiesByAnimalId(animalId).asLiveData()
    }

    fun addActivity(activity: Activite) {
        viewModelScope.launch {
            activiteDao.insert(activity)
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