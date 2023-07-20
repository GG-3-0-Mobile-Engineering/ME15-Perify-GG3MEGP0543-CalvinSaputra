package com.gg3megp0543.perify.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gg3megp0543.perify.databinding.ActivityMainBinding
import com.gg3megp0543.perify.logic.model.Properties

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.properties.observe(this) {
            it?.let { properties ->
                setupAdapter(properties)
            }
        }

        val layoutMan = LinearLayoutManager(this)
        binding.rvDisaster.layoutManager = layoutMan
        val itemDeco = DividerItemDecoration(this, layoutMan.orientation)
        binding.rvDisaster.addItemDecoration(itemDeco)

    }

    private fun setupAdapter(listProperties: List<Properties>) {
        val adapter = DisasterAdapter()
        adapter.submitList(listProperties)
        binding.rvDisaster.adapter = adapter
    }
}