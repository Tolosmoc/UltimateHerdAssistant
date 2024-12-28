package fr.uha.ensisa.lacassagne.ultimateherdassistant.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import java.util.Date
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.Tracker

@Dao
interface TrackerDao {
    @Insert
    suspend fun insert(tracker: Tracker)

    @Query("SELECT * FROM tracker WHERE animalId = :animalId")
    fun getTrackersByAnimalId(animalId: Int): LiveData<List<Tracker>>

    @Query("SELECT * FROM tracker WHERE date = :date")
    suspend fun getTrackersByDate(date: Date): List<Tracker>
}