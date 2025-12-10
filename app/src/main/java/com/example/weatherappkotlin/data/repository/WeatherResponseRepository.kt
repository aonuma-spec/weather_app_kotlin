package com.example.weatherappkotlin.data.repository

import com.example.weatherappkotlin.data.model.WeatherApiResponseModel
import com.example.weatherappkotlin.util.WEATHER_API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

class WeatherResponseRepository(
    private var api: WeatherApiService
){
    suspend fun fetchWeatherData(city: String): WeatherResult {
        return try{
                val data = api.fetchWeatherData(city)
                WeatherResult.Success(data)
            } catch(e: Exception) {
                WeatherResult.Error(e)
            }
    }
}

//APIの結果を管理するためのクラス
sealed class WeatherResult {
    //成功した場合
    data class Success (
        val data: String
    ): WeatherResult()

    //エラーの場合
    data class Error(
        val exception: Throwable
    ): WeatherResult()
}