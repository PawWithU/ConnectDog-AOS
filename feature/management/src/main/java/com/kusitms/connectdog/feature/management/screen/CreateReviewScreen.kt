package com.kusitms.connectdog.feature.management.screen

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.kusitms.connectdog.core.designsystem.component.ConnectDogBottomButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTextField
import com.kusitms.connectdog.core.designsystem.component.ConnectDogTopAppBar
import com.kusitms.connectdog.core.designsystem.component.ListForUserItem
import com.kusitms.connectdog.core.designsystem.component.TopAppBarNavigationType
import com.kusitms.connectdog.core.designsystem.theme.Gray1
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray4
import com.kusitms.connectdog.core.designsystem.theme.Gray7
import com.kusitms.connectdog.core.model.AnnouncementHome
import com.kusitms.connectdog.core.model.Application
import com.kusitms.connectdog.feature.management.R
import com.kusitms.connectdog.feature.management.dialog.CreateReviewDialog
import com.kusitms.connectdog.feature.management.viewmodel.ReviewViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateReviewScreen(
    application: Application,
    onBackClick: () -> Unit,
    viewModel: ReviewViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            ConnectDogTopAppBar(
                titleRes = R.string.create_review,
                navigationType = TopAppBarNavigationType.BACK,
                onNavigationClick = onBackClick
            )
        }
    ) {
        Content(
            viewModel = viewModel,
            onBackClick = onBackClick,
            application = application
        )
    }
}

@Composable
private fun Content(
    onBackClick: () -> Unit,
    application: Application,
    viewModel: ReviewViewModel
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    var isConfirmDialogVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        viewModel.updatePostId(application.postId)
    }

    val uriList by viewModel.uriList.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 65.dp)
            .clickable(
                onClick = { focusManager.clearFocus() },
                indication = null,
                interactionSource = interactionSource
            )
    ) {
        VolunteerInfo(application)
        ReviewContent(viewModel)
        Divider(thickness = 8.dp, color = Gray7)
        UploadPhoto(viewModel)
        Spacer(modifier = Modifier.weight(1f))
        ConnectDogBottomButton(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 32.dp),
            onClick = { isConfirmDialogVisible = true },
            content = stringResource(id = R.string.create_review),
            enabled = viewModel.review.length >= 20 && uriList.size in 1..5
        )
    }

    if (isConfirmDialogVisible) {
        CreateReviewDialog(
            onConfirmClick = {
                viewModel.createReview(context)
                onBackClick()
            },
            onDismiss = { isConfirmDialogVisible = false }
        )
    }
}

@Composable
private fun VolunteerInfo(
    application: Application
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        ListForUserItem(
            imageUrl = application.imageUrl,
            announcementHome = AnnouncementHome(
                application.imageUrl,
                application.location,
                application.date,
                -1,
                application.dogName ?: "",
                application.pickUpTime ?: ""
            ),
            isValid = true
        )
    }
}

@Composable
private fun ReviewContent(
    viewModel: ReviewViewModel
) {
    Column(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 24.dp)
    ) {
        Text(
            text = stringResource(id = R.string.review_title),
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
        ConnectDogTextField(
            height = 244,
            text = viewModel.review,
            onTextChanged = { viewModel.updateReview(it) },
            label = "느꼈던 감정, 후기를 작성해주세요",
            placeholder = ""
        )
        Row {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "최소 글자수 20",
                fontSize = 10.sp,
                color = Gray4
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${viewModel.review.length} / 300",
                fontSize = 10.sp,
                color = Gray4
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
private fun UploadPhoto(
    viewModel: ReviewViewModel
) {
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia(5)
    ) {
        it.forEach { uri -> viewModel.updateUriList(uri) }
    }
    val uriList by viewModel.uriList.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.padding(top = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        Row {
            Text(
                text = "[선택] 사진 (최대 5장)",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Gray1
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${uriList.size}/5",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Gray3
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow {
            items(uriList.size + 1) { index ->
                key(index) {
                    if (index < uriList.size) {
                        Photo(
                            uri = uriList[index],
                            onRemoveClick = { viewModel.removeUriList(uriList[index]) }
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    } else {
                        AddPhotoButton {
                            launcher.launch(
                                PickVisualMediaRequest(
                                    mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Photo(
    uri: Uri,
    onRemoveClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .border(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.outline,
                width = 1.dp
            )
    ) {
        AsyncImage(
            model = uri,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        IconButton(
            onClick = onRemoveClick,
            modifier = Modifier
                .size(18.dp)
                .padding(top = 5.dp, end = 5.dp)
                .align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.Outlined.Close,
                tint = Color.White,
                contentDescription = null
            )
        }
    }
}

@Composable
private fun AddPhotoButton(onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.clickable { onClick() }
    ) {
        val stroke = Stroke(
            width = 2f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        )
        Canvas(Modifier.size(80.dp)) {
            drawRoundRect(
                color = Gray4,
                style = stroke,
                topLeft = Offset(0f, 0f),
                cornerRadius = CornerRadius(6.dp.toPx(), 6.dp.toPx())
            )
        }
        Icon(
            imageVector = Icons.Outlined.Add,
            tint = Gray4,
            contentDescription = null,
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.Center)
        )
    }
}
