package com.gg3megp0543.perify.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gg3megp0543.perify.R
import com.gg3megp0543.perify.databinding.ItemPerilBinding
import com.gg3megp0543.perify.logic.helper.CodeProvinceHelper
import com.gg3megp0543.perify.logic.helper.Utils
import com.gg3megp0543.perify.logic.response.Properties

class DisasterAdapter :
    androidx.recyclerview.widget.ListAdapter<Properties, DisasterAdapter.MyViewHolder>(
        DIFF_CALLBACK
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemPerilBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class MyViewHolder(private val binding: ItemPerilBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Properties) {
            binding.tvDisasterName.text = Utils.getStringOrDefault(
                data.title,
                binding.root.context.getString(R.string.title_null)
            )

            binding.tvDisasterInformation.text = Utils.getStringOrDefault(
                data.text,
                binding.root.context.getString(R.string.information_null),
                binding.root.context.getString(R.string.empty_string)
            )

            binding.tvDisasterDate.text = binding.root.context.getString(
                R.string.iperil_date_location,
                Utils.dateFormatter(data.createdAt.toString()),
                CodeProvinceHelper.getLocationName(data.tags?.instanceRegionCode.toString())
            )
            binding.tvDisasterLabel.text = data.disasterType

            binding.disasterCard.setCardBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    Utils.setCardBackgroundColor(data.disasterType)
                )
            )

            Glide.with(binding.root.context)
                .load(data.imageUrl ?: R.drawable.empty_placeholder)
                .into(binding.ivDisasaterPhoto)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Properties>() {
            override fun areItemsTheSame(
                oldItem: Properties,
                newItem: Properties
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Properties,
                newItem: Properties
            ): Boolean {
                return oldItem.pkey == newItem.pkey
            }
        }
    }
}