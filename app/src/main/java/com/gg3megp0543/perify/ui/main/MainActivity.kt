package com.gg3megp0543.perify.ui.main

import android.content.pm.PackageManager
import android.database.MatrixCursor
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.SearchView
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
import com.google.android.gms.maps.model.LatLng
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
    private var isMapReady = false
    private val cityMap = mapOf(
        "Jakarta" to "ID-JK",
        "Palembang" to "ID-PB",
        "Yogyakrta" to "ID-YO"
    )
    private val suggestions = cityMap.keys.toList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel = ViewModelProvider(this, MainViewModelFactory())[MainViewModel::class.java]

        val columns = arrayOf("_id", "city_name")
        val cursor = MatrixCursor(columns)
        suggestions.forEachIndexed { index, suggestion ->
            cursor.addRow(arrayOf(index, suggestion))
        }

        val adapter = android.widget.SimpleCursorAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            cursor,
            arrayOf("city_name"),
            intArrayOf(android.R.id.text1),
            0
        )
        binding.svProvince.suggestionsAdapter = adapter

        binding.svProvince.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.svProvince.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    val filteredCursor = MatrixCursor(columns)
                    suggestions.forEachIndexed { index, suggestion ->
                        if (suggestion.contains(newText, ignoreCase = true)) {
                            filteredCursor.addRow(arrayOf(index, suggestion))
                        }
                    }
                    adapter.changeCursor(filteredCursor)
                }
                return true
            }
        })

        binding.svProvince.setOnSuggestionListener(object : SearchView.OnSuggestionListener,
            androidx.appcompat.widget.SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                val cursor = adapter.getItem(position) as MatrixCursor
                val selectedCity = cursor.getString(cursor.getColumnIndex("city_name"))
                val cityCode = cityMap[selectedCity]

                binding.svProvince.setQuery(selectedCity, true)

                viewModel.showDisasterReport(admin = cityCode)
                return true
            }
        })

        viewModel.loadingState.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.propertiesWithCoordinates.observe(this) { propertiesWithCoordinates ->
            propertiesWithCoordinates?.let {
                setData(it)
                if (isMapReady) {
                    locationMarker()
                }
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

    private fun setData(propertiesWithCoordinates: List<Pair<Properties, List<Any?>>>) {
        val adapter = DisasterAdapter()
        adapter.submitList(propertiesWithCoordinates.map { it.first })
        binding.rvDisaster.adapter = adapter
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        isMapReady = true

        mMap.let {
            it.uiSettings.isZoomControlsEnabled = true
            it.uiSettings.isIndoorLevelPickerEnabled = true
            it.uiSettings.isCompassEnabled = true
            it.uiSettings.isMapToolbarEnabled = true
        }

        getMyLocation()
        locationMarker()
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

    private fun locationMarker() {
        mMap.clear()

        viewModel.propertiesWithCoordinates.observe(this) { propertiesWithCoordinates ->
            propertiesWithCoordinates?.forEach { (properties, coordinates) ->
                if (coordinates.size >= 2) {
                    val latLng = LatLng(coordinates[1] as Double, coordinates[0] as Double)
                    val addressName =
                        getAddressName(coordinates[1] as Double, coordinates[0] as Double)
                    mMap.addMarker(
                        MarkerOptions().position(latLng).title(properties.disasterType)
                            .snippet(addressName)
                    )
                    boundsBuilder.include(latLng)
                }
            }

            if (propertiesWithCoordinates?.isNotEmpty() == true) {
                val bounds: LatLngBounds = boundsBuilder.build()
                mMap.animateCamera(
                    CameraUpdateFactory.newLatLngBounds(
                        bounds,
                        resources.displayMetrics.widthPixels,
                        resources.displayMetrics.heightPixels,
                        300
                    )
                )
            }
        }
    }

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