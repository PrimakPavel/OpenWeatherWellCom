package com.pavelprymak.openweatherwellcom.presentation.screens

import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.pavelprymak.openweatherwellcom.R
import com.pavelprymak.openweatherwellcom.data.db.WeatherEntity
import com.pavelprymak.openweatherwellcom.presentation.viewModels.WeatherViewModel
import com.pavelprymak.openweatherwellcom.utils.SettingsPreferenceManager
import com.pavelprymak.openweatherwellcom.utils.toCelsius
import kotlinx.android.synthetic.main.fragment_weather_info.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class WeatherInfoFragment : Fragment() {
    private lateinit var mNavController: NavController
    private val mWeatherInfoViewModel: WeatherViewModel by viewModel()
    private val mSettingsPreferenceManager: SettingsPreferenceManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.setting_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.actionSettings -> {
                mSettingsPreferenceManager.saveCityName(null)
                mWeatherInfoViewModel.cleanWeatherInfoInDb()
                mNavController.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_info, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mWeatherInfoViewModel.removeObservers(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mNavController = Navigation.findNavController(view)
        mSettingsPreferenceManager.cityName?.let { cN ->
            mWeatherInfoViewModel.prepareWeatherData(cN)
            activity?.title = cN
        }
        mWeatherInfoViewModel.getLoadingData().observe(this, Observer { isLoading ->
            loadingShow(isLoading)
        })
        mWeatherInfoViewModel.getWeatherInfoFromDb()?.observe(this, Observer { weatherInfo ->
            showData(weatherInfo)
        })
    }

    private fun loadingShow(isLoading: Boolean?) {
        if (isLoading == true) pb.visibility = VISIBLE else pb.visibility = GONE
    }

    private fun showData(weatherInfo: WeatherEntity?) {
        if (weatherInfo != null) {
            groupWeatherData.visibility = VISIBLE
            tvErrorMessage.visibility = GONE
            weatherInfo.tempMax?.let { tempMax -> tvTempMax.text = toCelsius(tempMax) }
            weatherInfo.temp?.let { temp -> tvTemp.text = toCelsius(temp) }
            weatherInfo.tempMin?.let { tempMin -> tvTempMin.text = toCelsius(tempMin) }
            weatherInfo.humidity?.toString()?.let { humidity ->
                tvHumidity.text = getString(R.string.humidity_label, humidity)
            }
            weatherInfo.pressure?.toString()?.let { pressure ->
                tvPressure.text = getString(R.string.pressure_label, pressure)
            }
            weatherInfo.windDeg?.toString()?.let { windDeg ->
                tvWindDegree.text = getString(R.string.wind_degree_label, windDeg)
            }
            weatherInfo.windSpeed?.toString()?.let { windSpeed ->
                tvWindSpeed.text = getString(R.string.wind_speed_label, windSpeed)
            }
        } else {
            groupWeatherData.visibility = GONE
            tvErrorMessage.setText(R.string.no_data)
            tvErrorMessage.visibility = VISIBLE
        }
    }
}
