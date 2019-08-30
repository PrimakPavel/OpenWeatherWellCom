package com.pavelprymak.openweatherwellcom.utils

fun toCelsius(k: Double): String {
    return String.format("%.1f", (k - 273.15))
}