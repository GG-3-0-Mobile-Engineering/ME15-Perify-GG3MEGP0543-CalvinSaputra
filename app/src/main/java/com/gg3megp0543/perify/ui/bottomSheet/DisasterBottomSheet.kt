package com.gg3megp0543.perify.ui.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gg3megp0543.perify.R
import com.gg3megp0543.perify.logic.model.Properties
import com.gg3megp0543.perify.ui.main.DisasterAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DisasterBottomSheet : BottomSheetDialogFragment() {
    private lateinit var rvDisaster: RecyclerView

    fun setData(listProperties: List<Properties>) {
        val adapter = DisasterAdapter()
        adapter.submitList(listProperties)
        rvDisaster.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet, container, false)
        val standardBottomSheet = view.findViewById<FrameLayout>(R.id.bottom_sheet)
        val standardBottomSheetBehavior = BottomSheetBehavior.from(standardBottomSheet)
        standardBottomSheetBehavior.isHideable = false
        standardBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        rvDisaster = view.findViewById(R.id.rv_peril)
        rvDisaster.layoutManager = LinearLayoutManager(requireContext())
        return view
    }
}