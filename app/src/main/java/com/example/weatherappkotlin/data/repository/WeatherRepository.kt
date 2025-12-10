package com.example.weatherappkotlin.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

// APIコールの実行とJSONデータの取得（HTTP通信を行う部分）
class WeatherRepository {

    /**
     * HTTP通信 : ワーカースレッドの中身
     * スレッドを分離し、IO（ワーカースレッド）で実行
     */
    suspend fun fetchWeatherJson(selectedPlace: String): String = withContext(Dispatchers.IO) {
        try{
        // 天気情報サービスを実行する
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        val api = retrofit.create(WeatherResponseRepository::class.java) //インターフェースのクラス名

        return@withContext api.fetchWeatherData(selectedPlace)
        } catch (e: Exception){
            e.printStackTrace()
            return@withContext ""
        }
    }
}