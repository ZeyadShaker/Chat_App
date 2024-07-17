package com.chatapp.widget

import android.app.Dialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.chatapp.ui.theme.cyan

@Composable
fun LoadingDialog(isLoading: MutableState<Boolean>) {
    if (isLoading.value)
    Dialog(onDismissRequest = { /*TODO*/ }) {
        CircularProgressIndicator(color = cyan,
            modifier = Modifier.
            background(Color.White, shape = RoundedCornerShape(12.dp)).padding(32.dp)
        )

    }

}