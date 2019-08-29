package com.pavelprymak.openweatherwellcom.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
class WeatherEntity(
    @ColumnInfo(name = "city")
    var cityName: String? = null,

    @ColumnInfo(name = "temp")
    val temp: Double? = null,

    @ColumnInfo(name = "temp_min")
    val tempMin: Double? = null,
    @ColumnInfo(name = "temp_max")
    val tempMax: Double? = null,

    @ColumnInfo(name = "humidity")
    val humidity: Int? = null,

    @ColumnInfo(name = "pressure")
    val pressure: Int? = null,

    @ColumnInfo(name = "wind_deg")
    val windDeg: Int? = null,

    @ColumnInfo(name = "wind_speed")
    val windSpeed: Double? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    override fun toString(): String {
        return "WeatherEntity(cityName=$cityName, temp=$temp, tempMin=$tempMin, tempMax=$tempMax, humidity=$humidity, pressure=$pressure, windDeg=$windDeg, windSpeed=$windSpeed)"
    }

}


