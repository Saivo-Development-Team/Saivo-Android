package com.saivo.recommendo.view.fragment.pages


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.saivo.recommendo.R
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
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

}
