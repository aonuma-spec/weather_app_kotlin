package com.example.weatherappkotlin.presenter
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.weatherappkotlin.data.model.PlaceModel
import com.example.weatherappkotlin.data.repository.WeatherRepository
import com.example.weatherappkotlin.util.WEATHER_API_KEY
import com.example.weatherappkotlin.util.TEMP_MAX_PLACE
import com.example.weatherappkotlin.util.TEMP_MIN_PLACE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * ユーザーの入力イベントを受け取り、Modelと連携してViewを更新する
 */
class MainPresenter(
    // Viewへの参照をContract経由で保持する
    private var view: MainContract.View?,
    private val repository: WeatherRepository,
) : MainContract.Presenter {
    private var apiKey: String? = null

    // presenter用のCoroutineScope
    private val presenterScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onViewCreated() {
        this.apiKey = WEATHER_API_KEY
        if (apiKey.isNullOrEmpty()) {
            view?.showApiKeyErrorDialog()
        }
    }

    override fun onWeatherDetailButtonClicked(selectedPlace: PlaceModel) {

        val currentKey = apiKey

        if (currentKey.isNullOrEmpty()) {
            view?.showApiErrorDialog()
            return
        }

        // API実行処理
        fetchWeatherData(selectedPlace.value)
    }

    /**
     * API実行を行う処理
     */
    private fun fetchWeatherData(selectedPlace: String) {

        presenterScope.launch {
            val weatherDeferred = async { repository.fetchWeatherJson(selectedPlace) }
            val weatherUrlMinTempDeferred = async { repository.fetchWeatherJson(TEMP_MIN_PLACE) }
            val weatherUrlMaxTempDeferred = async { repository.fetchWeatherJson(TEMP_MAX_PLACE) }

            val weatherJsonData = weatherDeferred.await()
            val weatherUrlMinTempData = weatherUrlMinTempDeferred.await()
            val weatherUrlMaxTempData = weatherUrlMaxTempDeferred.await()

            if (weatherJsonData != null && weatherUrlMinTempData != null && weatherUrlMaxTempData != null) {
                view?.navigateToDetail(
                    weatherJsonData,
                    weatherUrlMinTempData,
                    weatherUrlMaxTempData
                )
            } else {
                view?.showApiErrorDialog()
            }
        }
    }

    override fun onDestroy() {
        presenterScope.cancel()
        view = null
    }
}