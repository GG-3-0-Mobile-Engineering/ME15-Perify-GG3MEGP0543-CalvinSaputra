package com.gg3megp0543.perify.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gg3megp0543.perify.R
import com.gg3megp0543.perify.databinding.ItemPerilBinding
import com.gg3megp0543.perify.logic.model.Properties

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
            binding.tvDisasterName.text = data.title.toString()
            binding.tvDisasterInformation.text = data.text ?: "No Text"
            Glide.with(binding.root.context)
                .load(data.imageUrl ?: R.drawable.ic_launcher_background)
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