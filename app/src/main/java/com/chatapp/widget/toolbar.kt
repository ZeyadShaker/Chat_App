package com.chatapp.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopBar(title: String, onNavigationClick: (() -> Unit)? = null) {
    CenterAlignedTopAppBar(navigationIcon = {
        if (onNavigationClick != null) {
            Image(painter = painterResource(id = com.chatapp.R.drawable.ic_back),
                contentDescription = "backArrow",
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clickable {
                        onNavigationClick()
                    })
        }
    },


        title = {
            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
    )


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ChatTopBarPreview() {
    ChatTopBar(title = "Login")
}