package fr.uha.ensisa.lacassagne.ultimateherdassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.Animal

@Dao
interface AnimalDao {
    @Query("SELECT * FROM animals")
    fun getAll(): Flow<List<Animal>>

    @Query("SELECT * FROM animals WHERE id = :id")
    fun getById(id: Int): Animal

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(animal: Animal)

    @Update
    suspend fun update(animal: Animal)

    @Delete
    suspend fun delete(animals: Animal)

    @Query("SELECT COUNT(*) FROM animals")
    suspend fun getCount(): Int
}