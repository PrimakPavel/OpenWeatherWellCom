package com.pavelprymak.openweatherwellcom.utils

import com.pavelprymak.openweatherwellcom.data.db.WeatherEntity
import com.pavelprymak.openweatherwellcom.data.network.pojos.weaterByCityName.WeatherByCityResponse

fun WeatherByCityResponse.convertToWeatherEntity(): WeatherEntity {
    return WeatherEntity(
        cityName = name,
        temp = main?.temp,
        tempMin = main?.tempMin,
        tempMax = main?.tempMax,
        humidity = main?.humidity,
        pressure = main?.pressure,
        windDeg = wind?.deg,
        windSpeed = wind?.speed
    )
}