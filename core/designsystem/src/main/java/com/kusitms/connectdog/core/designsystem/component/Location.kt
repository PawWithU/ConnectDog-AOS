package com.kusitms.connectdog.core.designsystem.component

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.theme.Gray1
import com.kusitms.connectdog.core.designsystem.theme.Gray4

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationContent(
    departureLocation: String?,
    destinationLocation: String?,
    onSelectedRegion: (String?, String?) -> Unit,
) {
    val departureSheetState = rememberModalBottomSheetState()
    var isDepartureSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    val destinationSheetState = rememberModalBottomSheetState()
    var isDestinationSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    var departure by remember { mutableStateOf(departureLocation) }
    var destination by remember { mutableStateOf(destinationLocation) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.img_location_path),
            contentDescription = "출발지-도착지",
        )
        Spacer(modifier = Modifier.size(12.dp))
        Column {
            SelectLocation(
                titleRes = R.string.filter_departure,
                place = departure,
                placeholderRes = R.string.filter_select_departure,
            ) {
                isDepartureSheetOpen = true
            }
            Spacer(modifier = Modifier.size(30.dp))
            SelectLocation(
                titleRes = R.string.filter_destination,
                place = destination,
                placeholderRes = R.string.filter_select_destination,
            ) {
                isDestinationSheetOpen = true
            }
        }
    }

    if (isDepartureSheetOpen) {
        RegionBottomSheet(
            sheetState = departureSheetState,
            regionType = RegionType.DEPARTURE,
            onDismissRequest = { isDepartureSheetOpen = false },
        ) {
            Log.d("FilterSearch", "departure = $it")
            departure = it
            isDepartureSheetOpen = false
            onSelectedRegion(departure, destination)
        }
    }

    if (isDestinationSheetOpen) {
        RegionBottomSheet(
            sheetState = destinationSheetState,
            regionType = RegionType.DESTINATION,
            onDismissRequest = { isDestinationSheetOpen = false },
        ) {
            Log.d("FilterSearch", "destination = $it")
            destination = it
            isDestinationSheetOpen = false
            onSelectedRegion(departure, destination)
        }
    }
}

@Composable
private fun SelectLocation(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    place: String?,
    @StringRes placeholderRes: Int,
    onClick: () -> Unit,
) {
    Column(modifier = modifier.clickable { onClick() }) {
        Text(
            text = stringResource(id = titleRes),
            style = MaterialTheme.typography.titleMedium,
            fontSize = 14.sp,
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = if (place.isNullOrEmpty()) stringResource(id = placeholderRes) else place,
            style = MaterialTheme.typography.bodyLarge,
            color = if (place.isNullOrEmpty()) Gray4 else Gray1,
            modifier = Modifier.padding(vertical = 8.dp),
        )
        Divider(modifier = Modifier.fillMaxWidth())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RegionBottomSheet(
    sheetState: SheetState,
    regionType: RegionType,
    onDismissRequest: () -> Unit,
    onSelectedRegion: (String) -> Unit,
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        containerColor = MaterialTheme.colorScheme.surface,
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
    onBackClick: () -> Unit,
) {
    ConnectDogTopAppBar(
        titleRes = titleRes,
        navigationType = TopAppBarNavigationType.CLOSE,
        navigationIconContentDescription = "닫기",
        onNavigationClick = { onBackClick() },
    )
}

enum class RegionType {
    DEPARTURE,
    DESTINATION,
}
