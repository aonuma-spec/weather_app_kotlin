package com.example.weatherappkotlin.presenter
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.weatherappkotlin.data.model.PlaceModel
import com.example.weatherappkotlin.data.repository.WeatherRepository
import com.example.weatherappkotlin.util.WEATHER_API_KEY
import com.example.weatherappkotlin.util.TEMP_MAX_PLACE
import com.example.weatherappkotlin.util.TEMP_MIN_PLACE
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * ユーザーの入力イベントを受け取り、Modelと連携してViewを更新する
 */
class MainPresenter(
    // Viewへの参照をContract経由で保持する
    private var view: MainContract.View?,
    private val repository: WeatherRepository,
    private val lifecycleScope: LifecycleCoroutineScope
) : MainContract.Presenter {
    private var apiKey: String? = null
    private var mainUrl: String? = null

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

//        // API実行用のURLを設定
//        val weatherUrl =
//            "${MAIN_URL}${WEATHER_API_KEY}&lang=ja&units=metric&q=${selectedPlace.value}"

        // API実行処理
        fetchWeatherData(selectedPlace.value)
//        fetchWeatherData(weatherUrl)
    }

    /**
     * API実行を行う処理
     */
    private fun fetchWeatherData(selectedPlace: String) {

        lifecycleScope.launch {
            val weatherDeferred = async {repository.fetchWeatherJson(selectedPlace)}
            val weatherUrlMinTempDeferred = async {repository.fetchWeatherJson(TEMP_MIN_PLACE)}
            val weatherUrlMaxTempDeferred = async {repository.fetchWeatherJson(TEMP_MAX_PLACE)}

            val weatherJsonData = weatherDeferred.await()
            val weatherUrlMinTempData = weatherUrlMinTempDeferred.await()
            val weatherUrlMaxTempData = weatherUrlMaxTempDeferred.await()

            if (weatherJsonData.isNotEmpty() && weatherUrlMaxTempData.isNotEmpty() && weatherUrlMaxTempData.isNotEmpty()) {

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
        view = null
    }
}