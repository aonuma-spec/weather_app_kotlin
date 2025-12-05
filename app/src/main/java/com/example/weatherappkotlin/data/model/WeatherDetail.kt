package com.example.weatherappkotlin.data.model

/**
 * 天気APIから受け取ったJSONパースを行う
 */
data class WeatherDetail(
    val placeName: String,
    val weatherMain: String,
    val description: String,
    val currentTemp: Double,
    val humidity: Int,
    val minPlaceName: String,
    val minTemp: Double,
    val minTempDiff: Double,
    val maxPlaceName: String,
    val maxTemp: Double,
    val maxTempDiff: Double
)