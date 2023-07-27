package com.gg3megp0543.perify.ui.main

import androidx.lifecycle.ViewModel

class DummyNotificationViewModel : ViewModel() {
    private var _isNotificationShown = false

    val isNotificationShown: Boolean
        get() = _isNotificationShown

    fun markNotificationAsShown() {
        _isNotificationShown = true
    }
}