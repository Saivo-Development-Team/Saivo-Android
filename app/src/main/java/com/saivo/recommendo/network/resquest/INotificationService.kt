package com.saivo.recommendo.network.resquest

import android.icu.text.CaseMap
import com.saivo.recommendo.data.model.domain.Notification
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface INotificationService {

    @GET ("/user/recommendations/activities/{title}")
    fun getRecommendationOfActivity(@Path("title") title: String): Deferred<Notification>

    @GET("/user/{email}/recommendations/activities")
    fun getNotificationForUserAsync(@Path("email") email: String): Deferred <List<Notification>>
}