package com.kusitms.connectdog.core.designsystem.component

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kusitms.connectdog.core.designsystem.theme.Gray3
import com.kusitms.connectdog.core.designsystem.theme.Gray4
import com.kusitms.connectdog.core.designsystem.theme.Gray5

@Composable
fun ConnectDogTextField(
    text: String,
    onTextChanged: (String) -> Unit,
    label: String,
    enabled: Boolean = true,
    placeholder: String,
    @StringRes supportingText: Int? = null,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false,
    @SuppressLint("PrivateResource") @StringRes errorMessageRes: Int = androidx.compose.ui.R.string.default_error_message,
    height: Int = 65
) {
    val visualTransformation =
        if (keyboardType == KeyboardType.Password) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }

    OutlinedTextField(
        visualTransformation = visualTransformation,
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp),
        value = text,
        onValueChange = { onTextChanged(it) },
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
        keyboardOptions =
        KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        isError = isError,
        enabled = enabled,
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
        colors =
        OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Gray5,
            errorBorderColor = MaterialTheme.colorScheme.error
        )
//        textStyle = LocalTextStyle.current.copy(
//            baselineShift = BaselineShift(if(height == 65) 0f else 2.5f)
//        )
    )
}

@OptIn(ExperimentalComposeUiApi::class)
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
    buttonLabel: String,
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
            onTextChanged = {
                onTextChanged(it)
            },
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
                Log.d("testts", text)
                onClick(text)
            }
        )
    }
}

// @Preview
// @Composable
// private fun ConnectDogTextFieldPreview() {
//    val (text, onTextChanged) =
//        remember {
//            mutableStateOf("")
//        }
//    ConnectDogTheme {
//        ConnectDogTextField(
//            text = text,
//            onTextChanged = onTextChanged,
//            label = "텍스트",
//            placeholder = "비밀번호"
//        )
//    }
// }

// @Preview
// @Composable
// private fun ConnectDogTextFieldSupportPreview() {
//    val (text, onTextChanged) = remember {
//        mutableStateOf("")
//    }
//    ConnectDogTheme {
//        ConnectDogTextField(
//            text = text,
//            onTextChanged = onTextChanged,
//            label = "텍스트"
//            supportingText = R.string.untitled
//        )
//    }
// }
//
// @Preview
// @Composable
// private fun ConnectDogTextFieldErrorPreview() {
//    val (text, onTextChanged) = remember {
//        mutableStateOf("")
//    }
//    ConnectDogTheme {
//        ConnectDogTextField(
//            text = text,
//            onTextChanged = onTextChanged,
// //            labelRes = androidx.compose.ui.R.string.selected,
// //            placeholderRes = R.string.untitled,
//            isError = true,
//            errorMessageRes = R.string.untitled
//        )
//    }
// }

// @Preview
// @Composable
// private fun ConnnectDogTextFieldWithButtonPreview() {
//    ConnectDogTheme {
//        ConnectDogTextFieldWithButton(width = 100, height = 30, label = "test", padding = 10)
//    }
// }
