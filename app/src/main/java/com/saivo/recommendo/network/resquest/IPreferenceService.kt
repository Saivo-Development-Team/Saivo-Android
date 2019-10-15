package com.saivo.recommendo.network.resquest

import com.saivo.recommendo.data.model.domain.Preference
import com.saivo.recommendo.data.objects.Response
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IPreferenceService {
    @GET("/preference/{Id}")
    fun getPreferenceAsync(@Path("Id") Id: String): Deferred<Response>

    @GET("/preference/all/{email}")
    fun getPreferencesAsync(@Path("email") email: String): Deferred<Response>

    @POST("/preference/save/{email}")
    fun savePreferenceAsync(@Path("email") email: String, @Body preference: Preference): Deferred<Response>
}