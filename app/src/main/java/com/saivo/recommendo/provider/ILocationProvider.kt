package com.saivo.recommendo.provider

import android.location.Location

interface ILocationProvider {
    suspend fun getDeviceLocation(): Location?
}