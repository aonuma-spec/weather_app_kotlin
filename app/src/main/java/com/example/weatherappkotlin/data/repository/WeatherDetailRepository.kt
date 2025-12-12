package com.example.weatherappkotlin.data.repository

import android.os.Parcelable
import com.example.weatherappkotlin.data.model.MainInfo
import com.example.weatherappkotlin.data.model.WeatherApiResponseModel
import com.example.weatherappkotlin.data.model.WeatherDetailModel
import org.json.JSONObject
import kotlin.math.abs

class WeatherDetailRepository {

    fun parseWeatherData(
        selectedData: WeatherApiResponseModel?,
        minPlaceWeatherData: WeatherApiResponseModel?,
        maxPlaceWeatherData: WeatherApiResponseModel?
    ): WeatherDetailModel? {
        if (selectedData == null || minPlaceWeatherData == null || maxPlaceWeatherData == null) {
            return null
        }

        // 選択中の地域情報
        val currentTemp = selectedData.main.temp
        val minTemp = minPlaceWeatherData.main.temp
        val maxTemp = maxPlaceWeatherData.main.temp

        return WeatherDetailModel(
            placeName = selectedData.name,
            weatherMain = selectedData.weather.firstOrNull()?.weatherMain as String,
            description = selectedData.weather.firstOrNull()?.description as String,
            currentTemp = currentTemp,
            humidity = selectedData.main.humidity,
            minPlaceName = minPlaceWeatherData.name,
            minTemp = minTemp,
            minTempDiff = calcTempDiff(currentTemp, minTemp),
            maxPlaceName = maxPlaceWeatherData.name,
            maxTemp = maxTemp,
            maxTempDiff = calcTempDiff(currentTemp, maxTemp)
        )
    }

    private fun calcTempDiff(currentTemp: Double, targetTemp: Double) =
        abs(currentTemp - targetTemp)
}