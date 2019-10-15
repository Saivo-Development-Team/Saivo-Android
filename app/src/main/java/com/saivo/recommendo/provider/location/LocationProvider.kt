package com.saivo.recommendo.provider.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Task
import com.saivo.recommendo.provider.PreferenceProvider
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

class LocationProvider(private val context: Context) : PreferenceProvider(context),
    ILocationProvider {
    override fun getLocationProviderClient(): FusedLocationProviderClient {
        return FusedLocationProviderClient(context)
    }

    var isDeviceGPSEnabled: Boolean = false
    var isLocationPermissionGranted: Boolean = false

    private fun gotLocationPermission(): Boolean {
        checkLocationPermission().let {
            return (it == PackageManager.PERMISSION_GRANTED).apply {
                if (this) {
                    isLocationPermissionGranted = true
                }
            }
        }
    }

    override suspend fun getDeviceLocation(): Location? {
        return if (gotLocationPermission())
            getLocationProviderClient().lastLocation.asDeferredAsync().await()
        else null
    }

    private fun checkLocationPermission(): Int {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
    }


    fun locationEnabled(
        runFalse: (() -> Unit?)? = null,
        runTrue: (() -> Unit?)? = null
    ): Boolean {
        (context.getSystemService(Context.LOCATION_SERVICE) as LocationManager).apply {
            if (isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                runTrue?.let { it() }
                return true
            }
            runFalse?.let { it() }
            return false
        }
    }

    private fun <T> Task<T>.asDeferredAsync(): Deferred<T> {
        val deferred = CompletableDeferred<T>()
        this.addOnSuccessListener { result ->
            deferred.complete(result)
        }

        this.addOnFailureListener { exception ->
            deferred.completeExceptionally(exception)
        }

        return deferred
    }

}
