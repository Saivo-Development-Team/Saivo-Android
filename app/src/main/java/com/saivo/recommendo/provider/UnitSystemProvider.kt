package com.saivo.recommendo.provider

import android.content.Context

class UnitSystemProvider(context: Context) : PreferenceProvider(context), IUnitSystemProvider {
    override fun getUnitSystem(): String? {
        return preferenceSettings.getString("UNIT_SYSTEM", "Metric")
    }
}