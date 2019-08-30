package com.pavelprymak.openweatherwellcom.presentation.viewModels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pavelprymak.openweatherwellcom.data.db.WeatherEntity
import com.pavelprymak.openweatherwellcom.data.db.repo.DbRepo
import com.pavelprymak.openweatherwellcom.data.network.pojos.weaterByCityName.WeatherByCityResponse
import com.pavelprymak.openweatherwellcom.data.network.repo.NetworkRepo
import com.pavelprymak.openweatherwellcom.presentation.common.SimpleViewModel
import com.pavelprymak.openweatherwellcom.utils.convertToWeatherEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WeatherViewModel(
    private val mNetworkRepo: NetworkRepo, private val mDbRepo: DbRepo
) : SimpleViewModel() {
    private val mWeatherData = MutableLiveData<WeatherEntity>()
    private val mLoadingData = MutableLiveData<Boolean>()
    private val mErrorData = MutableLiveData<Throwable>()
    private var mWeatherInfoDbData: LiveData<WeatherEntity>? = null
    private var mCurrentCityName: String? = null

    fun prepareWeatherData(cityName: String) {
        if (mWeatherData.value == null || cityName != mCurrentCityName) {
            mCurrentCityName = cityName
            mLoadingData.value = true
            mNetworkRepo.getWeatherByCity(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { weatherResponse: WeatherByCityResponse -> weatherResponse.convertToWeatherEntity() }
                .subscribe({ result ->
                    mWeatherData.value = result
                    mDbRepo.clearTableAndInsertWeather(result)
                    mLoadingData.value = false
                }, {
                    mLoadingData.value = false
                    mErrorData.value = it
                })
                .untilDestroy()
        }
    }

    fun getWeatherInfoFromDb(): LiveData<WeatherEntity>? {
        if (mWeatherInfoDbData == null || mWeatherInfoDbData?.value == null) {
            mWeatherInfoDbData = mDbRepo.getWeather()
        }
        return mWeatherInfoDbData
    }

    fun getLoadingData(): LiveData<Boolean> {
        return mLoadingData
    }

    fun getErrorsData(): LiveData<Throwable> {
        return mErrorData
    }

    fun cleanWeatherInfoInDb() {
        mDbRepo.clearTable()
    }

    fun removeObservers(lifecycleOwner: LifecycleOwner) {
        mWeatherData.removeObservers(lifecycleOwner)
        mLoadingData.removeObservers(lifecycleOwner)
        mErrorData.removeObservers(lifecycleOwner)
        mWeatherInfoDbData?.removeObservers(lifecycleOwner)
    }
}