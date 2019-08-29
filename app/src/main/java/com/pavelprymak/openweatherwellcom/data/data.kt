package com.pavelprymak.openweatherwellcom.data

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.pavelprymak.openweatherwellcom.data.db.AppDatabase
import com.pavelprymak.openweatherwellcom.data.db.AppExecutors
import com.pavelprymak.openweatherwellcom.data.db.repo.DbRepo
import com.pavelprymak.openweatherwellcom.data.db.repo.DbRepoImpl
import com.pavelprymak.openweatherwellcom.data.network.Constants
import com.pavelprymak.openweatherwellcom.data.network.MyCookieJar
import com.pavelprymak.openweatherwellcom.data.network.WeatherApi
import com.pavelprymak.openweatherwellcom.data.network.repo.NetworkRepo
import com.pavelprymak.openweatherwellcom.data.network.repo.NetworkRepoImpl
import com.pavelprymak.openweatherwellcom.utils.SettingsPreferenceManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

val data = module {
    single { createMoshi() }
    single { createSettingPref(get()) }
    single { createOkHttpClient() }
    single { createAppExecutor() }
    single { createAppDataBase(get()) }
    single { createWebService<WeatherApi>(get(), get(), Constants.BASE_URL) }
    single<NetworkRepo> { NetworkRepoImpl(get()) }
    single<DbRepo> { DbRepoImpl(get(), get()) }
}

private inline fun <reified T> createWebService(okHttpClient: OkHttpClient, moshi: Moshi, url: String): T {
    val retrofit = baseRetrofitBuilder(url, okHttpClient, moshi)
        .build()
    return retrofit.create(T::class.java)
}

private fun createMoshi(): Moshi {
    return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
}

private fun createOkHttpClient(): OkHttpClient {
    val httpClient = OkHttpClient.Builder()
    httpClient.cookieJar(MyCookieJar())
    httpClient.connectTimeout(30, TimeUnit.SECONDS)
    httpClient.writeTimeout(30, TimeUnit.SECONDS)
    httpClient.readTimeout(30, TimeUnit.SECONDS)
    httpClient.addNetworkInterceptor(StethoInterceptor())
    val logging = HttpLoggingInterceptor()
    logging.level = HttpLoggingInterceptor.Level.BODY
    httpClient.addInterceptor(logging)
    return httpClient.build()
}

private fun baseRetrofitBuilder(url: String, okHttpClient: OkHttpClient, moshi: Moshi): Retrofit.Builder {
    return Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
}

private fun createAppExecutor(): AppExecutors {
    return AppExecutors()
}

private fun createAppDataBase(context: Context): AppDatabase {
    return AppDatabase.getInstance(context)
}

private fun createSettingPref(context: Context): SettingsPreferenceManager {
    return SettingsPreferenceManager(context)
}
