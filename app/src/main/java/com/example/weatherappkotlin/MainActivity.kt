package com.example.weatherappkotlin

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /*
        地域一覧
         */
        //地域一覧の設定値
        val spinner: Spinner = findViewById(R.id.placeSpinner)
        val selectItem = arrayOf("東京", "札幌", "仙台", "新潟", "栃木", "大阪", "鹿児島", "沖縄")

        // プルダウンを設定
        val itemAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, selectItem)
        spinner.adapter = itemAdapter

        /*
        送信ボタン押下時処理
         */
        // 1. viewの取得
        val btnWeatherDetail: Button = findViewById(R.id.btnWeatherDetail)

        // 2: ボタン押下で天気詳細画面へ移動
        btnWeatherDetail.setOnClickListener {

            // 遷移先の画面にプルダウンの値を送信
            val selectedPlace : String = spinner.selectedItem.toString()

            // 値受け渡しに必要な値を設定
            val intent = Intent(this, weather_detail::class.java)
            intent.putExtra("SELECTED_PLACE", selectedPlace)

            // 画面遷移
            startActivity(intent)
        }
    }
}