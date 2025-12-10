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
weatherappkotlin\
├──&nbsp;WeatherApplication.kt\
├──&nbsp;data\
│&nbsp;&nbsp;&nbsp;├──&nbsp;model\
│&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;├──&nbsp;CustomSpinnerAdapterModel.kt\
│&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;├──&nbsp;PlaceModel.kt\
│&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;├──&nbsp;WeatherApiResponseModel.kt\
│&nbsp;&nbsp;&nbsp;│&nbsp;&nbsp;&nbsp;└──&nbsp;WeatherDetailModel.kt\
│&nbsp;&nbsp;&nbsp;└──&nbsp;repository\
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├──&nbsp;WeatherApiService.kt\
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├──&nbsp;WeatherDetailRepository.kt\
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;├──&nbsp;WeatherRepository.kt\
│&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└──&nbsp;WeatherResponseRepository.kt\
├──&nbsp;di\
│&nbsp;&nbsp;&nbsp;└──&nbsp;AppModule.kt\
├──&nbsp;presenter\
│&nbsp;&nbsp;&nbsp;├──&nbsp;MainContract.kt\
│&nbsp;&nbsp;&nbsp;├──&nbsp;MainPresenter.kt\
│&nbsp;&nbsp;&nbsp;├──&nbsp;WeatherDetailContract.kt\
│&nbsp;&nbsp;&nbsp;└──&nbsp;WeatherDetailPresenter.kt\
└──&nbsp;ui\
&nbsp;&nbsp;&nbsp;├──&nbsp;MainActivity.kt\
&nbsp;&nbsp;&nbsp;├──&nbsp;MainFragment.kt\
&nbsp;&nbsp;&nbsp;└──&nbsp;WeatherDetailFragment.kt\