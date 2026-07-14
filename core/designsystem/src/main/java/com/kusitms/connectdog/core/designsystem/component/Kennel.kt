package com.kusitms.connectdog.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.R

@Composable
fun SelectKennel(
    hasKennel: Boolean?,
    selectedKennel: (Boolean) -> Unit,
) {
    val selectedState = remember { mutableStateOf(hasKennel) }

    Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
        KennelButton(
            modifier = Modifier.weight(1f),
            isSelected = selectedState.value == true,
            onSelected = {
                selectedKennel(true)
                selectedState.value = true
            },
            textRes = R.string.filter_kennel_no_need,
        )
        KennelButton(
            modifier = Modifier.weight(1f),
            isSelected = selectedState.value == false,
            onSelected = {
                selectedKennel(false)
                selectedState.value = false
            },
            textRes = R.string.filter_kennel_need,
        )
    }
}

@Composable
private fun KennelButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    onSelected: () -> Unit,
    @StringRes textRes: Int,
) {
    ConnectDogOutlinedButton(
        modifier = modifier,
        isSelected = isSelected,
        onClick = { onSelected() },
    ) {
        Text(
            text = stringResource(id = textRes),
            style = MaterialTheme.typography.titleSmall,
            fontSize = 12.sp,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
        )
    }
}
