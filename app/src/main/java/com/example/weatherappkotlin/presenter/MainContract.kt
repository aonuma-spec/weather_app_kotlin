package com.example.weatherappkotlin.presenter

import com.example.weatherappkotlin.data.model.PlaceModel

/**
 * ViewとPresenterの間で呼び出せるメソッドを定義する
 */
interface MainContract {

    // ViewがPresenterに要求できる操作
    interface Presenter {
        fun onViewCreated()
        fun onWeatherDetailButtonClicked(selectedPlace: PlaceModel)
        fun onDestroy() // ViewのライフサイクルとPresenterを連携させる
    }

    //PresenterがViewに要求できる操作
    interface View {
        fun showApiKeyErrorDialog()
        fun showApiErrorDialog()
        fun navigateToDetail(weatherJson: String, minTempData: String, maxTempData: String)
    }
}