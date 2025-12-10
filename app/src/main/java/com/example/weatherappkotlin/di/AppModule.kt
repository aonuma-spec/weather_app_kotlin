package com.example.weatherappkotlin.di

import com.example.weatherappkotlin.data.repository.WeatherApiService
import com.example.weatherappkotlin.data.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /**
     * Retrofit用のDIを設定
     */
    @Singleton //アプリ全体で1つのインスタンスを使う設定
    @Provides
    fun providerRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    /**
     * WeatherApiService用のDIを設定
     */
    @Singleton
    @Provides
    //WeatherRepository のコンストラクタが WeatherApiService を要求した時に
    //Hilt がこの provideWeatherApiService() を自動で呼んでインスタンスを渡す
    fun provideWeatherApiService(retrofit: Retrofit): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }

    /**
     * provideWeatherRepository用のDIを設定
     */
    @Singleton
    @Provides
    fun provideWeatherRepository(api: WeatherApiService): WeatherRepository {
        return WeatherRepository(api)
    }
    class WeatherRepository (
        private val api: WeatherApiService //DI
    )
}