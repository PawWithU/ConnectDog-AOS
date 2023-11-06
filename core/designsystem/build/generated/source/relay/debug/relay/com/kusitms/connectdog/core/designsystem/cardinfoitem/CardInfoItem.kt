package com.kusitms.connectdog.core.designsystem.cardinfoitem

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.google.relay.compose.CrossAxisAlignment
import com.google.relay.compose.MainAxisAlignment
import com.google.relay.compose.RelayContainer
import com.google.relay.compose.RelayContainerArrangement
import com.google.relay.compose.RelayContainerScope
import com.google.relay.compose.RelayImage
import com.google.relay.compose.RelayText
import com.google.relay.compose.tappable
import com.kusitms.connectdog.core.designsystem.R

/**
 * This composable was generated from the UI Package 'card_info_item'.
 * Generated code; do not edit directly
 */
@Composable
fun CardInfoItem(
    modifier: Modifier = Modifier,
    onCardInfoItemClicked: () -> Unit = {},
    img: Painter,
    title: String,
    datetime: String,
    organization: String,
    etc: String
) {
    TopLevel(
        onCardInfoItemClicked = onCardInfoItemClicked,
        modifier = modifier
    ) {
        CardImage {
            Image1(
                img = img,
                modifier = Modifier.boxAlign(
                    alignment = Alignment.TopStart,
                    offset = DpOffset(
                        x = 1.5.dp,
                        y = 1.5.dp
                    )
                )
            )
        }
        CardContent(modifier = Modifier.rowWeight(1.0f)) {
            Title(title = title)
            Description(modifier = Modifier.rowWeight(1.0f)) {
                Date(modifier = Modifier.rowWeight(1.0f)) {
                    Datetime()
                    TextDueDate(datetime = datetime)
                }
                Organization(modifier = Modifier.rowWeight(1.0f)) {
                    Organization()
                    TextDueDate1(organization = organization)
                }
                Etc(modifier = Modifier.rowWeight(1.0f)) {
                    Etc()
                    TextDueDate2(etc = etc)
                }
            }
        }
    }
}

@Preview(widthDp = 320, heightDp = 80)
@Composable
private fun CardInfoItemPreview() {
    MaterialTheme {
        RelayContainer {
            CardInfoItem(
                onCardInfoItemClicked = {},
                img = painterResource(R.drawable.card_info_item_image_1),
                title = "인천 > 부산",
                datetime = "23.12.22(금) 17:00",
                organization = "단체이름이름",
                etc = "X (따로 준비가 필요해요)",
                modifier = Modifier.rowWeight(1.0f)
            )
        }
    }
}

@Composable
fun Image1(
    img: Painter,
    modifier: Modifier = Modifier
) {
    RelayImage(
        image = img,
        contentScale = ContentScale.Crop,
        modifier = modifier.requiredWidth(83.0.dp).requiredHeight(83.0.dp)
    )
}

@Composable
fun CardImage(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        radius = 6.0,
        content = content,
        modifier = modifier.requiredWidth(80.0.dp).requiredHeight(80.0.dp)
    )
}

@Composable
fun Title(
    title: String,
    modifier: Modifier = Modifier
) {
    RelayText(
        content = title,
        fontFamily = pretendard,
        color = Color(
            alpha = 255,
            red = 55,
            green = 55,
            blue = 55
        ),
        height = 1.193359375.em,
        letterSpacing = -0.6000000238418579.sp,
        textAlign = TextAlign.Left,
        fontWeight = FontWeight(600.0.toInt()),
        modifier = modifier
    )
}

@Composable
fun Datetime(modifier: Modifier = Modifier) {
    RelayText(
        content = "이동봉사 일정",
        fontSize = 12.0.sp,
        fontFamily = pretendard,
        color = Color(
            alpha = 255,
            red = 159,
            green = 159,
            blue = 159
        ),
        height = 1.193359375.em,
        letterSpacing = -0.6000000238418579.sp,
        textAlign = TextAlign.Left,
        maxLines = -1,
        modifier = modifier.requiredWidth(75.0.dp)
    )
}

