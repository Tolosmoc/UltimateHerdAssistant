package fr.uha.ensisa.lacassagne.ultimateherdassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.Nourissage

@Dao
interface NourissageDao {
    @Query("SELECT * FROM feeding")
    fun getAll(): Flow<List<Nourissage>>

    @Query("SELECT * FROM feeding WHERE id = :id")
    fun getById(id: Long): Flow<Nourissage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(feeding: Nourissage)

    @Update
    suspend fun update(feeding: Nourissage)

    @Delete
    suspend fun delete(feeding: Nourissage)
}