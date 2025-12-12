package com.example.weatherappkotlin.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// 全体のレスポンス
@Parcelize
@Serializable
data class WeatherApiResponseModel(
    val name: String,
    val weather: List<WeatherInfo>,
    val main: MainInfo
) : Parcelable

// weather
@Parcelize
@Serializable
data class WeatherInfo(
    @SerialName("main") val weatherMain: String,
    val description: String
) : Parcelable

// main
@Parcelize
@Serializable
data class MainInfo(
    val temp: Double,
    val humidity: Int,
    @SerialName("temp_min") val tempMin: Double,
    @SerialName("temp_max") val tempMax: Double
) : Parcelable