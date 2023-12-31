package com.kusitms.connectdog.feature.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TermsViewModel : ViewModel() {
    private val _allChecked = MutableLiveData(false)
    val allChecked: LiveData<Boolean> = _allChecked

    private val _termsChecked = MutableLiveData(false)
    val termsChecked: LiveData<Boolean> = _termsChecked

    private val _privacyChecked = MutableLiveData(false)
    val privacyChecked: LiveData<Boolean> = _privacyChecked

    fun toggleAllChecked() {
        _allChecked.value = !_allChecked.value!!
        _termsChecked.value = !_allChecked.value!!
        _privacyChecked.value = !_allChecked.value!!
    }

    fun toggleTermsChecked() {
        _termsChecked.value = !_termsChecked.value!!
        updateAllCheckedState()
    }

    fun togglePrivacyChecked() {
        _privacyChecked.value = !_privacyChecked.value!!
        updateAllCheckedState()
    }

    private fun updateAllCheckedState() {
        _allChecked.value = _termsChecked.value == true && _privacyChecked.value == true
    }

    fun resetState() {
        _allChecked.value = false
        _privacyChecked.value = false
        _termsChecked.value = false
    }
}
