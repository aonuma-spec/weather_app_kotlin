package com.example.weatherappkotlin.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import androidx.core.view.WindowInsetsCompat
import com.example.weatherappkotlin.R
import com.example.weatherappkotlin.data.repository.WeatherRepository
import com.example.weatherappkotlin.databinding.ActivityMainBinding
import com.example.weatherappkotlin.presenter.MainPresenter
import CustomSpinnerAdapterModel
import com.example.weatherappkotlin.data.model.PlaceModel
import com.example.weatherappkotlin.presenter.MainContract
import com.example.weatherappkotlin.util.PLACE_LIST

class MainActivity : AppCompatActivity(), MainContract.View {

    // binding変数を設定
    private lateinit var binding: ActivityMainBinding

    // 紐付け用のPresenterを設定(ViewがPresenterと通信するための準備)
    private lateinit var presenter: MainPresenter

    // 天気詳細画面を設定
    private val weatherDetailActivityClass = WeatherDetailActivity::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // bindingインスタンスを初期化
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // PresenterとRepositoryを初期化し、ViewをPresenterと紐付ける
        val repository = WeatherRepository()
        presenter = MainPresenter(this, repository, lifecycleScope)

        setupUI()

        // onCreate完了をPresenterに通知
        presenter.onViewCreated()
    }
    // -- MainContract.Viewの実装 --

    override fun showApiKeyErrorDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("エラー")
        builder.setMessage("天気の取得に失敗しました")

        builder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }


    /**
     * 天気情報取得API実行
     */
    override fun navigateToDetail(weatherJson: String, minTempData: String, maxTempData: String) {

        // 値受け渡しに必要な値を設定
        val intent = Intent(this, WeatherDetailActivity::class.java).apply {
            putExtra("SELECTED_PLACE_VALUE", weatherJson)
            putExtra("MIN_TEMP_DATA", minTempData)
            putExtra("MAX_TEMP_DATA", maxTempData)
        }
        startActivity(intent)
    }

    /**
     * 画面を設定
     */
    fun setupUI() {
        val placeAdapter = CustomSpinnerAdapterModel(
            this, android.R.layout.simple_spinner_dropdown_item,
            PLACE_LIST
        )
        binding.placeSpinner.adapter = placeAdapter

        // ボタン押下で天気詳細画面へ移動
        binding.btnWeatherDetail.setOnClickListener {
            val selectedPlaceObject = binding.placeSpinner.selectedItem as PlaceModel
            presenter.onWeatherDetailButtonClicked(selectedPlaceObject)
        }
    }
}