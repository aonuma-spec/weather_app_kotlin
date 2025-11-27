package com.example.weatherappkotlin

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONObject

class weather_detail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_weather_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /**
         * 選択された地域を表示
         */
        // 1: viewを取得
        val tvPlace: TextView = findViewById(R.id.tvPlace)
        val tvDetail: TextView = findViewById(R.id.tvDetail)
        val img_otenki: ImageView = findViewById(R.id.img_otenki)
        val tvWeather: TextView = findViewById(R.id.tvWeather)
        val tvTemp: TextView = findViewById(R.id.tvTemp)
        val tvHumidity: TextView = findViewById(R.id.tvHumidity)
        val tvWeatherData: TextView = findViewById(R.id.tvWeatherData)
        val tvTempData: TextView = findViewById(R.id.tvTempData)
        val tvHumidityData: TextView = findViewById(R.id.tvHumidityData)
        val tvTemperatureDifference: TextView = findViewById(R.id.tvTemperatureDifference)
        val tvTempMin: TextView = findViewById(R.id.tvTempMin)
        val tvTempMax: TextView = findViewById(R.id.tvTempMax)

        // 2: 渡された値を取り出す
        val placeVal = intent.getStringExtra("SELECTED_PLACE_VALUE")

        // JSONオブジェクトを生成
        val jsonObj = JSONObject(placeVal?:"")

        // name
        val jsonPlace = jsonObj.getString("name")

        // weather
        val weatherJsonArray = jsonObj.getJSONArray("weather")
        val weatherJson = weatherJsonArray.getJSONObject(0)
        val jsonWeather = weatherJson.getString("description")

        // main
        val mainJson = jsonObj.getJSONObject("main")
        val jsonTemp = mainJson.getString("temp")
        val jsonHumidity = mainJson.getString("humidity")
        val jsonTempMinDiff = mainJson.getString("temp") // 各地との平均が低い場所との気温差（仮）
        val jsonTempMaxDiff = mainJson.getString("temp") // 各地との平均が高い場所との気温差（仮）

        tvPlace.text = jsonPlace // 地域名
        tvWeatherData.text = jsonWeather // 天気
        tvTempData.text = jsonTemp + " 度" //温度
        tvHumidityData.text = jsonHumidity + " %" // 湿度
        tvTemperatureDifference.text = jsonPlace + "と各地の気温差について" // 地域気温差タイトル
        tvTempMin.text = "日本で平均気温が低い「陸別（現在${jsonTempMinDiff}度）」より${jsonTempMinDiff}度暖かいです" // 各地との平均が低い場所との気温差（仮）
        tvTempMax.text = "日本で平均気温が高い「沖縄（現在${jsonTempMaxDiff}度）」より${jsonTempMaxDiff}度暖かいです" // 各地との平均が高い場所との気温差（仮）
    }
}