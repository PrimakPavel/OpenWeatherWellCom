package com.pavelprymak.openweatherwellcom.data.db.repo

import androidx.lifecycle.LiveData
import com.pavelprymak.openweatherwellcom.data.db.WeatherEntity

interface DbRepo {
    fun getWeather(): LiveData<WeatherEntity>

    fun clearTableAndInsertWeather(weatherEntity: WeatherEntity)

    fun clearTable()
}