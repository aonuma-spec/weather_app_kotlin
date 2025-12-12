package com.example.weatherappkotlin.data.repository

import android.util.Log
import com.example.weatherappkotlin.data.model.WeatherApiResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

// APIコールの実行とJSONデータの取得（HTTP通信を行う部分）
class WeatherRepository @Inject constructor(
    private val api: WeatherApiService //DI
){
    /**
     * 天気情報取得APIを実行
     */
    suspend fun fetchWeatherJson(selectedPlace: String): WeatherApiResponseModel? {
        return try{
            api.fetchWeatherData(selectedPlace)
        } catch (e: Exception){
            e.printStackTrace()
            null
        }
    }
}