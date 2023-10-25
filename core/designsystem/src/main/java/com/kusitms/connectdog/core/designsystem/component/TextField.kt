package com.kusitms.connectdog.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray4

@Composable
fun ConnectDogTextField(
    text: String,
    onTextChanged: (String) -> Unit,
    @StringRes labelRes: Int,
    @StringRes placeholderRes: Int,
    @StringRes supportingText: Int? = null,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false,
    @StringRes errorMessageRes: Int = 0
) {
    OutlinedTextField(
        value = text,
        onValueChange = onTextChanged,
        label = {
            Text(
                text = stringResource(id = labelRes),
                style = MaterialTheme.typography.labelMedium,
                color = Gray3
            )
        },
        placeholder = {
            Text(
                text = stringResource(id = placeholderRes),
                style = MaterialTheme.typography.bodyLarge,
                color = Gray4
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    text = stringResource(id = errorMessageRes),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            } else if (supportingText != null) {
                Text(
                    text = stringResource(id = supportingText),
                    style = MaterialTheme.typography.labelSmall,
                    color = Gray3
                )
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            errorBorderColor = MaterialTheme.colorScheme.error
        )
    )
}

@Preview
@Composable
private fun ConnectDogTextFieldPreview() {
    val (text, onTextChanged) = remember {
        mutableStateOf("")
    }
    ConnectDogTheme {
        ConnectDogTextField(
            text = text,
            onTextChanged = onTextChanged,
            labelRes = androidx.compose.ui.R.string.selected,
            placeholderRes = R.string.untitled
        )
    }
}

@Preview
@Composable
private fun ConnectDogTextFieldSupportPreview() {
    val (text, onTextChanged) = remember {
        mutableStateOf("")
    }
    ConnectDogTheme {
        ConnectDogTextField(
            text = text,
            onTextChanged = onTextChanged,
            labelRes = androidx.compose.ui.R.string.selected,
            placeholderRes = R.string.untitled,
            supportingText = R.string.untitled
        )
    }
}

@Preview
@Composable
private fun ConnectDogTextFieldErrorPreview() {
    val (text, onTextChanged) = remember {
        mutableStateOf("")
    }
    ConnectDogTheme {
        ConnectDogTextField(
            text = text,
            onTextChanged = onTextChanged,
            labelRes = androidx.compose.ui.R.string.selected,
            placeholderRes = R.string.untitled,
            isError = true,
            errorMessageRes = R.string.untitled
        )
    }
}
