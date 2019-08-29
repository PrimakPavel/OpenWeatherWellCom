package com.pavelprymak.openweatherwellcom.data.db.repo

import androidx.lifecycle.LiveData
import com.pavelprymak.openweatherwellcom.data.db.AppDatabase
import com.pavelprymak.openweatherwellcom.data.db.AppExecutors
import com.pavelprymak.openweatherwellcom.data.db.WeatherEntity
import java.util.concurrent.Executor

class DbRepoImpl(db: AppDatabase, executors: AppExecutors) : DbRepo {
    override fun clearTable() {
        diskIO.execute {
            mDb.weatherDao().cleanTable()
        }
    }

    private val mDb: AppDatabase = db
    private val diskIO: Executor = executors.diskIO()

    override fun getWeather(): LiveData<WeatherEntity> {
        return mDb.weatherDao().loadWeather()
    }

    override fun clearTableAndInsertWeather(weatherEntity: WeatherEntity) {
        diskIO.execute {
            mDb.weatherDao().cleanTable()
            mDb.weatherDao().insertWeather(weatherEntity)
        }
    }
}