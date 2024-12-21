package fr.uha.ensisa.lacassagne.ultimateherdassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.Suivi

@Dao
interface SuiviDao {
    @Query("SELECT * FROM monitoring")
    fun getAll(): Flow<List<Suivi>>

    @Query("SELECT * FROM monitoring WHERE id = :id")
    fun getById(id: Long): Flow<Suivi>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(monitoring: Suivi)

    @Update
    suspend fun update(monitoring: Suivi)

    @Delete
    suspend fun delete(monitoring: Suivi)
}