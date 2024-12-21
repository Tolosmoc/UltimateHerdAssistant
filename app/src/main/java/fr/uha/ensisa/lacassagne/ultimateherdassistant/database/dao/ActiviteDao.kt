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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(activity: Activite)

    @Update
    suspend fun update(activity: Activite)

    @Delete
    suspend fun delete(activity: Activite)
}