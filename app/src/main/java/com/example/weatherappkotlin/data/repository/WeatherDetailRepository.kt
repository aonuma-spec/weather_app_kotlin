package com.example.weatherappkotlin.data.repository

import com.example.weatherappkotlin.data.model.WeatherDetailModel
import org.json.JSONObject
import kotlin.math.abs

class WeatherDetailRepository {

    fun parseWeatherData(
        placeVal: String?,
        minTempData: String?,
        maxTempData: String?
    ): WeatherDetailModel? {
        if (placeVal == null || minTempData == null || maxTempData == null) {
            return null
        }

        // JSONオブジェクトを生成
        val jsonObj = JSONObject(placeVal ?: "")
        val minJsonObj = JSONObject(minTempData ?: "")
        val maxJsonObj = JSONObject(maxTempData ?: "")

        // 選択中の地域・平均気温が低い地域・平均気温が高い地域のJSONデータのMapを設定
        val selectedData = openJson(jsonObj)
        val minPlaceWeatherData = openJson(minJsonObj)
        val maxPlaceWeatherData = openJson(maxJsonObj)

        // 選択中の地域情報
        val currentTemp = selectedData["temp"] as Double
        val minTemp = minPlaceWeatherData["temp"] as Double
        val maxTemp = maxPlaceWeatherData["temp"] as Double

        return WeatherDetailModel(
            placeName = selectedData["name"] as String,
            weatherMain = selectedData["main"] as String,
            description = selectedData["description"] as String,
            currentTemp = currentTemp,
            humidity = selectedData["humidity"] as Int,
            minPlaceName = minPlaceWeatherData["name"] as String,
            minTemp = minTemp,
            minTempDiff = calcTempDiff(currentTemp, minTemp),
            maxPlaceName = maxPlaceWeatherData["name"] as String,
            maxTemp = maxTemp,
            maxTempDiff = calcTempDiff(currentTemp, maxTemp)
        )
    }

    /**
     * 天気情報取得APIのjsonデータをマップに格納
     */
    private fun openJson(JsonData: JSONObject): Map<String, Any> {
        // name
        val jsonPlace = JsonData.getString("name")

        // weather
        val weatherJsonArray = JsonData.getJSONArray("weather")
        val weatherJson = weatherJsonArray.getJSONObject(0)
        val jsonWeatherMain = weatherJson.getString("main")
        val jsonWeatherDescription = weatherJson.getString("description")

        // main
        val mainJson = JsonData.getJSONObject("main")
        val jsonTemp = mainJson.getDouble("temp")
        val jsonHumidity = mainJson.getInt("humidity")

        // jsonデータをキーに設定
        val weatherMap = mapOf(
            "name" to jsonPlace, // 地域名
            "main" to jsonWeatherMain, // 天気
            "description" to jsonWeatherDescription, // 天気詳細
            "temp" to jsonTemp, // 温度
            "humidity" to jsonHumidity, // 湿度
        )

        return weatherMap
    }

    private fun calcTempDiff(currentTemp: Double, targetTemp: Double) =
        abs(currentTemp - targetTemp)
}