package com.example.weatherappkotlin.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL

// APIコールの実行とJSONデータの取得（HTTP通信を行う部分）
class WeatherRepository {

    /**
     * HTTP通信 : ワーカースレッドの中身
     * スレッドを分離し、IO（ワーカースレッド）で実行
     */
    suspend fun fetchWeatherJson(weatherUrl: String): String = withContext(Dispatchers.IO) {
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
        } catch (e: Exception) {
            // エラーを出力
            e.printStackTrace()
        }
        // HTTP通信の結果、取得したjsonをhttpRequesetを戻り値とする
        return@withContext httpResult
    }
}