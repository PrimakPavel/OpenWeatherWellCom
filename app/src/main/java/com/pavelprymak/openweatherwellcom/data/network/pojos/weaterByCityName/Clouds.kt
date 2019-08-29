package com.pavelprymak.openweatherwellcom.data.network.pojos.weaterByCityName

import com.squareup.moshi.Json

data class Clouds(

    @Json(name = "all")
    val all: Int? = null
)