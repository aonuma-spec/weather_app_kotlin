package com.example.weatherappkotlin.presenter
import androidx.lifecycle.LifecycleCoroutineScope
import com.example.weatherappkotlin.data.model.PlaceModel
import com.example.weatherappkotlin.data.repository.WeatherRepository
import com.example.weatherappkotlin.util.MAIN_URL
import com.example.weatherappkotlin.util.WEATHER_API_KEY
import com.example.weatherappkotlin.util.WEATHER_URL_MAX
import com.example.weatherappkotlin.util.WEATHER_URL_MIN
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

        // API実行用のURLを設定
        val weatherUrl =
            "${MAIN_URL}${WEATHER_API_KEY}&lang=ja&units=metric&q=${selectedPlace.value}"

        // API実行処理
        fetchWeatherData(weatherUrl)
    }

    /**
     * API実行を行う処理
     */
    private fun fetchWeatherData(weatherUrl: String) {

        lifecycleScope.launch {
            val weatherJsonData = repository.fetchWeatherJson(weatherUrl)
            val weatherUrlMinTempData = repository.fetchWeatherJson(WEATHER_URL_MIN)
            val weatherUrlMaxTempData = repository.fetchWeatherJson(WEATHER_URL_MAX)

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