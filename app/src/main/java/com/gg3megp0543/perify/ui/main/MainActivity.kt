package com.gg3megp0543.perify.ui.main

import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gg3megp0543.perify.R
import com.gg3megp0543.perify.databinding.ActivityMainBinding
import com.gg3megp0543.perify.logic.model.Properties
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMainBinding
    private val boundsBuilder = LatLngBounds.Builder()
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel = ViewModelProvider(this, MainViewModelFactory())[MainViewModel::class.java]

        viewModel.properties.observe(this) { properties ->
            properties?.let {
                setData(it)
            }
        }

        binding.rvDisaster.layoutManager = LinearLayoutManager(this)

        val disasterBottomSheet = findViewById<FrameLayout>(R.id.bottom_sheet)
        val disasterBottomSheetBehavior = BottomSheetBehavior.from(disasterBottomSheet)
        disasterBottomSheetBehavior.let {
            it.isHideable = false
            it.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun setData(listProperties: List<Properties>) {
        val adapter = DisasterAdapter()
        adapter.submitList(listProperties)
        binding.rvDisaster.adapter = adapter
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.let {
            it.uiSettings.isZoomControlsEnabled = true
            it.uiSettings.isIndoorLevelPickerEnabled = true
            it.uiSettings.isCompassEnabled = true
            it.uiSettings.isMapToolbarEnabled = true
        }

        getMyLocation()
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { permitted: Boolean ->
        if (permitted) {
            getMyLocation()
        }
    }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

//    private fun locationMarker() {
//        viewModel.properties.observe(this) { properties ->
//            properties?.forEach { item ->
//                val latLng = story.lat?.let { story.lon?.let { it1 -> LatLng(it, it1) } }
//                val addressName = getAddressName(item)
//                mMap.addMarker(
//                    MarkerOptions().position(latLng!!).title(story.name).snippet(addressName)
//                )
//                boundsBuilder.include(latLng)
//            }
//
//            if (properties.isNotEmpty()) {
//                val bounds: LatLngBounds = boundsBuilder.build()
//                mMap.animateCamera(
//                    CameraUpdateFactory.newLatLngBounds(
//                        bounds,
//                        resources.displayMetrics.widthPixels,
//                        resources.displayMetrics.heightPixels,
//                        300
//                    )
//                )
//            }
//        }
//    }

    @Suppress("DEPRECATION")
    private fun getAddressName(lat: Double, lon: Double): String? {
        var addressName: String? = null
        val geocoder = Geocoder(this@MainActivity, Locale.getDefault())
        try {
            val list = geocoder.getFromLocation(lat, lon, 1)
            if (list != null && list.size != 0) {
                addressName = list[0].getAddressLine(0)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return addressName
    }
}