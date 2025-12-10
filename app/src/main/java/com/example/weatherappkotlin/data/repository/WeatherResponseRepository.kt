package com.example.weatherappkotlin.data.repository

import com.example.weatherappkotlin.util.WEATHER_API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherResponseRepository {
    @GET("weather") //URL
    suspend fun fetchWeatherData(
        @Query("q") city: String,
        @Query("appid") apiKey: String = WEATHER_API_KEY?:"",
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "ja"
    ): String
//    ): WeatherApiResponseModel
}
