package com.example.weatherappkotlin.util

import com.example.weatherappkotlin.BuildConfig
import com.example.weatherappkotlin.data.model.PlaceModel

// 天気情報取得APIキー
const val WEATHER_API_KEY: String = BuildConfig.WEATHER_API_KEY
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
val WEATHER_URL_MIN =
    "https://api.openweathermap.org/data/2.5/weather?appid=" + WEATHER_API_KEY + "&lang=ja&units=metric&q=rikubetsu"

// API実行用のURLを設定（平均が高い）
val WEATHER_URL_MAX =
    "https://api.openweathermap.org/data/2.5/weather?appid=" + WEATHER_API_KEY + "&lang=ja&units=metric&q=okinawa"