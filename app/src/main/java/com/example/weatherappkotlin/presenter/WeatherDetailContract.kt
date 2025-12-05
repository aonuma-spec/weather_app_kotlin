package com.example.weatherappkotlin.presenter

import com.example.weatherappkotlin.data.model.WeatherDetailModel

interface WeatherDetailContract {

    //MainActivityから受け取ったIntentデータをPresenterに渡す
    interface Presenter {
        fun handleIntentData(placeVal: String?, minTempData: String?, maxTempData: String?)
        fun onDestroy()
    }

    // Presenterからデータを受け取り表示する
    interface View {
        fun displayWeatherData(detail: WeatherDetailModel)
        fun setWeatherIcon(resourceId: Int)
        fun showErrorAndFinish()
    }
}