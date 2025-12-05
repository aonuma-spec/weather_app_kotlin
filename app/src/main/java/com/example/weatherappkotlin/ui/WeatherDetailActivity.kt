package com.example.weatherappkotlin.ui

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.weatherappkotlin.R
import com.example.weatherappkotlin.data.model.WeatherDetail
import com.example.weatherappkotlin.data.repository.WeatherDetailRepository
import com.example.weatherappkotlin.databinding.ActivityWeatherDetailBinding
import com.example.weatherappkotlin.presenter.WeatherDetailContract
import com.example.weatherappkotlin.presenter.WeatherDetailPresenter
import org.json.JSONObject
import kotlin.math.abs

class WeatherDetailActivity : AppCompatActivity(), WeatherDetailContract.View {
    // binding変数を設定
    private lateinit var binding: ActivityWeatherDetailBinding
    private lateinit var presenter: WeatherDetailPresenter

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

        val repository = WeatherDetailRepository()
        presenter = WeatherDetailPresenter(this, repository)


        // Intentからデータを受け取り、Presenterに処理を依頼
        val placeVal = intent.getStringExtra("SELECTED_PLACE_VALUE")
        val minTempData = intent.getStringExtra("MIN_TEMP_DATA")
        val maxTempData = intent.getStringExtra("MAX_TEMP_DATA")

        presenter.handleIntentData(placeVal, minTempData, maxTempData)
    }

    override fun displayWeatherData(detail: WeatherDetail) {
        // Presenterから受け取ったデータでUIを更新する
        binding.tvPlace.text = detail.placeName
        binding.tvWeatherData.text = detail.description

        // Anndroidリソースへのアクセス
        binding.tvTempData.text = getString(R.string.temperatureDisplay, detail.currentTemp)
        binding.tvHumidityData.text = getString(R.string.humidityDisplay, detail.humidity)

        binding.tvTemperatureDifference.text =
            getString(R.string.temperatureDifferenceTitle, detail.placeName)
        binding.tvTempMin.text =
            getString(R.string.tempMinComparison, detail.minPlaceName, detail.minTemp, detail.minTempDiff)
        binding.tvTempMax.text =
            getString(R.string.tempMaxComparison, detail.maxPlaceName, detail.maxTemp, detail.maxTempDiff)
    }

    override fun setWeatherIcon(resourceId: Int) {
        binding.imgWeather.setImageResource(resourceId)
    }

    override fun showErrorAndFinish() {
        Toast.makeText(this, "天気データの読み込みに失敗しました", Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}