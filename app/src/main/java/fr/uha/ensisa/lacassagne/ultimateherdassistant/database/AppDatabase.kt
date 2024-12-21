package fr.uha.ensisa.lacassagne.ultimateherdassistant.database

import androidx.room.Database
import androidx.room.RoomDatabase

import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.*

import fr.uha.ensisa.lacassagne.ultimateherdassistant.database.dao.*


@Database(
    entities = [Animal::class, Stock::class, Nourissage::class, Suivi::class, Activite::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun animalDao(): AnimalDao
    abstract fun stockDao(): StockDao
    abstract fun nourissageDao(): NourissageDao
    abstract fun suiviDao(): SuiviDao
    abstract fun activiteDao(): ActiviteDao
}