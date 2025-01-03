package fr.uha.ensisa.lacassagne.ultimateherdassistant.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import fr.uha.ensisa.lacassagne.ultimateherdassistant.database.DatabaseProvider
import fr.uha.ensisa.lacassagne.ultimateherdassistant.database.dao.StockDao
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.Stock
import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.StockType


class StockViewModel(application: Application) : AndroidViewModel(application) {
    private val stockDao: StockDao = DatabaseProvider.getDatabase(application).stockDao()
    val stockList: LiveData<List<Stock>> = stockDao.getAll().asLiveData()

    fun addStock(name: String, quantity: Int, minQuantity: Int, type: StockType) {
        viewModelScope.launch {
            val stock = Stock(name = name, quantity = quantity, minQuantity = minQuantity, type = type)
            stockDao.insert(stock)
        }
    }

    fun getStockByType(type: StockType): LiveData<List<Stock>> {
        return stockDao.getByType(type.displayName).asLiveData()
    }

    fun updateStock(stock: Stock) {
        viewModelScope.launch {
            stockDao.update(stock)
        }
    }

    fun reStock(stockId: Int, quantity: Int) {
        viewModelScope.launch {
            stockDao.addStock(stockId, quantity)
        }
    }

    fun reduceStock(stockId: Int, quantity: Int) { // TODO
        viewModelScope.launch {
            stockDao.reduceStock(stockId, quantity)
        }
    }

    fun deleteStock(stock: Stock) { // TODO
        viewModelScope.launch {
            stockDao.delete(stock)
        }
    }
}