package com.saivo.recommendo.view.viewmodel

import android.Manifest
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.saivo.recommendo.R
import com.saivo.recommendo.provider.location.LocationProvider
import com.saivo.recommendo.util.helpers.REQUEST_DEVICE_GPS_PERMISSION
import com.saivo.recommendo.util.helpers.REQUEST_FINE_LOCATION_PERMISSION
import com.saivo.recommendo.view.objects.AlertDialog


class MainViewModel(private val context: Context) : ViewModel() {
    private var activity: AppCompatActivity? = null
    private lateinit var locationProvider: LocationProvider

    fun setActivity(activity: AppCompatActivity) {
        this.activity = activity
    }

    fun checkLocationServices() {
        initProvider()
        if (activity != null) initCheckLocation()
    }

    private fun initCheckLocation() {
        if (locationProvider.locationEnabled(runFalse = { buildAlertMessageNoGps() })) {
            getLocationPermission()
        }
    }

    fun getLocationPermission() {
        activity?.requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_FINE_LOCATION_PERMISSION
        )
    }

    private fun initProvider() {
        locationProvider = LocationProvider(context)
    }

    fun getLocationProvider(): LocationProvider {
        return locationProvider
    }

    private fun buildAlertMessageNoGps() {
        activity?.let { _activity ->
            AlertDialog.setDialog(_activity).apply {
                title.apply {
                    text = resources.getString(R.string.enable_location)
                    setTextColor(resources.getColor(R.color.black, null))
                }

                image.apply {
                    setImageResource(R.drawable.ic_location_off)
                    visibility = View.VISIBLE
                }

                button.apply {
                    text = resources.getString(R.string.settings)
                    visibility = View.VISIBLE
                    setOnClickListener {
                        val enableGpsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        _activity.startActivityForResult(
                            enableGpsIntent,
                            REQUEST_DEVICE_GPS_PERMISSION
                        )
                        dialog.dismiss()
                    }
                }

                message.apply {
                    text = resources.getString(R.string.turn_on_location)
                }

                close.setOnClickListener {
                    dialog.dismiss()
                }

                dialog.show()
            }
        }
    }
}