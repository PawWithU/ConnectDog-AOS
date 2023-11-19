package com.kusitms.connectdog.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.repository.HomeRepository
import com.kusitms.connectdog.feature.home.model.Detail
import com.kusitms.connectdog.feature.home.model.Filter
import com.kusitms.connectdog.feature.home.state.AnnouncementUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate

@HiltViewModel
class SearchViewModel @Inject constructor(
    homeRepository: HomeRepository
): ViewModel() {
    private var _filter = MutableStateFlow(Filter())
    val filter: StateFlow<Filter> get() = _filter

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow: SharedFlow<Throwable> get() = _errorFlow

    val announcementUiState: StateFlow<AnnouncementUiState> =
        flow {
            emit(homeRepository.getAnnouncementListWithFilter())
        }.map {
            if (it.isNotEmpty()) {
                AnnouncementUiState.Announcements(it)
            } else {
                AnnouncementUiState.Empty
            }
        }.catch {
            _errorFlow.emit(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AnnouncementUiState.Loading
        )

    fun setFilter(filter: Filter) {
        _filter.value = filter
        Log.d("HomeViewModel", "filter = ${filter}")
    }

    fun setFilter(detail: Detail) {
        _filter.value = filter.value.copy(detail = detail)
        Log.d("HomeViewModel", "filter = ${filter.value}")
    }

    fun setFilter(startDate: LocalDate, endDate: LocalDate) {
        Log.d("HomeViewModel", "filter = ${filter.value}")
        _filter.value = filter.value.copy(startDate = startDate, endDate = endDate)
    }

    fun setFilter(departure: String, arrival: String) {
        _filter.value = filter.value.copy(departure = departure, arrival = arrival)
    }

    fun clearFilter() {
        _filter.value = Filter()
    }
}