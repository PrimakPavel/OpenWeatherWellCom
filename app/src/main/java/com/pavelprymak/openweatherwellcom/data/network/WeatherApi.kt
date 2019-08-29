package com.pavelprymak.openweatherwellcom.data.network

import com.pavelprymak.openweatherwellcom.data.network.pojos.weaterByCityName.WeatherByCityResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET(Constants.PATH_WEATHER)
    fun weatherByCityName(
        @Query(Constants.QUERY_Q) cityName: String, @Query(Constants.QUERY_APPID) appId: String
    ): Call<WeatherByCityResponse>

    @GET(Constants.PATH_WEATHER)
    fun weatherByCityNameRx(
        @Query(Constants.QUERY_Q) cityName: String, @Query(Constants.QUERY_APPID) appId: String
    ): Single<WeatherByCityResponse>
}