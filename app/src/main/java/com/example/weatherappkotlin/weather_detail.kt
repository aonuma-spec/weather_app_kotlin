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
        val placeVal = intent.getStringExtra("SELECTED_PLACE_VALUE")

        val displayMessage = when (placeVal) {
            "tokyo" -> {"東京"}
            "sapporo" -> {"札幌"}
            "sendai" -> {"仙台"}
            "niigata" -> {"新潟"}
            "tochigi" -> {"栃木"}
            "osaka" -> {"大阪"}
            "kagoshima" -> {"鹿児島"}
            "okinawa" -> {"沖縄"}
            else -> {"不明"}
        }

        placeTitle.text = displayMessage
        tvTemperatureDifference.text = displayMessage + "と各地の気温差について"
    }
}