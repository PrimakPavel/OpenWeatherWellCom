package com.pavelprymak.openweatherwellcom.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weather")
    fun loadWeather(): LiveData<WeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weatherEntity: WeatherEntity)

    @Query("DELETE FROM weather")
    fun cleanTable()
}