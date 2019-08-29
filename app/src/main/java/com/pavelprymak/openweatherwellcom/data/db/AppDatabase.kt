package com.pavelprymak.openweatherwellcom.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import timber.log.Timber

@Database(entities = [WeatherEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {
        private val LOCK = Any()
        private val DATABASE_NAME = "db_podcasts"
        private var sInstance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (sInstance == null) {
                synchronized(LOCK) {
                    Timber.d("Creating new database instance")
                    sInstance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java, DATABASE_NAME
                    ).build()
                }
            }
            Timber.d("Getting the database instance")
            return sInstance!!
        }
    }
}