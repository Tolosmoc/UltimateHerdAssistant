package fr.uha.ensisa.lacassagne.ultimateherdassistant.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "ultimate_herd_assistant_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}