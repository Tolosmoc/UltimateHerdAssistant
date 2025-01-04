package fr.uha.ensisa.lacassagne.ultimateherdassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.Stock
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.StockType

@Dao
interface StockDao {
    @Query("SELECT * FROM stock")
    fun getAll(): Flow<List<Stock>>

    @Query("SELECT * FROM stock WHERE type = :type")
    fun getByType(type: StockType): Flow<List<Stock>>

    @Query("SELECT * FROM stock WHERE id = :id")
    fun getById(id: Long): Flow<Stock>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stock: Stock)

    @Update
    suspend fun update(stock: Stock)

    @Delete
    suspend fun delete(stock: Stock)

    @Query("UPDATE stock SET quantity = quantity + :quantity WHERE id = :stockId")
    suspend fun addStock(stockId: Int, quantity: Int)

    @Query("UPDATE stock SET quantity = quantity - :quantity WHERE id = :stockId")
    suspend fun reduceStock(stockId: Int, quantity: Int)

}