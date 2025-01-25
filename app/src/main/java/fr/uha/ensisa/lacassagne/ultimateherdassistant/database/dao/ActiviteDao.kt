package fr.uha.ensisa.lacassagne.ultimateherdassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.Activite

@Dao
interface ActiviteDao {
    @Query("SELECT * FROM activities")
    fun getAll(): Flow<List<Activite>>

    @Query("SELECT * FROM activities WHERE id = :id")
    suspend fun getById(id: Int): Activite

    // Animal
    @Query("SELECT * FROM activities WHERE animal_id = :animalId")
    fun getActivitiesByAnimalId(animalId: Int): Flow<List<Activite>>

    // Type
    @Query("SELECT * FROM activities WHERE type = 'Food'")
    fun getFoodActivities(): Flow<List<Activite>>

    @Query("SELECT * FROM activities WHERE type = 'Medical'")
    fun getMedicalActivities(): Flow<List<Activite>>

    @Query("SELECT * FROM activities WHERE type != 'Food' AND type != 'Medical'")
    fun getOtherActivities(): Flow<List<Activite>>

    // Animal & Type
    @Query("SELECT * FROM activities WHERE animal_id = :animalId AND type = 'Food'")
    fun getFoodActivitiesByAnimalId(animalId: Int): Flow<List<Activite>>

    @Query("SELECT * FROM activities WHERE animal_id = :animalId AND type = 'Medical'")
    fun getMedicalActivitiesByAnimalId(animalId: Int): Flow<List<Activite>>

    @Query("SELECT * FROM activities WHERE animal_id = :animalId AND type != 'Food' AND type != 'Medical'")
    fun getOtherActivitiesByAnimalId(animalId: Int): Flow<List<Activite>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(activity: Activite)

    @Update
    suspend fun update(activity: Activite)

    @Delete
    suspend fun delete(activity: Activite)

    @Query("DELETE FROM activities WHERE animal_id = :animalId")
    suspend fun deleteActivitiesByAnimalId(animalId: Int)
}