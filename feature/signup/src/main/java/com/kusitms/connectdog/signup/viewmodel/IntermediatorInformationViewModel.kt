package com.kusitms.connectdog.signup.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IntermediatorInformationViewModel
    @Inject
    constructor() : ViewModel() {
        private val _url: MutableState<String> = mutableStateOf("")
        val url: String
            get() = _url.value

        private val _contact: MutableState<String> = mutableStateOf("")
        val contact: String
            get() = _contact.value

        fun updateUrl(name: String) {
            _url.value = name
        }

        fun updateContact(contact: String) {
            _contact.value = contact
        }
    }
