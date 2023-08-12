package com.gg3megp0543.perify.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gg3megp0543.perify.R
import com.gg3megp0543.perify.core.domain.model.Disaster
import com.gg3megp0543.perify.databinding.ItemPerilBinding
import com.gg3megp0543.perify.core.utils.CodeProvinceHelper
import com.gg3megp0543.perify.core.utils.Utils

class DisasterAdapter :
    androidx.recyclerview.widget.ListAdapter<Disaster, DisasterAdapter.MyViewHolder>(
        DIFF_CALLBACK
    ) {

    private var listData = ArrayList<Disaster>()

    fun setData(newListData: List<Disaster>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_peril, parent, false)
        )

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPerilBinding.bind(itemView)
        fun bind(data: Disaster) {
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
                data.location?.let { CodeProvinceHelper.getLocationName(it) }
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
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Disaster>() {
            override fun areItemsTheSame(
                oldItem: Disaster,
                newItem: Disaster
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Disaster,
                newItem: Disaster
            ): Boolean {
                return oldItem.pkey == newItem.pkey
            }
        }
    }
}