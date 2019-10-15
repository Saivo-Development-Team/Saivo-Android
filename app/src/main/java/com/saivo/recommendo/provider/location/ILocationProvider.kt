package com.saivo.recommendo.provider.location

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient

interface ILocationProvider {
    suspend fun getDeviceLocation(): Location?
    fun getLocationProviderClient(): FusedLocationProviderClient
}