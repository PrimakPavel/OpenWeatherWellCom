package com.pavelprymak.openweatherwellcom.presentation.screens

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.pavelprymak.openweatherwellcom.R
import com.pavelprymak.openweatherwellcom.presentation.viewModels.WeatherViewModel
import com.pavelprymak.openweatherwellcom.utils.SettingsPreferenceManager
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mNavController = Navigation.findNavController(view)
        val cityName = mSettingsPreferenceManager.cityName
        activity?.title = cityName
        cityName?.let { cN ->
            mWeatherInfoViewModel.prepareWeatherData(cN)
        }
        mWeatherInfoViewModel.getWeatherInfoFromDb()?.observe(this, Observer { weatherInfo ->
            if (weatherInfo != null) {
                Toast.makeText(context, weatherInfo.toString(), Toast.LENGTH_LONG).show()
            } else {
                //TODO show empty info
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mWeatherInfoViewModel.removeObservers(this)
    }
}
