# weather_app_kotlin
Kotlinで作成した天気アプリ。\
Weather APIからデータを取得し、画面に表示します。

## スクリーンショット
![アプリのスクリーンショット1](https://github.com/aonuma-spec/weather_app_kotlin/blob/Image/Images/app_view1.png)
![アプリのスクリーンショット2](https://github.com/aonuma-spec/weather_app_kotlin/blob/Image/Images/app_view2.png)

## 使用技術
- Android Studio
- Kotlin

## 利用プラグイン
- libs.plugins.android.application
- libs.plugins.kotlin.android
- com.google.android.libraries.mapsplatform.secrets-gradle-plugin

## 利用パッケージ
- Coroutines::1.6.0
- Android Jetpack Lifecycle KTX2.3.0
- AndroidX Core KTX
- AppCompat
- aterial Design
- Activity KTX
- ConstraintLayout
- JUnit
- Espresso Core

## 環境変数
- WEATHER_API_KEY: Weather APIより取得したAPIキーの値を設定

## ディレクトリ構成
weatherappkotlin/\
|__ data/\
　　　|__ model/\
　　　　　　|__ CustomSpinnerAdapter.kt\
　　　　　　|__ Place.kt\
　　　　　　|__ WeatherDetail.kt\
　　　|__ repository/\
　　　　　　|__ WeatherDetailRepository.kt\
　　　　　　|__ WeatherRepository.kt\
|__ presenter/\
　　　|__ MainContract.kt\
　　　|__ MainPresenter.kt\
　　　|__ WeatherDetailContract.kt\
　　　|__ WeatherDetailPresenter.kt\
|__ ui/\
　　　|__ MainActivity.kt\
　　　|__ WeatherDetailActivity.kt\
|__ util/\
　　　|__ Constants.kt\