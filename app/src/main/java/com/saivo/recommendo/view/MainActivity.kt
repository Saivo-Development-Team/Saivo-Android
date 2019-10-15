package com.saivo.recommendo.view

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.saivo.recommendo.R
import com.saivo.recommendo.util.helpers.REQUEST_DEVICE_GPS_PERMISSION
import com.saivo.recommendo.util.helpers.REQUEST_FINE_LOCATION_PERMISSION
import com.saivo.recommendo.view.viewmodel.MainViewModel
import com.saivo.recommendo.view.viewmodel.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance


class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: ViewModelFactory by instance()
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        mainViewModel.apply {
            setActivity(this@MainActivity)
            checkLocationServices()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_FINE_LOCATION_PERMISSION -> {
                grantResults.let {
                    if (it.isNotEmpty() && it[0] == PackageManager.PERMISSION_GRANTED) {
                        mainViewModel.getLocationProvider().isLocationPermissionGranted = true
                    }
                }
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_DEVICE_GPS_PERMISSION -> {
                (getSystemService(Context.LOCATION_SERVICE) as LocationManager).apply {
                    if (isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        mainViewModel.getLocationProvider().isDeviceGPSEnabled = true
                        mainViewModel.getLocationPermission()
                    }
                }
            }
        }
    }
}
