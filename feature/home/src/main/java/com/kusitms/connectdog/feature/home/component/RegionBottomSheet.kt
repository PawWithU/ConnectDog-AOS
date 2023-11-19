package com.kusitms.connectdog.feature.home.component

import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import com.kusitms.connectdog.core.designsystem.component.ConnectDogRegions
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.feature.home.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RegionBottomSheet(
    sheetState: SheetState,
    regionType: RegionType,
    onDismissRequest: () -> Unit,
    onSelectedRegion: (String) -> Unit
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        val titleRes =
            if (regionType == RegionType.DEPARTURE) {
                R.string.filter_select_departure_region
            } else {
                R.string.filter_select_destination_region
            }
        RegionHeader(titleRes = titleRes) {
            onDismissRequest()
        }
        ConnectDogRegions(onSelected = onSelectedRegion)
    }
}

@Composable
private fun RegionHeader(
    @StringRes titleRes: Int,
    onBackClick: () -> Unit
) {
    ConnectDogTopAppBar(
        titleRes = titleRes,
        navigationType = TopAppBarNavigationType.CLOSE,
        navigationIconContentDescription = "닫기",
        onNavigationClick = { onBackClick() }
    )
}

enum class RegionType {
    DEPARTURE, DESTINATION
}
