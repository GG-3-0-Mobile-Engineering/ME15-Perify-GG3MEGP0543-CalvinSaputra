package com.gg3megp0543.perify.core.presenter.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.MatrixCursor
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.gg3megp0543.perify.R
import com.gg3megp0543.perify.databinding.ActivityMainBinding
import com.gg3megp0543.perify.core.utils.DisasterEnum
import com.gg3megp0543.perify.core.utils.KEY_DEPTH
import com.gg3megp0543.perify.core.utils.KEY_TITLE
import com.gg3megp0543.perify.core.utils.ProvinceHelper
import com.gg3megp0543.perify.notification.FloodNotificationWorker
import com.gg3megp0543.perify.core.data.source.remote.response.Properties
import com.gg3megp0543.perify.core.presenter.setting.SettingsActivity
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
    private val suggestions = ProvinceHelper.provinceMap.keys.toList()
    private var selectedProvince: String? = null
    private var selectedDisaster: String? = null
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var notifViewModel: DummyNotificationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel = ViewModelProvider(this, MainViewModelFactory())[MainViewModel::class.java]

        sharedPreferences =
            getSharedPreferences(getString(R.string.dummy_notif_pref), Context.MODE_PRIVATE)
        notifViewModel = ViewModelProvider(this)[DummyNotificationViewModel::class.java]

        if (!notifViewModel.isNotificationShown) {
            showNotification()
            notifViewModel.markNotificationAsShown()
        }

        val columns = arrayOf("_id", "prov_name")
        val cursor = MatrixCursor(columns)
        suggestions.forEachIndexed { index, suggestion ->
            cursor.addRow(arrayOf(index, suggestion))
        }

        val adapter = android.widget.SimpleCursorAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            cursor,
            arrayOf("prov_name"),
            intArrayOf(android.R.id.text1),
            0
        )
        binding.svProvince.suggestionsAdapter = adapter

        binding.svProvince.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.svProvince.clearFocus()

                val provinceCode = ProvinceHelper.provinceMap.entries.find { (province, _) ->
                    province.equals(query, ignoreCase = true)
                }?.value

                if (provinceCode != null) {
                    selectedProvince = provinceCode
                    viewModel.showDisasterReport(
                        admin = selectedProvince,
                        disaster = selectedDisaster
                    )
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        R.string.unknown_province,
                        Toast.LENGTH_SHORT
                    ).show()
                }
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
                val selectedProv = cursor.getString(cursor.getColumnIndex("prov_name"))
                val provCode = ProvinceHelper.provinceMap[selectedProv]

                binding.svProvince.setQuery(selectedProv, true)
                selectedProvince = provCode

                viewModel.showDisasterReport(admin = selectedProvince, disaster = selectedDisaster)
                return true
            }
        })

        binding.chipFlood.setOnClickListener {
            selectedDisaster = DisasterEnum.FLOOD.disaster
            viewModel.showDisasterReport(admin = selectedProvince, disaster = selectedDisaster)
        }

        binding.chipEarthquake.setOnClickListener {
            selectedDisaster = DisasterEnum.EARTHQUAKE.disaster
            viewModel.showDisasterReport(admin = selectedProvince, disaster = selectedDisaster)
        }

        binding.chipFire.setOnClickListener {
            selectedDisaster = DisasterEnum.FIRE.disaster
            viewModel.showDisasterReport(admin = selectedProvince, disaster = selectedDisaster)
        }

        binding.chipHaze.setOnClickListener {
            selectedDisaster = DisasterEnum.HAZE.disaster
            viewModel.showDisasterReport(admin = selectedProvince, disaster = selectedDisaster)
        }

        binding.chipWind.setOnClickListener {
            selectedDisaster = DisasterEnum.WIND.disaster
            viewModel.showDisasterReport(admin = selectedProvince, disaster = selectedDisaster)
        }

        binding.chipVolcano.setOnClickListener {
            selectedDisaster = DisasterEnum.VOLCANO.disaster
            viewModel.showDisasterReport(admin = selectedProvince, disaster = selectedDisaster)
        }

        binding.ivSetting.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        viewModel.loadingState.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.tvDisasterHeader.visibility = if (isLoading) View.GONE else View.VISIBLE
        }

        viewModel.propertiesWithCoordinates.observe(this) { propertiesWithCoordinates ->
            propertiesWithCoordinates?.let {
                setData(it)

                if (isMapReady) {
                    locationMarker()
                }
            }
        }

        viewModel.error.observe(this) { errorMessage ->
            Toast.makeText(
                this@MainActivity,
                errorMessage.message.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.rvDisaster.layoutManager = LinearLayoutManager(this)

        val disasterBottomSheet = findViewById<FrameLayout>(R.id.bottom_sheet)
        val disasterBottomSheetBehavior = BottomSheetBehavior.from(disasterBottomSheet)
        disasterBottomSheetBehavior.let {
            it.isHideable = false
            it.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun showNotification() {
        val notificationWorkRequest =
            OneTimeWorkRequest.Builder(FloodNotificationWorker::class.java)
                .setInputData(
                    workDataOf(
                        KEY_TITLE to getString(R.string.dn_title),
                        KEY_DEPTH to getString(R.string.dn_desc)
                    )
                )
                .build()

        WorkManager.getInstance(this).enqueue(notificationWorkRequest)
    }

    private fun setData(propertiesWithCoordinates: List<Pair<Properties, List<Any?>>>) {
        val adapter = DisasterAdapter()
        adapter.submitList(propertiesWithCoordinates.map { it.first })

        if (propertiesWithCoordinates.isNotEmpty()) {
            binding.rvDisaster.visibility = View.VISIBLE
            binding.tvEmptyData.visibility = View.GONE
            binding.rvDisaster.adapter = adapter
        } else {
            binding.rvDisaster.visibility = View.GONE
            binding.tvEmptyData.visibility = View.VISIBLE
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        isMapReady = true

        mMap.let {
            it.uiSettings.isIndoorLevelPickerEnabled = true
            it.uiSettings.isCompassEnabled = true
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