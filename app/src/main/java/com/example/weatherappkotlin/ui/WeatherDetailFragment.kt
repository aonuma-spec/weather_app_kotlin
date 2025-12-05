package com.example.weatherappkotlin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.weatherappkotlin.R
import com.example.weatherappkotlin.data.model.WeatherDetailModel
import com.example.weatherappkotlin.data.repository.WeatherDetailRepository
import com.example.weatherappkotlin.presenter.WeatherDetailContract
import com.example.weatherappkotlin.presenter.WeatherDetailPresenter
import com.example.weatherappkotlin.databinding.FragmentWeatherDetailBinding


class WeatherDetailFragment : Fragment(), WeatherDetailContract.View {


    // View Bindingインスタンスを保持
    private var _binding: FragmentWeatherDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: WeatherDetailPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = WeatherDetailRepository()
        presenter = WeatherDetailPresenter(this, repository)

        // 手動でBundleから受け取る場合
        val placeVal = arguments?.getString("SELECTED_PLACE_VALUE")
        val minTempData = arguments?.getString("MIN_TEMP_DATA")
        val maxTempData = arguments?.getString("MAX_TEMP_DATA")

        // Presenterに処理を依頼
        presenter.handleIntentData(placeVal, minTempData, maxTempData)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        presenter.onDestroy()
    }

    override fun displayWeatherData(detail: WeatherDetailModel) {
        binding.tvPlace.text = detail.placeName
        binding.tvWeatherData.text = detail.description
        binding.tvTempData.text = getString(R.string.temperatureDisplay, detail.currentTemp)
        binding.tvHumidityData.text = getString(R.string.humidityDisplay, detail.humidity)
        binding.tvTemperatureDifference.text =
            getString(R.string.temperatureDifferenceTitle, detail.placeName)
        binding.tvTempMin.text =
            getString(
                R.string.tempMinComparison,
                detail.minPlaceName,
                detail.minTemp,
                detail.minTempDiff
            )
        binding.tvTempMax.text =
            getString(
                R.string.tempMaxComparison,
                detail.maxPlaceName,
                detail.maxTemp,
                detail.maxTempDiff
            )
    }

    override fun setWeatherIcon(resourceId: Int) {
        binding.imgWeather.setImageResource(resourceId)
    }

    override fun showErrorAndFinish() {
        // Activityを終了する代わりに、Toast表示後に前の画面（Fragment）に戻る
        Toast.makeText(context, "天気データの読み込みに失敗しました", Toast.LENGTH_LONG).show()
        // FragmentをFragmentManagerからポップして前の画面に戻る
        parentFragmentManager.popBackStack()
    }

    // MainFragmentからの遷移時に使用する newInstance メソッド
    companion object {
        @JvmStatic
        fun newInstance(weatherJson: String, minTempData: String, maxTempData: String) =
            WeatherDetailFragment().apply {
                arguments = Bundle().apply {
                    putString("SELECTED_PLACE_VALUE", weatherJson)
                    putString("MIN_TEMP_DATA", minTempData)
                    putString("MAX_TEMP_DATA", maxTempData)
                }
            }
    }
}