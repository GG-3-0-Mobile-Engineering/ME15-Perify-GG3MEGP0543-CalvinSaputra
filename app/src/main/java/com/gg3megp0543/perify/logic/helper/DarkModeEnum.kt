package com.gg3megp0543.perify.logic.helper

import androidx.appcompat.app.AppCompatDelegate

enum class DarkModeEnum(val value: Int) {

    FOLLOW_SYSTEM(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM),
    ON(AppCompatDelegate.MODE_NIGHT_YES),
    OFF(AppCompatDelegate.MODE_NIGHT_NO)

}