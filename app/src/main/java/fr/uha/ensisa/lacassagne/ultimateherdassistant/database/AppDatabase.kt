package fr.uha.ensisa.lacassagne.ultimateherdassistant.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import fr.uha.ensisa.lacassagne.ultimateherdassistant.model.*

import fr.uha.ensisa.lacassagne.ultimateherdassistant.database.dao.*


@Database(
    entities = [Animal::class, Stock::class, Nourissage::class, Tracker::class, Activite::class],
    version = 1, // !!! Change this number when you change the database schema OR delete the app !!!
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun animalDao(): AnimalDao
    abstract fun stockDao(): StockDao
    abstract fun nourissageDao(): NourissageDao
    abstract fun trackerDao(): TrackerDao
    abstract fun activiteDao(): ActiviteDao
}