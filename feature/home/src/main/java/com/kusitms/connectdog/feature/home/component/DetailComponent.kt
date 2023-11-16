package com.kusitms.connectdog.feature.home.component

import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.component.ConnectDogCardButton
import com.kusitms.connectdog.core.designsystem.component.ConnectDogIconTextField
import com.kusitms.connectdog.core.designsystem.component.ConnectDogOutlinedButton
import com.kusitms.connectdog.feature.home.R
import com.kusitms.connectdog.feature.home.model.Detail

@Composable
internal fun SelectDogSize(
    selected: Detail.DogSize?,
    onSelectedDogSize: (Detail.DogSize) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
        DogSizeButton(
            modifier = Modifier.weight(1f),
            isSelected = selected == Detail.DogSize.BIG,
            onSelected = {
                onSelectedDogSize(Detail.DogSize.BIG)
            },
            imageRes = R.drawable.img_big_dog,
            textRes = R.string.filter_big_dog
        )
        DogSizeButton(
            modifier = Modifier.weight(1f),
            isSelected = selected == Detail.DogSize.MIDDLE,
            onSelected = {
                onSelectedDogSize(Detail.DogSize.MIDDLE)
            },
            imageRes = R.drawable.img_middle_dog,
            textRes = R.string.filter_middle_dog
        )
        DogSizeButton(
            modifier = Modifier.weight(1f),
            isSelected = selected == Detail.DogSize.SMALL,
            onSelected = {
                onSelectedDogSize(Detail.DogSize.SMALL)
            },
            imageRes = R.drawable.img_small_dog,
            textRes = R.string.filter_small_dog
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
        modifier = modifier.defaultMinSize(minHeight = 102.dp, minWidth = 90.dp),
        isSelected = isSelected, onSelected = { onSelected() }) {
        Box {
            Image(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 4.dp),
                painter = painterResource(id = imageRes),
                contentDescription = stringResource(id = textRes)
            )
            Text(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 10.dp),
                text = stringResource(id = textRes),
                style = MaterialTheme.typography.titleSmall,
                fontSize = 12.sp,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
internal fun SelectKennel(
    selectedKennel: (Boolean?) -> Unit
) {
    var hasKennel by remember { mutableStateOf<Boolean?>(null) }
    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
        KennelButton(
            modifier = Modifier.weight(1f),
            isSelected = hasKennel == true,
            onSelected = {
                hasKennel = true
                selectedKennel(hasKennel)
            },
            textRes = R.string.filter_kennel_no_need
        )
        KennelButton(
            modifier = Modifier.weight(1f),
            isSelected = hasKennel == false,
            onSelected = {
                hasKennel = false
                selectedKennel(hasKennel)
            },
            textRes = R.string.filter_kennel_need
        )
    }
}

@Composable
private fun KennelButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    onSelected: () -> Unit,
    @StringRes textRes: Int
) {
    ConnectDogOutlinedButton(
        modifier = modifier,
        isSelected = isSelected,
        onClick = { onSelected() }
    ) {
        Text(
            text = stringResource(id = textRes),
            style = MaterialTheme.typography.titleSmall,
            fontSize = 12.sp,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
        )
    }
}


@Composable
internal fun SearchOrganization(
    modifier: Modifier = Modifier,
    onSearched: (String?) -> Unit
) {
    val (text, onTextChanged) = remember { mutableStateOf("") }
    ConnectDogIconTextField(
        modifier = modifier.fillMaxWidth(),
        text = text,
        onTextChanged = onTextChanged,
        iconRes = R.drawable.ic_search,
        placeholderRes = R.string.filter_organization_placeholder,
        onImeAction = {
            Log.d("SearchOrganization", "text = $text, onTextChanged = $onTextChanged")
            onSearched(text)
        }
    )
}