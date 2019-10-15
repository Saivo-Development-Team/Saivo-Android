package com.saivo.recommendo.provider.location

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest

class LocationObserver(
    lifecycleOwner: LifecycleOwner,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val locationCallback: LocationCallback
) : LifecycleObserver {
    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    private val locationRequest = LocationRequest().apply {
        interval = 60000
        fastestInterval = 6000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}