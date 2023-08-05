package com.gg3megp0543.perify.core.utils

import com.gg3megp0543.perify.R
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun getStringOrDefault(
        value: String?,
        defaultString: String,
        emptyString: String = ""
    ): String {
        return value?.takeUnless { it == emptyString } ?: defaultString
    }

    fun setCardBackgroundColor(disasterType: String?): Int {
        return when (disasterType) {
            DisasterEnum.FIRE.disaster -> R.color.fire
            DisasterEnum.FLOOD.disaster -> R.color.flood
            DisasterEnum.EARTHQUAKE.disaster -> R.color.earthquake
            DisasterEnum.HAZE.disaster -> R.color.haze
            DisasterEnum.VOLCANO.disaster -> R.color.volcano
            DisasterEnum.WIND.disaster -> R.color.wind
            else -> R.color.flood
        }
    }

    fun dateFormatter(inputDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val date: Date = inputFormat.parse(inputDate) ?: Date()
        val outputFormat = SimpleDateFormat("MMM d, yyyy - HH:mm:ss", Locale.US)

        return outputFormat.format(date)
    }
}