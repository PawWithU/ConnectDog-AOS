package com.kusitms.connectdog.core.designsystem.component.button

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kusitms.connectdog.core.designsystem.R
import com.kusitms.connectdog.core.designsystem.theme.Gray2
import com.kusitms.connectdog.core.designsystem.theme.Gray5
import com.kusitms.connectdog.core.designsystem.theme.PetOrange

@Composable
fun BookmarkButton(
    isBookmark: Boolean,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    var isActive by remember { mutableStateOf(isBookmark) }
    val shape = RoundedCornerShape(12.dp)
    val (imageResource, setImageResource) =
        remember {
            mutableIntStateOf(if (isActive) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark)
        }
    val (borderColor, setBorderColor) = remember { mutableStateOf(if (isActive) PetOrange else Gray5) }
    val imagePainter: Painter = painterResource(id = imageResource)

    Button(
        onClick = {
            isActive = !isActive
            setImageResource(if (isActive) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark)
            setBorderColor(if (isActive) PetOrange else Gray5)
            Log.d("testsss", isActive.toString())
            if (isActive) {
                onSaveClick()
            } else {
                onDeleteClick()
            }
        },
        contentPadding = PaddingValues(vertical = 16.dp),
        shape = shape,
        modifier =
            Modifier
                .width(56.dp)
                .height(56.dp)
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = shape,
                ),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Gray2),
    ) {
        Image(
            painter = imagePainter,
            contentDescription = null,
        )
    }
}
