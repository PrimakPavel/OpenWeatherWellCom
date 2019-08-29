package com.pavelprymak.openweatherwellcom.data.network.pojos.weaterByCityName

import com.squareup.moshi.Json


data class Coord(

	@Json(name="lon")
	val lon: Double? = null,

	@Json(name="lat")
	val lat: Double? = null
)