package com.kusitms.connectdog.feature.intermediator.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.api.model.intermediator.CreateApplicationDto
import com.kusitms.connectdog.core.data.repository.InterHomeRepository
import com.kusitms.connectdog.core.designsystem.component.DayTime
import com.kusitms.connectdog.core.designsystem.component.Detail
import com.kusitms.connectdog.core.util.ImageConverter.uriToFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CreateApplicationViewModel
    @Inject
    constructor(
        private val interHomeRepository: InterHomeRepository,
    ) : ViewModel() {
        private val _uriList = MutableStateFlow<MutableList<Uri>>(mutableListOf())
        val uriList: StateFlow<List<Uri>>
            get() = _uriList

        private val _departure = MutableStateFlow<String?>(null)
        val departure: StateFlow<String?>
            get() = _departure

        private val _destination = MutableStateFlow<String?>(null)
        val destination: StateFlow<String?>
            get() = _destination

        private val _startDate = MutableStateFlow<LocalDate?>(null)
        val startDate: StateFlow<LocalDate?>
            get() = _startDate

        private val _endDate = MutableStateFlow<LocalDate?>(null)
        val endDate: StateFlow<LocalDate?>
            get() = _endDate

        private val _hour = MutableStateFlow<Int?>(null)
        val hour: StateFlow<Int?>
            get() = _hour

        private val _minute = MutableStateFlow<Int?>(null)
        val minute: StateFlow<Int?>
            get() = _minute

        private val _dayTime = MutableStateFlow<DayTime?>(null)
        val dayTime: StateFlow<DayTime?>
            get() = _dayTime

        private val _isKennel = MutableStateFlow<Boolean?>(null)
        val isKennel: StateFlow<Boolean?>
            get() = _isKennel

        private val _isAdjustableTime = MutableStateFlow<Boolean>(false)
        val isAdjustableTime: StateFlow<Boolean>
            get() = _isAdjustableTime

        private val _isAdjustableSchedule = MutableStateFlow(false)
        val isAdjustableSchedule: StateFlow<Boolean>
            get() = _isAdjustableSchedule

        private val _content: MutableState<String> = mutableStateOf("")
        val content: String
            get() = _content.value

        private val _specifics: MutableState<String> = mutableStateOf("")
        val specifics: String
            get() = _specifics.value

        private val _name: MutableState<String> = mutableStateOf("")
        val name: String
            get() = _name.value

        private val _dogSize = MutableStateFlow<Detail.DogSize?>(null)
        val dogSize: StateFlow<Detail.DogSize?>
            get() = _dogSize

        fun updateUriList(uri: Uri) {
            _uriList.value = _uriList.value.toMutableList().apply { add(uri) }
        }

        fun removeUriList(uri: Uri) {
            _uriList.value = _uriList.value.toMutableList().apply { remove(uri) }
        }

        fun updateStartDate(start: LocalDate) {
            _startDate.value = start
        }

        fun updateEndDate(end: LocalDate) {
            _endDate.value = end
        }

        fun updateDestination(destination: String) {
            _destination.value = destination
        }

        fun updateDeparture(departure: String) {
            _departure.value = departure
        }

        fun updateHour(hour: Int) {
            _hour.value = hour
        }

        fun updateMinute(minute: Int) {
            _minute.value = minute
        }

        fun updateDayTime(dayTime: DayTime) {
            _dayTime.value = dayTime
        }

        fun updateIsKennel(value: Boolean) {
            _isKennel.value = value
        }

        fun updateDogSize(dogSize: Detail.DogSize) {
            _dogSize.value = dogSize
        }

        fun updateIsAdjustableTime(value: Boolean) {
            _isAdjustableTime.value = value
            _minute.value = null
            _hour.value = null
            _dayTime.value = null
        }

        fun updateName(name: String) {
            _name.value = name
        }

        fun updateSignificant(description: String) {
            _content.value = description
        }

        fun updateIsAdjustableSchedule(value: Boolean) {
            _isAdjustableSchedule.value = value
        }

        fun updateSpecifics(value: String) {
            _specifics.value = value
        }

        fun clear() {
            _uriList.value = mutableListOf()
            _departure.value = null
            _destination.value = null
            _startDate.value = null
            _endDate.value = null
            _hour.value = null
            _minute.value = null
            _dayTime.value = null
            _isKennel.value = null
            _isAdjustableTime.value = false
            _isAdjustableSchedule.value = false
            _content.value = ""
            _name.value = ""
            _dogSize.value = null
            _specifics.value = ""
        }

        fun createApplication(context: Context) =
            viewModelScope.launch {
                val files =
                    _uriList.value.mapNotNull { uri ->
                        uriToFile(context, uri, 80)
                    }

                val pickUpTime =
                    if (_startDate.value != _endDate.value) {
                        ""
                    } else if (_isAdjustableTime.value || (_startDate.value != _endDate.value)) {
                        "시간 미정"
                    } else {
                        "${_dayTime.value?.time} ${String.format("%02d", _hour.value)}:${String.format("%02d", _minute.value)}"
                    }

                val body =
                    CreateApplicationDto(
                        departureLoc = _departure.value!!,
                        arrivalLoc = _destination.value!!,
                        startDate = _startDate.value.toString(),
                        endDate = _endDate.value.toString(),
                        pickUpTime = pickUpTime,
                        isKennel = _isKennel.value!!,
                        content = _content.value,
                        dogName = _name.value,
                        dogSize = _dogSize.value!!.toDisplayName(),
                        specifics = _specifics.value,
                    )

                try {
                    interHomeRepository.createApplication(body, files)
                } catch (e: Exception) {
                    Log.d("tesqasxzasda", e.toString())
                }
            }
    }
