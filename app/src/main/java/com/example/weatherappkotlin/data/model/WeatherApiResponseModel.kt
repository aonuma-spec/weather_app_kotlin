package com.example.weatherappkotlin.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// 全体のレスポンス
@Serializable
data class WeatherApiResponseModel(
    val name: String,
    val weather: List<WeatherInfo>,
    val main: MainInfo
)

// weather
@Serializable
data class WeatherInfo(
    val main: String,
    val descripttion: String
)

// main
@Serializable
data class MainInfo(
    val temp: Double,
    val humidity: Int,
    @SerialName("temp_min") val tempMin: Double,
    @SerialName("temp-max") val tempMax: Double
)