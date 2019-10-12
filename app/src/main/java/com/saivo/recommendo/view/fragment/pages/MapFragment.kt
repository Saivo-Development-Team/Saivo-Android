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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.saivo.recommendo.R
import com.saivo.recommendo.provider.ILocationProvider
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
class MapFragment : CoroutineFragment(), KodeinAware, OnMapReadyCallback {
    override val kodein: Kodein by closestKodein()
    private lateinit var userViewModel: IUserViewModel
    private val deviceLocation: LiveData<Location>
        get() = _deviceLocation
    private var _deviceLocation = MutableLiveData<Location>()
    private val locationProvider: ILocationProvider by instance()
    private val viewModelFactory: ViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        userViewModel = ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)

        launch {
            locationProvider.getDeviceLocation().apply {
                _deviceLocation.postValue(this)
                toastMessage(this@MapFragment.requireContext(), this.toString())
            }
            userViewModel.userData.await().observe(
                this@MapFragment, Observer {
                    firstname_text_view.text = it.firstname
                    lastname_text_view.text = it.lastname
                }
            )
        }

        val map = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        map.getMapAsync(this)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        deviceLocation.observe(this, Observer {
            val location = LatLng(it.latitude, it.longitude)
            googleMap.addMarker(MarkerOptions().position(location))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
        })
    }

}
