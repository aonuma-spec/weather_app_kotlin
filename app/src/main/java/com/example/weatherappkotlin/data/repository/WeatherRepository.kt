package com.example.weatherappkotlin.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

// APIコールの実行とJSONデータの取得（HTTP通信を行う部分）
class WeatherRepository @Inject constructor(
    private val api: WeatherApiService //DI
){
    /**
     * HTTP通信 : ワーカースレッドの中身
     * スレッドを分離し、IO（ワーカースレッド）で実行
     */
    suspend fun fetchWeatherJson(selectedPlace: String): String = withContext(Dispatchers.IO) {
        try{
            api.fetchWeatherData(selectedPlace)
        } catch (e: Exception){
            e.printStackTrace()
            ""
        }
    }
}