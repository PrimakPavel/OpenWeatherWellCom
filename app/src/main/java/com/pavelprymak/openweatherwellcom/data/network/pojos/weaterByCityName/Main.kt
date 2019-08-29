package com.pavelprymak.openweatherwellcom.data.network.pojos.weaterByCityName

import com.squareup.moshi.Json

data class Main(

	@Json(name="temp")
	val temp: Double? = null,

	@Json(name="temp_min")
	val tempMin: Double? = null,

	@Json(name="humidity")
	val humidity: Int? = null,

	@Json(name="pressure")
	val pressure: Int? = null,

	@Json(name="temp_max")
	val tempMax: Double? = null
)