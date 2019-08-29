package com.pavelprymak.openweatherwellcom.data.network.repo

import com.pavelprymak.openweatherwellcom.data.network.pojos.weaterByCityName.WeatherByCityResponse
import io.reactivex.Single

interface NetworkRepo {
    fun getWeatherByCity(cityName: String): Single<WeatherByCityResponse>
}