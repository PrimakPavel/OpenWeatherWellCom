package com.pavelprymak.openweatherwellcom.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.pavelprymak.openweatherwellcom.R
import com.pavelprymak.openweatherwellcom.utils.SettingsPreferenceManager
import com.pavelprymak.openweatherwellcom.utils.showInputMethod
import kotlinx.android.synthetic.main.fragment_city_input.*
import org.koin.android.ext.android.inject

const val MIN_CITY_LENGTH = 3

class CityInputFragment : Fragment() {
    lateinit var mNavController: NavController
    private val mSettingsPreferenceManager: SettingsPreferenceManager by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_city_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mNavController = Navigation.findNavController(view)
        if (mSettingsPreferenceManager.cityName?.isNotEmpty() == true) {
            mNavController.navigate(R.id.weatherInfoFragment)
            return
        }
        activity?.setTitle(R.string.select_city_title)
        buttonOk.setOnClickListener {
            val cityName = etCityName.text.toString()
            if (cityName.length >= MIN_CITY_LENGTH) {
                mSettingsPreferenceManager.saveCityName(cityName)
                mNavController.navigate(R.id.weatherInfoFragment)
            } else {
                etCityName.error = getString(R.string.error_incorrect_input)
            }
        }
        if (etCityName.requestFocus()) {
            activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
            context?.showInputMethod(etCityName)
        }
    }
}
