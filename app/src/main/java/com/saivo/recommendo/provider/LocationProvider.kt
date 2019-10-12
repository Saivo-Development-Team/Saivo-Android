package com.saivo.recommendo.provider

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Task
import com.saivo.recommendo.util.exception.NeedLocationPermission
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

class LocationProvider(private val context: Context) : PreferenceProvider(context),
    ILocationProvider {
    private fun gotLocationPermission(): Boolean {
        ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION).let {
            return it == PackageManager.PERMISSION_GRANTED
        }
    }

    override suspend fun getDeviceLocation(): Location? {
        if (gotLocationPermission())
            return FusedLocationProviderClient(context).lastLocation.asDeferredAsync().await()
        else throw NeedLocationPermission()
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
