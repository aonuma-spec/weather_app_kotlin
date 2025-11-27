package com.example.weatherappkotlin

import CustomSpinnerAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.weatherappkotlin.data.model.Place
import com.example.weatherappkotlin.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL

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

        // 天気情報取得APIキー
        val weatherApiKey: String = BuildConfig.WEATHER_API_KEY
        // URL
        val mainUrl = "https://api.openweathermap.org/data/2.5/weather?appid="

        /**
         * 地域一覧
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

        /**
         * 送信ボタン押下時処理
         */
        // 1. viewの取得
        val btnWeatherDetail: Button = findViewById(R.id.btnWeatherDetail)

        // 2: ボタン押下で天気詳細画面へ移動
        binding.btnWeatherDetail.setOnClickListener {

            // 選択されたPlaceオブジェクトを取得
            val selectedPlaceObject = binding.placeSpinner.selectedItem as Place

            // 表示名のvalueを取得
            val valueToSend = selectedPlaceObject.value

            // API実行用のURLを設定
            val weatherUrl = "${mainUrl}${weatherApiKey}&lang=ja&units=metric&q=${valueToSend}"

            // API実行
            weatherTask(weatherUrl)
        }
    }

    /**
     * 天気情報取得API実行
     */
    private fun weatherTask(weatherUrl: String) {
        var weatherJsonData: String = ""
        lifecycleScope.launch {
            // HTTP通信
            weatherJsonData = weatherBackgroundTask(weatherUrl)

            // 天気詳細画面へ移動：HTTP通信を受けてお天気データ(json)を表示
            nextDetailPage(weatherJsonData)
        }
    }

    /**
     * HTTP通信 : ワーカースレッドの中身
     */
    private suspend fun weatherBackgroundTask(weatherUrl: String): String {

        // スレッドを分離し、IO（ワーカースレッド）で実行
        val response = withContext(Dispatchers.IO) {

            // 天気情報サービスから受信した結果情報
            var httpResult = ""

            try {
                val urlObj = URL(weatherUrl)

                // テキストファイルを読み込み、URLを展開
                val br = BufferedReader(InputStreamReader(urlObj.openStream()))
                httpResult = br.readText()
            } catch (e: IOException) {
                // エラーを出力
                e.printStackTrace()
            } catch (e: JSONException) {
                // jsonエラーを出力
                e.printStackTrace()
            }
            // HTTP通信の結果、取得したjsonをhttpRequesetを戻り値とする
            return@withContext httpResult
        }

        return response
    }

    private fun nextDetailPage(weatherJsonData: String) {

        // 値受け渡しに必要な値を設定
        val intent = Intent(this, weather_detail::class.java)

        intent.putExtra("SELECTED_PLACE_VALUE", weatherJsonData)

        // 画面遷移
        startActivity(intent)
    }

}