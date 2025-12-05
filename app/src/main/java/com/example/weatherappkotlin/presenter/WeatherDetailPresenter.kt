package com.example.weatherappkotlin.presenter

import android.util.Log
import com.example.weatherappkotlin.R
import com.example.weatherappkotlin.data.repository.WeatherDetailRepository

class WeatherDetailPresenter(
    private var view: WeatherDetailContract.View?,
    private var repository: WeatherDetailRepository
): WeatherDetailContract.Presenter {

    override fun handleIntentData(placeVal: String?, minTempData: String?, maxTempData: String?) {
        val detailData = repository.parseWeatherData(placeVal, minTempData, maxTempData)

        if (detailData == null) {
            view?.showErrorAndFinish()
            return
        }

        //viewにデータを渡す
        view?.displayWeatherData(detailData)

        val imageResourceId = when(detailData.weatherMain) { // 天候ごとの画像を設定
            "Clear" -> R.drawable.mark_tenki_hare
            "Clouds" -> R.drawable.mark_tenki_kumori
            "Rain" -> R.drawable.mark_tenki_umbrella
            "Snow" -> R.drawable.tenki_snow
            else -> R.drawable.mark_question
        }
        view?.setWeatherIcon(imageResourceId)
    }

    override fun onDestroy() {
        view = null
    }
}