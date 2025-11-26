package com.example.weatherappkotlin

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class weather_detail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_weather_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /*
        選択された地域を表示
         */
        // 1: viewを取得
        val placeTitle: TextView = findViewById(R.id.tvPlace)
        val tvTemperatureDifference: TextView = findViewById(R.id.tvTemperatureDifference)

        // 2: 渡された値を取り出す
        val placeVal = intent.getStringExtra("SELECTED_PLACE")

        placeTitle.text = placeVal
        tvTemperatureDifference.text = placeVal + "と各地の気温差について"
//                intent.putExtra("SELECTED_PLACE", selectedPlace)

    }
}