package fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import fr.uha.ensisa.lacassagne.ultimateherdassistant.database.DatabaseProvider
import fr.uha.ensisa.lacassagne.ultimateherdassistant.database.dao.TrackerDao
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.Tracker
import kotlinx.coroutines.launch

class TrackerViewModel (application: Application) : AndroidViewModel(application) {
    private val trackerDao: TrackerDao = DatabaseProvider.getDatabase(application).trackerDao()

    fun getTrackersByAnimalId(animalId: Int): LiveData<List<Tracker>> {
        return trackerDao.getTrackersByAnimalId(animalId)
    }

    fun addTracker(tracker: Tracker) {
        viewModelScope.launch {
            trackerDao.insert(tracker)
        }
    }
}