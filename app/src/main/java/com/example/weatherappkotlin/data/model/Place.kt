package com.example.weatherappkotlin.data.model

/**
 * spinnerで使用する選択地域の表示名と値
 */
data class Place(
    val name: String,   // 表示名 (例: "東京")
    val value: String   // 裏側の値 (例: "tokyo")
)