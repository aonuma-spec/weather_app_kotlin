package com.example.weatherappkotlin.util

import com.example.weatherappkotlin.BuildConfig.WEATHER_API_KEY
import com.example.weatherappkotlin.data.model.PlaceModel

// 天気情報取得APIキー
val WEATHER_API_KEY: String? = WEATHER_API_KEY
// URL
const val MAIN_URL = "https://api.openweathermap.org/data/2.5/weather?appid="

val PLACE_LIST = listOf(
    PlaceModel("東京", "tokyo"),
    PlaceModel("札幌", "sapporo"),
    PlaceModel("仙台", "sendai"),
    PlaceModel("新潟", "niigata"),
    PlaceModel("栃木", "tochigi"),
    PlaceModel("大阪", "osaka"),
    PlaceModel("鹿児島", "kagoshima"),
    PlaceModel("沖縄", "okinawa")
)

// API実行用のURLを設定（平均が低い）
val TEMP_MIN_PLACE = "rikubetsu"

// API実行用のURLを設定（平均が高い）
val TEMP_MAX_PLACE = "okinawa"