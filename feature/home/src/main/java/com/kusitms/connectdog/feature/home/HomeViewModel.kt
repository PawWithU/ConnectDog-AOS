package com.kusitms.connectdog.feature.home

import androidx.lifecycle.ViewModel
import com.kusitms.connectdog.core.data.repository.ExampleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    exampleRepository: ExampleRepository
) : ViewModel() {

}
