package com.gg3megp0543.perify.logic.helper

import com.gg3megp0543.perify.R

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
}