package com.pavelprymak.openweatherwellcom.data.network.repo

import com.pavelprymak.openweatherwellcom.BuildConfig.API_KEY
import com.pavelprymak.openweatherwellcom.data.network.WeatherApi
import com.pavelprymak.openweatherwellcom.data.network.pojos.weaterByCityName.WeatherByCityResponse
import io.reactivex.Single

class NetworkRepoImpl(private val mApi: WeatherApi) : NetworkRepo {
    override fun getWeatherByCity(cityName: String): Single<WeatherByCityResponse> {
        return mApi.weatherByCityNameRx(cityName, API_KEY)
    }
}