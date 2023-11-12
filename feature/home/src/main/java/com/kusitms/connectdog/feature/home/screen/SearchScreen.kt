package com.kusitms.connectdog.feature.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray4
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import com.kusitms.connectdog.core.model.Filter
import com.kusitms.connectdog.feature.home.R

@Composable
internal fun SearchScreen(
    onBackClick: () -> Unit,
) {
    Column {
        TopAppBar {
            onBackClick()
        }
        SearchBar(
            modifier = Modifier
                .padding(horizontal = 13.dp, vertical = 6.dp)
                .fillMaxWidth()
        ) {} //todo 검색 popup
        FilterBar(filter = Filter(), onClick = { /*TODO*/ })
    }
}

@Composable
private fun TopAppBar(
    onBackClick: () -> Unit
) {
    ConnectDogTopAppBar(
        titleRes = R.string.search_app_bar_title,
        navigationType = TopAppBarNavigationType.BACK,
        navigationIconContentDescription = "Back",
        onNavigationClick = { onBackClick() }
    )
}

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .background(color = Gray7, shape = RoundedCornerShape(6.dp))
            .padding(horizontal = 12.dp, vertical = 15.dp)
            .clickable { onClick() }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "search icon",
            tint = Gray3
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = stringResource(id = R.string.search_where_to_move),
            style = MaterialTheme.typography.titleSmall,
            fontSize = 14.sp,
            color = Gray4
        )
    }
}

@Composable
private fun FilterBar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    filter: Filter,
) {
    val dateFilter: String =
        if (filter.startDate != null && filter.endDate != null) filter.startDate + "-" + filter.endDate
        else stringResource(id = R.string.search_location)
    val locationFilter: String =
        if (filter.startLocation != null && filter.destLocation != null) filter.startLocation + " -> " + filter.destLocation
        else stringResource(id = R.string.search_detail)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterTag(tag = dateFilter, isSelected = filter.startDate != null) { onClick() }
        FilterTag(
            tag = locationFilter,
            isSelected = filter.startLocation != null
        ) { onClick() }
        FilterTag(
            tag = stringResource(id = R.string.search_detail),
            isSelected = filter.detail != null
        ) { onClick() }
    }
}

@Composable
private fun FilterTag(
    tag: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val color = if (isSelected) MaterialTheme.colorScheme.primary else Gray4
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .border(width = 1.dp, color = color)
            .clickable { onClick() }
    ) {
        Text(
            text = tag,
            style = MaterialTheme.typography.titleMedium,
            fontSize = 12.sp,
            color = color
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_expand_down),
            contentDescription = "필터 확장",
            tint = color
        )
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    ConnectDogTheme {
        SearchScreen {}
    }
}