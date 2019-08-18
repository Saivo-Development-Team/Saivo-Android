package com.saivo.recommendo.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView
import com.saivo.recommendo.R
import com.mapbox.mapboxsdk.maps.Style
import com.saivo.recommendo.util.MAPBOX_KEY
import com.saivo.recommendo.view.viewmodel.UserViewModel
import com.saivo.recommendo.view.viewmodel.UserViewModelFactory
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class MainFragment : CoroutineFragment(), KodeinAware {
    override val kodein by closestKodein()
    private val userViewModelFactory: UserViewModelFactory by instance()
    private lateinit var userViewModel: UserViewModel

    private var mapView: MapView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Mapbox.getInstance(this@MainFragment.context!!, MAPBOX_KEY)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel = ViewModelProvider(this@MainFragment, userViewModelFactory).get(UserViewModel::class.java)
        mapView = view.findViewById(R.id.MainMapView)
        mapView!!.onCreate(savedInstanceState)
        mapView!!.getMapAsync { mapboxMap -> mapboxMap.setStyle(Style.MAPBOX_STREETS) }
        loadUserData()
    }

    private fun loadUserData() = launch {
        val userData = userViewModel.userData.await()
        userData.observe(this@MainFragment, Observer {
            if (it == null) return@Observer
//            user_name_text.text = it.email
        })
    }
}
