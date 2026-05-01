package com.kusitms.connectdog.core.designsystem.component

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.theme.ConnectDogTheme
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray4
import com.kusitms.connectdog.core.designsystem.theme.Gray5
import com.kusitms.connectdog.core.designsystem.theme.PetOrange
import kotlinx.coroutines.delay

@Composable
fun ConnectDogTextField(
    text: String,
    onTextChanged: (String) -> Unit,
    label: String,
    enabled: Boolean = true,
    placeholder: String,
    borderColor: Color = Gray5,
    @StringRes supportingText: Int? = null,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false,
    @SuppressLint("PrivateResource") @StringRes errorMessageRes: Int = R.string.default_error_message,
    height: Int = 65,
    showCharCount: Boolean = false,
    maxLength: Int = Int.MAX_VALUE,
    modifier: Modifier = Modifier
) {
    val visualTransformation =
        if (keyboardType == KeyboardType.Password) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }

    Column(modifier = modifier.fillMaxWidth()) {
        var textFieldValue by remember {
            mutableStateOf(TextFieldValue(text = text))
        }

        LaunchedEffect(text) {
            if (textFieldValue.text != text) {
                textFieldValue = TextFieldValue(
                    text = text,
                    selection = TextRange(text.length)
                )
            }
        }

        Box(
            modifier = Modifier
                .height(height.dp)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                visualTransformation = visualTransformation,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxSize(),
                value = textFieldValue,
                onValueChange = { newValue ->
                    if (newValue.text.length <= maxLength) {
                        textFieldValue = newValue
                        onTextChanged(newValue.text)
                    }
                },
                label = {
                    Text(
                        text = label,
                        color = Gray3
                    )
                },
                placeholder = {
                    Text(
                        text = placeholder,
                        color = Gray4
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = imeAction
                ),
                singleLine = (height == 65),
                shape = RoundedCornerShape(12.dp),
                isError = isError,
                enabled = enabled,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = borderColor,
                    errorBorderColor = MaterialTheme.colorScheme.error
                )
            )
        }
        if (showCharCount) {
            Text(
                text = "${text.length} / 200",
                color = Gray4,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 4.dp, end = 4.dp)
            )
        }
    }
}

@Composable
fun ConnectDogTextField(
    text: String,
    onTextChanged: (String) -> Unit,
    enabled: Boolean = true,
    placeholder: String,
    borderColor: Color = Gray5,
    @StringRes supportingText: Int? = null,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false,
    @SuppressLint("PrivateResource") @StringRes errorMessageRes: Int = R.string.default_error_message,
    height: Int = 65,
    showCharCount: Boolean = false,
    modifier: Modifier = Modifier
) {
    val visualTransformation =
        if (keyboardType == KeyboardType.Password) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }

    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .height(height.dp)
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                visualTransformation = visualTransformation,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxSize(),
                value = text,
                onValueChange = { onTextChanged(it) },
                placeholder = {
                    Text(
                        text = placeholder,
                        color = Gray4
                    )
                },
                keyboardOptions =
                KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = imeAction
                ),
                singleLine = (height == 65),
                shape = RoundedCornerShape(12.dp),
                isError = isError,
                enabled = enabled,
                colors =
                OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = borderColor,
                    errorBorderColor = MaterialTheme.colorScheme.error
                )
            )
        }
        if (showCharCount) {
            Text(
                text = "${text.length} / 200",
                color = Gray4,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 4.dp, end = 4.dp)
            )
        }
    }
}

@Composable
fun ConnectDogIconTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (String) -> Unit,
    iconRes: Int,
    @StringRes placeholderRes: Int,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: () -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = text,
        onValueChange = onTextChanged,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        leadingIcon = {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = "icon",
                tint = Gray4,
                modifier = Modifier.size(20.dp)
            )
        },
        textStyle = MaterialTheme.typography.titleSmall.copy(fontSize = 14.sp),
        placeholder = {
            Text(
                text = stringResource(id = placeholderRes),
                style = MaterialTheme.typography.titleSmall.copy(fontSize = 14.sp),
                color = Gray4
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
            onImeAction.invoke()
        }),
        visualTransformation = visualTransformation
    )
}

@Composable
fun ConnectDogTextFieldWithButton(
    text: String,
    onTextChanged: (String) -> Unit,
    width: Int,
    height: Int,
    textFieldLabel: String,
    placeholder: String,
    buttonLabel: String = "중복 확인",
    borderColor: Color = Gray5,
    keyboardType: KeyboardType = KeyboardType.Text,
    padding: Int,
    onClick: (String) -> Unit = {},
    isError: Boolean = false
) {
    Box {
        ConnectDogTextField(
            text = text,
            label = textFieldLabel,
            placeholder = placeholder,
            keyboardType = keyboardType,
            onTextChanged = { onTextChanged(it) },
            borderColor = borderColor,
            isError = isError
        )

        ConnectDogOutlinedButton(
            width = width,
            height = height,
            text = buttonLabel,
            padding = padding,
            modifier = Modifier
                .padding(top = 6.dp, end = 16.dp)
                .align(Alignment.CenterEnd),
            onClick = {
                onClick(text)
            }
        )
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun ConnectDogTextFieldWithTimer(
    initialMinute: Int = 5,
    initialSecond: Int = 0,
    text: String,
    onTextChanged: (String) -> Unit,
    textFieldLabel: String,
    placeholder: String,
    borderColor: Color = Gray5,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false,
    maxLength: Int = Int.MAX_VALUE
) {
    var minute by remember { mutableIntStateOf(initialMinute) }
    var second by remember { mutableIntStateOf(initialSecond) }

    LaunchedEffect(key1 = minute, key2 = second) {
        if (minute > 0 || second > 0) {
            delay(1000L)
            if (second > 0) {
                second--
            } else {
                if (minute > 0) {
                    minute--
                    second = 59
                }
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center
    ) {
        ConnectDogTextField(
            text = text,
            label = textFieldLabel,
            placeholder = placeholder,
            keyboardType = keyboardType,
            onTextChanged = { if (it.length <= maxLength) onTextChanged(it) },
            borderColor = borderColor,
            isError = isError,
            maxLength = maxLength
        )
        Text(
            text = String.format("%02d:%02d", minute, second),
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp),
            color = PetOrange
        )
    }
}

@Preview
@Composable
private fun ConnectDogTextFieldPreview() {
    val (text, onTextChanged) =
        remember {
            mutableStateOf("")
        }
    ConnectDogTheme {
        ConnectDogTextField(
            text = text,
            onTextChanged = onTextChanged,
            label = "텍스트",
            placeholder = "비밀번호"
        )
    }
}
