package com.kusitms.connectdog.feature.mypage.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kusitms.connectdog.core.data.api.model.volunteer.BookmarkResponseItem
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.ListForUserItem
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray7

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BookmarkScreen(
    onBackClick: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val bookmarkItem by viewModel.bookmark.observeAsState(null)

    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.bookmark,
                navigationType = TopAppBarNavigationType.BACK,
                onNavigationClick = onBackClick
            )
        }
    ) {
        bookmarkItem?.let { Content(it) }
    }
}

@Composable
private fun Content(item: List<BookmarkResponseItem>) {
    LazyColumn(
        modifier = Modifier.padding(top = 48.dp),
        verticalArrangement = Arrangement.Top
    ) {
        items(item) {
            BookmarkContent(item = it)
        }
    }
}

@Composable
private fun BookmarkContent(item: BookmarkResponseItem) {
    ListForUserItem(
        modifier = Modifier.padding(20.dp),
        imageUrl = item.mainImage,
        location = "${item.departureLoc} → ${item.arrivalLoc}",
        date = "${item.startDate} - ${item.endDate}",
        organization = item.intermediaryName,
        hasKennel = item.isKennel
    )
    Divider(thickness = 8.dp, color = Gray7)
}

@Preview
@Composable
private fun Test() {
    ConnectDogTheme {
        BookmarkScreen(onBackClick = {})
    }
}
