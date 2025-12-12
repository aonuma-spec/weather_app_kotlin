package com.example.weatherappkotlin.ui

import CustomSpinnerAdapterModel
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.weatherappkotlin.R
import com.example.weatherappkotlin.data.model.PlaceModel
import com.example.weatherappkotlin.data.model.WeatherApiResponseModel
import com.example.weatherappkotlin.data.repository.WeatherRepository
import com.example.weatherappkotlin.databinding.FragmentMainBinding
import com.example.weatherappkotlin.presenter.MainContract
import com.example.weatherappkotlin.presenter.MainPresenter
import com.example.weatherappkotlin.util.PLACE_LIST
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment(), MainContract.View {

    // view bindingインスタンスを保持する
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    // Fragment内でRepositoryとPresenterを初期化
    @Inject lateinit var repository: WeatherRepository
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //PresenterにDIのRepositoryを設定し、Viewと紐づける
        presenter = MainPresenter(this, repository)

        setupUI()

        //処理の完了をPresenterに通知
        presenter.onViewCreated()
    }

    // -- MainContract.Viewの実装をここに移す --

    override fun showApiKeyErrorDialog() {
        // requireContext() を使ってダイアログを表示
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("エラー")
        builder.setMessage("エラーが発生しました")
        builder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    override fun showApiErrorDialog() {
        // requireContext() を使ってダイアログを表示
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("エラー")
        builder.setMessage("天気の取得に失敗しました")
        builder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    override fun navigateToDetail(
        weatherJson: WeatherApiResponseModel?,
        minTempData: WeatherApiResponseModel?,
        maxTempData: WeatherApiResponseModel?
    ) {
        val fragment = WeatherDetailFragment.newInstance(
            weatherJson,
            minTempData,
            maxTempData
        )

        // 天気アプリトップ画面のFragmentから天気アプリ詳細画面のFragmentへ切り替え
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.flMain, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun setupUI() {
        val placeAdapter = CustomSpinnerAdapterModel(
            requireContext(), // requireContext() を使用
            android.R.layout.simple_spinner_dropdown_item,
            PLACE_LIST
        )
        binding.placeSpinner.adapter = placeAdapter

        // ボタン押下で天気詳細画面へ移動
        binding.btnWeatherDetail.setOnClickListener {
            val selectedPlaceObject = binding.placeSpinner.selectedItem as PlaceModel
            presenter.onWeatherDetailButtonClicked(selectedPlaceObject)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment()
    }
}