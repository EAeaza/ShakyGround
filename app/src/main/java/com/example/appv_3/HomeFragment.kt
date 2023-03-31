package com.example.appv_3

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mapView: MapView
    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object{
        private const val LOCATION_REQUEST_CODE = 1
    }


    private lateinit var floatingButton: FloatingActionButton
    private lateinit var floatingButton1: FloatingActionButton
    private lateinit var floatingButton2: FloatingActionButton
    private lateinit var locationButton: FloatingActionButton

    private lateinit var activ: MainActivity


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        mapView = rootView.findViewById(R.id.map_View)
        mapView.onCreate(savedInstanceState)

        floatingButton = rootView.findViewById(R.id.floating_button)
        floatingButton1 = rootView.findViewById(R.id.floating_button_1)
        floatingButton2 = rootView.findViewById(R.id.floating_button_2)
        locationButton = rootView.findViewById(R.id.floating_button_gps)

        floatingButton.setOnClickListener {
            if (floatingButton1.visibility == View.GONE) {
                floatingButton1.visibility = View.VISIBLE
                floatingButton2.visibility = View.VISIBLE
            } else {
                floatingButton1.visibility = View.GONE
                floatingButton2.visibility = View.GONE
            }
        }

        locationButton.setOnClickListener {
            mapView.getMapAsync { googleMap ->
                // Disable default "My Location" button
                googleMap.uiSettings.isMyLocationButtonEnabled = false}
            if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activ)
                == ConnectionResult.SUCCESS) {
                view?.findViewById<MapView>(R.id.map_View)?.getMapAsync { googleMap ->
                    if (ActivityCompat.checkSelfPermission(
                            activ,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            activ,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return@getMapAsync
                    }

                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        if (location != null) {
                            val latLng = LatLng(location.latitude, location.longitude)
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                        }
                    }
                    googleMap.isMyLocationEnabled = true
                }
            } else {
                Toast.makeText(activity, "Google Play Services not available", Toast.LENGTH_SHORT).show()
            }
        }

        activ = activity as MainActivity
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activ)

        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        floatingButton1.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }
        floatingButton2.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_itemFragment)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)
        setUpMap()

    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(activ, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activ, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
            return
        }
        mMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(activ) { location ->

            if (location != null) {
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLong)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 12f) )
            }
        }
    }

    private fun placeMarkerOnMap (currentLatLng: LatLng) {
        val markerOptions = MarkerOptions().position(currentLatLng)
        markerOptions.title("$currentLatLng")
        mMap.addMarker(markerOptions)
    }


    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onMarkerClick(p0: Marker) = false

}