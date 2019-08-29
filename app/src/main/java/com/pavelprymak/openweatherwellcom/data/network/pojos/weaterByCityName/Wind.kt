package com.pavelprymak.openweatherwellcom.data.network.pojos.weaterByCityName

import com.squareup.moshi.Json


data class Wind(

	@Json(name="deg")
	val deg: Int? = null,

	@Json(name="speed")
	val speed: Double? = null
)