@Composable
fun TextDueDate(
    datetime: String,
    modifier: Modifier = Modifier
) {
    RelayText(
        content = datetime,
        fontSize = 12.0.sp,
        fontFamily = pretendard,
        color = Color(
            alpha = 255,
            red = 55,
            green = 55,
            blue = 55
        ),
        height = 1.193359375.em,
        letterSpacing = -0.24.sp,
        textAlign = TextAlign.Left,
        modifier = modifier
    )
}

@Composable
fun Date(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        mainAxisAlignment = MainAxisAlignment.Start,
        crossAxisAlignment = CrossAxisAlignment.Start,
        arrangement = RelayContainerArrangement.Row,
        itemSpacing = 12.0,
        clipToParent = false,
        content = content,
        modifier = modifier.fillMaxWidth(1.0f)
    )
}

@Composable
fun Organization(modifier: Modifier = Modifier) {
    RelayText(
        content = "이동봉사 중개",
        fontSize = 12.0.sp,
        fontFamily = pretendard,
        color = Color(
            alpha = 255,
            red = 159,
            green = 159,
            blue = 159
        ),
        height = 1.193359375.em,
        letterSpacing = -0.6000000238418579.sp,
        textAlign = TextAlign.Left,
        maxLines = -1,
        modifier = modifier.requiredWidth(75.0.dp)
    )
}

@Composable
fun TextDueDate1(
    organization: String,
    modifier: Modifier = Modifier
) {
    RelayText(
        content = organization,
        fontSize = 12.0.sp,
        fontFamily = pretendard,
        color = Color(
            alpha = 255,
            red = 55,
            green = 55,
            blue = 55
        ),
        height = 1.193359375.em,
        letterSpacing = -0.6000000238418579.sp,
        textAlign = TextAlign.Left,
        modifier = modifier
    )
}

@Composable
fun Organization(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        mainAxisAlignment = MainAxisAlignment.Start,
        crossAxisAlignment = CrossAxisAlignment.Start,
        arrangement = RelayContainerArrangement.Row,
        itemSpacing = 12.0,
        clipToParent = false,
        content = content,
        modifier = modifier.fillMaxWidth(1.0f)
    )
}

@Composable
fun Etc(modifier: Modifier = Modifier) {
    RelayText(
        content = "컨넬 준비 여부",
        fontSize = 12.0.sp,
        fontFamily = pretendard,
        color = Color(
            alpha = 255,
            red = 159,
            green = 159,
            blue = 159
        ),
        height = 1.193359375.em,
        letterSpacing = -0.6000000238418579.sp,
        textAlign = TextAlign.Left,
        maxLines = -1,
        modifier = modifier.requiredWidth(75.0.dp)
    )
}

@Composable
fun TextDueDate2(
    etc: String,
    modifier: Modifier = Modifier
) {
    RelayText(
        content = etc,
        fontSize = 12.0.sp,
        fontFamily = pretendard,
        color = Color(
            alpha = 255,
            red = 55,
            green = 55,
            blue = 55
        ),
        height = 1.193359375.em,
        letterSpacing = -0.6000000238418579.sp,
        textAlign = TextAlign.Left,
        modifier = modifier
    )
}

@Composable
fun Etc(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        mainAxisAlignment = MainAxisAlignment.Start,
        crossAxisAlignment = CrossAxisAlignment.Start,
        arrangement = RelayContainerArrangement.Row,
        itemSpacing = 12.0,
        clipToParent = false,
        content = content,
        modifier = modifier.fillMaxWidth(1.0f)
    )
}

@Composable
fun Description(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        crossAxisAlignment = CrossAxisAlignment.Start,
        itemSpacing = 6.0,
        clipToParent = false,
        content = content,
        modifier = modifier.fillMaxWidth(1.0f)
    )
}

@Composable
fun CardContent(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        crossAxisAlignment = CrossAxisAlignment.Start,
        itemSpacing = 9.0,
        clipToParent = false,
        content = content,
        modifier = modifier.fillMaxWidth(1.0f)
    )
}

@Composable
fun TopLevel(
    onCardInfoItemClicked: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 255,
            green = 255,
            blue = 255
        ),
        mainAxisAlignment = MainAxisAlignment.Start,
        crossAxisAlignment = CrossAxisAlignment.Start,
        arrangement = RelayContainerArrangement.Row,
        itemSpacing = 16.0,
        content = content,
        modifier = modifier.tappable(onTap = onCardInfoItemClicked).fillMaxWidth(1.0f)
    )
}
