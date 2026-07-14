package com.kusitms.connectdog.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.theme.PetOrange

data class Detail(
    val dogSize: DogSize? = null,
    val hasKennel: Boolean? = null,
    val organization: String? = null,
) {
    fun isNotEmpty(): Boolean {
        return dogSize != null || hasKennel != null || organization != null
    }

    enum class DogSize {
        BIG,
        MIDDLE,
        SMALL,
        ;

        fun toDisplayName(): String {
            return when (this) {
                BIG -> "대형"
                MIDDLE -> "중형"
                SMALL -> "소형"
            }
        }
    }
}

@Composable
fun SelectDogSize(
    selected: Detail.DogSize?,
    onSelectedDogSize: (Detail.DogSize) -> Unit,
) {
    val selectedState = remember { mutableStateOf(selected) }

    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
        DogSizeButton(
            modifier = Modifier.weight(1f),
            isSelected = selectedState.value == Detail.DogSize.BIG,
            onSelected = {
                onSelectedDogSize(Detail.DogSize.BIG)
                selectedState.value = Detail.DogSize.BIG
            },
            imageRes = R.drawable.img_big_dog,
            textRes = R.string.filter_big_dog,
        )
        DogSizeButton(
            modifier = Modifier.weight(1f),
            isSelected = selectedState.value == Detail.DogSize.MIDDLE,
            onSelected = {
                onSelectedDogSize(Detail.DogSize.MIDDLE)
                selectedState.value = Detail.DogSize.MIDDLE
            },
            imageRes = R.drawable.img_middle_dog,
            textRes = R.string.filter_middle_dog,
        )
        DogSizeButton(
            modifier = Modifier.weight(1f),
            isSelected = selectedState.value == Detail.DogSize.SMALL,
            onSelected = {
                onSelectedDogSize(Detail.DogSize.SMALL)
                selectedState.value = Detail.DogSize.SMALL
            },
            imageRes = R.drawable.img_small_dog,
            textRes = R.string.filter_small_dog,
        )
    }
}

@Composable
private fun DogSizeButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    onSelected: () -> Unit,
    imageRes: Int,
    @StringRes textRes: Int,
) {
    ConnectDogCardButton(
        modifier = modifier.defaultMinSize(minHeight = 102.dp, minWidth = 102.dp),
        isSelected = isSelected,
        onSelected = { onSelected() },
    ) {
        Box {
            Image(
                modifier =
                    Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 4.dp),
                painter = painterResource(id = imageRes),
                contentDescription = stringResource(id = textRes),
            )
            Text(
                modifier =
                    Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 10.dp),
                text = stringResource(id = textRes),
                style = MaterialTheme.typography.titleSmall,
                fontSize = 12.sp,
                color = if (isSelected) PetOrange else MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}
