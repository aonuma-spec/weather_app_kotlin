package com.example.weatherappkotlin

import CustomSpinnerAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.weatherappkotlin.data.model.Place
import com.example.weatherappkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // binding変数を設定
    private lateinit var binding: ActivityMainBinding

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

        /*
        地域一覧
         */
        //地域一覧の設定値
        val spinner: Spinner = findViewById(R.id.placeSpinner)

        val placeList = listOf(
            Place("東京", "tokyo"),
            Place("札幌", "sapporo"),
            Place("仙台", "sendai"),
            Place("新潟", "niigata"),
            Place("栃木", "tochigi"),
            Place("大阪", "osaka"),
            Place("鹿児島", "kagoshima"),
            Place("沖縄", "okinawa")
        )

        // spinner用のカスタムアダプターを設定
        val placeAdapter =
            CustomSpinnerAdapter(this, android.R.layout.simple_spinner_dropdown_item, placeList)
        binding.placeSpinner.adapter = placeAdapter

        /*
        送信ボタン押下時処理
         */
        // 1. viewの取得
        val btnWeatherDetail: Button = findViewById(R.id.btnWeatherDetail)

        // 2: ボタン押下で天気詳細画面へ移動
        binding.btnWeatherDetail.setOnClickListener {

            // 選択されたPlaceオブジェクトを取得
            val selectedPlaceObject = binding.placeSpinner.selectedItem as Place

            // 表示名のvalueを取得
            val valueToSend = selectedPlaceObject.value

            // 値受け渡しに必要な値を設定
            val intent = Intent(this, weather_detail::class.java)
            intent.putExtra("SELECTED_PLACE_VALUE", valueToSend)

            // 画面遷移
            startActivity(intent)
        }
    }
}