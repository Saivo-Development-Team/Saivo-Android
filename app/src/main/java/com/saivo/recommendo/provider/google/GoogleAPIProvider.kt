package com.saivo.recommendo.provider.google

import android.content.Context
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient

class GoogleAPIProvider {
    companion object {

        fun places(context: Context): PlacesClient {
            return Places.createClient(context)
        }

    }
}