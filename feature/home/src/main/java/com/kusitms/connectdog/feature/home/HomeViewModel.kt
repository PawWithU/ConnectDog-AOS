package com.kusitms.connectdog.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kusitms.connectdog.core.data.repository.ExampleRepository
import com.kusitms.connectdog.core.data.repository.HomeRepository
import com.kusitms.connectdog.feature.home.model.Detail
import com.kusitms.connectdog.feature.home.model.Filter
import com.kusitms.connectdog.feature.home.state.AnnouncementUiState
import com.kusitms.connectdog.feature.home.state.ExampleUiState
import com.kusitms.connectdog.feature.home.state.ReviewUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    exampleRepository: ExampleRepository,
    homeRepository: HomeRepository
) : ViewModel() {
    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow: SharedFlow<Throwable> get() = _errorFlow

    val exampleUiState: StateFlow<ExampleUiState> =
        flow {
            emit(exampleRepository.getExample())
        }.map { examples ->
            if (examples.isNotEmpty()) {
                ExampleUiState.Examples(examples)
            } else {
                ExampleUiState.Empty
            }
        }.catch {
            _errorFlow.emit(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ExampleUiState.Loading
        )

    val announcementUiState: StateFlow<AnnouncementUiState> =
        flow {
            emit(homeRepository.getAnnouncementList())
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

    val reviewUiState: StateFlow<ReviewUiState> =
        flow {
            emit(homeRepository.getReviewList())
        }.map {
            if (it.isNotEmpty()) {
                ReviewUiState.Reviews(it)
            } else {
                ReviewUiState.Empty
            }
        }.catch {
            _errorFlow.emit(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ReviewUiState.Loading
        )


    private var _filter = MutableStateFlow(Filter())
    val filter: StateFlow<Filter> get() = _filter

    fun setFilter(filter: Filter){
        _filter.value = filter
        Log.d("HomeViewModel", "filter = ${filter}")
    }

    fun setFilter(detail: Detail){
        _filter.value.detail = detail
        Log.d("HomeViewModel", "filter = ${filter.value}")
    }

    fun detailContentDisplay(
        dogSize: Detail.DogSize?,
        hasKennel: Boolean?,
        organization: String?
    ): String {
        val dogSizeDisplayName = dogSize?.toDisplayName().orEmpty()
        val kennelStatus = if (hasKennel == null) "" else if (hasKennel) "켄넬 O" else "켄넬 X"
        val organizationText = organization.orEmpty()

        return buildString {
            append(dogSizeDisplayName)
            if (dogSize != null && hasKennel != null) append("-")
            append(kennelStatus)
            if (this.isNotEmpty() && organization != null) append("-")
            if (organizationText.isNotEmpty()) append(organizationText)
        }
    }


}
