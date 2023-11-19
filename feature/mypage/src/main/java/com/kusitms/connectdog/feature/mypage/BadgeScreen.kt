package com.kusitms.connectdog.feature.mypage

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.NetworkImage
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme

private val volunteerDescriptionList = listOf(
    R.string.review_first,
    R.string.review_second,
    R.string.review_third,
    R.string.review_fourth,
    R.string.review_fifth,
    R.string.review_sixth
)

private val reviewDescriptionList = listOf(
    R.string.first,
    R.string.second,
    R.string.third,
    R.string.fourth,
    R.string.fifth,
    R.string.sixth
)

data class BadgeItem(
    val imageUrl: String?,
    @StringRes val description: Int
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BadgeScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.badge,
                navigationType = TopAppBarNavigationType.BACK,
                navigationIconContentDescription = null,
                onNavigationClick = onBackClick
            )
        }
    ) {
        Content()
    }
}

@Composable
private fun Content() {
    val volunteerItems = List(6) {
        BadgeItem(null, reviewDescriptionList[it])
    }

    val reviewItems = List(6) {
        BadgeItem(null, volunteerDescriptionList[it])
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp)
    ) {
        BadgeGrid(titleRes = R.string.volunteer_title, volunteerItems)
        BadgeGrid(titleRes = R.string.review_title, reviewItems)
    }
}

@Composable
private fun BadgeGrid(
    @StringRes titleRes: Int,
    list: List<BadgeItem>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 30.dp)
    ) {
        Text(
            text = stringResource(id = titleRes),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(list) {
                BadgeContent(it)
            }
        }
    }
}

@Composable
private fun BadgeContent(
    item: BadgeItem
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NetworkImage(
            imageUrl = item.imageUrl,
            placeholder = painterResource(id = R.drawable.ic_lock)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = stringResource(id = item.description),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun test() {
    ConnectDogTheme {
        Content()
    }
}
