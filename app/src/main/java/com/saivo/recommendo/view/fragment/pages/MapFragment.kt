package com.saivo.recommendo.view.fragment.pages

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.saivo.recommendo.R
import com.saivo.recommendo.provider.location.ILocationProvider
import com.saivo.recommendo.provider.location.LocationObserver
import com.saivo.recommendo.util.helpers.toastMessage
import com.saivo.recommendo.view.fragment.CoroutineFragment
import com.saivo.recommendo.view.viewmodel.ViewModelFactory
import com.saivo.recommendo.view.viewmodel.user.IUserViewModel
import com.saivo.recommendo.view.viewmodel.user.UserViewModel
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

/**
 * A simple [Fragment] subclass.
 */
class MapFragment : CoroutineFragment(), KodeinAware {
    override val kodein: Kodein by closestKodein()
    private val deviceLocation: LiveData<Location>
        get() = _deviceLocation
    private lateinit var userViewModel: IUserViewModel
    private var _deviceLocation = MutableLiveData<Location>()
    private val viewModelFactory: ViewModelFactory by instance()
    private val locationProvider: ILocationProvider by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        LocationObserver(
            this, locationProvider.getLocationProviderClient(), locationCallBack()
        )
        userViewModel = ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)

        launch {
            userViewModel.userData.await().observe(
                this@MapFragment, Observer {
                    firstname_text_view.text = it.firstname
                    lastname_text_view.text = it.lastname
                }
            )
        }

        val map = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        map.getMapAsync { googleMap ->
            deviceLocation.observe(this, Observer {
                if (it == null) return@Observer
                toastMessage(
                    this.requireContext(),
                    "Latitude[${it.latitude}]\nLongitude[${it.longitude}]"
                )
                val location = LatLng(it.latitude, it.longitude)
                googleMap.addMarker(MarkerOptions().position(location))
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15F))
            })

            googleMap.animateCamera(CameraUpdateFactory.zoomIn())
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun locationCallBack(): LocationCallback {
        return object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                super.onLocationResult(p0)
                _deviceLocation.postValue(p0?.lastLocation)
            }
        }
    }

}
