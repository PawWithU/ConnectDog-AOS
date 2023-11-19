package com.kusitms.connectdog.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.repository.HomeRepository
import com.kusitms.connectdog.feature.home.model.Detail
import com.kusitms.connectdog.feature.home.model.Filter
import com.kusitms.connectdog.feature.home.state.AnnouncementUiState
import dagger.hilt.android.lifecycle.HiltViewModel
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
import javax.inject.Inject

private val TAG = "SearchViewModel"

@HiltViewModel
class SearchViewModel @Inject constructor(
    homeRepository: HomeRepository
) : ViewModel() {
    private var _filter = MutableStateFlow(Filter())
    val filter: StateFlow<Filter> get() = _filter

    private var _isDeadlineOrder = MutableStateFlow(true)
    val isDeadlineOrder: StateFlow<Boolean> get() = _isDeadlineOrder

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow: SharedFlow<Throwable> get() = _errorFlow

    val announcementUiState: StateFlow<AnnouncementUiState> =
        flow {
            emit(
                homeRepository.getAnnouncementListWithFilter(
                    departureLoc = filter.value.departure,
                    arrivalLoc = filter.value.arrival,
                    startDate = filter.value.startDate.toString(),
                    endDate = filter.value.endDate.toString(),
                    dogSize = filter.value.detail.dogSize?.toDisplayName(),
                    isKennel = filter.value.detail.hasKennel,
                    intermediaryName = filter.value.detail.organization,
                    orderCondition = if (isDeadlineOrder.value) "마감순" else "최신순"
                )
            )
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
        Log.d("HomeViewModel", "filter = $filter")
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

    fun changeOrderCondition() {
        _isDeadlineOrder.value = !_isDeadlineOrder.value
    }
}
