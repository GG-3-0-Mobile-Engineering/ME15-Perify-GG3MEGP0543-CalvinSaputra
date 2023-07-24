package com.gg3megp0543.perify.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.gg3megp0543.perify.R
import com.gg3megp0543.perify.databinding.ActivityMainBinding
import com.gg3megp0543.perify.logic.model.Properties
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMainBinding
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
        disasterBottomSheetBehavior.isHideable = false
        disasterBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    fun setData(listProperties: List<Properties>) {
        val adapter = DisasterAdapter()
        adapter.submitList(listProperties)
        binding.rvDisaster.adapter = adapter
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}