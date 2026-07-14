package com.kusitms.connectdog.feature.management.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.ReviewItemContent
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.util.UserType
import com.kusitms.connectdog.feature.management.R
import com.kusitms.connectdog.feature.management.state.ReviewUiState
import com.kusitms.connectdog.feature.management.viewmodel.ReviewViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CheckReviewScreen(
    onBackClick: () -> Unit,
    onInterProfileClick: (Long) -> Unit,
    reviewId: Long,
    userType: UserType,
    viewModel: ReviewViewModel = hiltViewModel(),
) {
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.review,
                navigationType = TopAppBarNavigationType.BACK,
                onNavigationClick = onBackClick,
            )
        },
    ) {
        LaunchedEffect(key1 = Unit) {
            viewModel.updateReviewId(reviewId)
        }
        val reviewUiState by viewModel.reviewUiState.collectAsStateWithLifecycle()

        when (reviewUiState) {
            is ReviewUiState.Loading -> Loading()
            is ReviewUiState.Reviews -> {
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(top = 48.dp),
                ) {
                    ReviewItemContent(
                        review = (reviewUiState as ReviewUiState.Reviews).review,
                        userType = userType,
                        onInterProfileClick = onInterProfileClick,
                    )
                }
            }
        }
    }
}
