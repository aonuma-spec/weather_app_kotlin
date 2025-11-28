package com.example.weatherappkotlin

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.weatherappkotlin.databinding.ActivityWeatherDetailBinding
import org.json.JSONObject
import java.util.Locale
import kotlin.math.abs

class weather_detail : AppCompatActivity() {
    // binding変数を設定
    private lateinit var binding: ActivityWeatherDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_weather_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // bindingインスタンスを初期化
        binding = ActivityWeatherDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        /**
         * 選択された地域を表示
         */
        // 1: viewを取得
        val tvPlace: TextView = findViewById(R.id.tvPlace)
        val imgWeatherView: ImageView = binding.imgWeather
        val tvWeatherData: TextView = findViewById(R.id.tvWeatherData)
        val tvTempData: TextView = findViewById(R.id.tvTempData)
        val tvHumidityData: TextView = findViewById(R.id.tvHumidityData)
        val tvTemperatureDifference: TextView = findViewById(R.id.tvTemperatureDifference)
        val tvTempMin: TextView = findViewById(R.id.tvTempMin)
        val tvTempMax: TextView = findViewById(R.id.tvTempMax)

        // 2: 渡された値を取り出す
        val placeVal = intent.getStringExtra("SELECTED_PLACE_VALUE")
        val minTempData = intent.getStringExtra("MIN_TEMP_DATA")
        val maxTempData = intent.getStringExtra("MAX_TEMP_DATA")

        // JSONオブジェクトを生成
        val jsonObj = JSONObject(placeVal ?: "")
        val minJsonObj = JSONObject(minTempData ?: "")
        val maxJsonObj = JSONObject(maxTempData ?: "")

        // 選択中の地域・平均気温が低い地域・平均気温が高い地域のJSONデータのMapを設定
        val selectedPlaceWeatherData = openJson(jsonObj)
        val minPlaceWeatherData = openJson(minJsonObj)
        val maxPlaceWeatherData = openJson(maxJsonObj)

        // 選択中の地域情報
        val selectedPlaceName =
            selectedPlaceWeatherData["name"] as? String ?: "不明な地域" // 選択中の地域
        val currentTemp = selectedPlaceWeatherData["temp"] as Double

        // 平均気温が低い地域・平均気温が高い地域の情報
        val minPlaceName = minPlaceWeatherData["name"] as? String ?: "不明な地域" // 平均気温が低い地域
        val maxPlaceName = maxPlaceWeatherData["name"] as? String ?: "不明な地域" // 平均気温が高い地域
        val minTemp = minPlaceWeatherData["temp"] as Double
        val maxTemp = maxPlaceWeatherData["temp"] as Double
        val minTempDiff = calcTempDiff(currentTemp, minTemp)
        val maxTempDiff = calcTempDiff(currentTemp, maxTemp)

        // 画面にAPIから取得した値を反映

        // 地域名
        tvPlace.text = selectedPlaceName
        val imageResourceId = when (selectedPlaceWeatherData["main"]) { // 天候ごとの画像を設定
            "Clear" -> R.drawable.mark_tenki_hare
            "Clouds" -> R.drawable.mark_tenki_kumori
            "Rain" -> R.drawable.mark_tenki_umbrella
            "Snow" -> R.drawable.tenki_snow
            else -> R.drawable.mark_question
        }
        imgWeatherView.setImageResource(imageResourceId) /// 天候ごとの画像を画面に表示
        tvWeatherData.text = selectedPlaceWeatherData["description"] as String // 天気

        // 温度
        val tempValue = selectedPlaceWeatherData["temp"] as Double
        val tempFormattedText = getString(R.string.temperatureDisplay, tempValue)
        tvTempData.text = tempFormattedText

        // 湿度
        val humidityValue = selectedPlaceWeatherData["humidity"] as Int
        val humidityFormattedText = getString(R.string.humidityDisplay, humidityValue)
        tvHumidityData.text = humidityFormattedText

        // 地域の気温差
        tvTemperatureDifference.text =
            getString(R.string.temperatureDifferenceTitle, selectedPlaceName) // 地域気温差タイトル
        tvTempMin.text =
            getString(
                R.string.tempMinComparison,
                minPlaceName,
                minTemp,
                minTempDiff
            ) // 平均気温が低い地域テキスト

        tvTempMax.text =
            getString(
                R.string.tempMaxComparison,
                maxPlaceName,
                maxTemp,
                maxTempDiff
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