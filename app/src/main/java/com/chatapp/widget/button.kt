package com.chatapp.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chatapp.R
import com.chatapp.ui.theme.cyan

@Composable
fun ChatAuthButton( modifier: Modifier = Modifier,title: String,onButtonClicked: () -> Unit) {
    Button(onClick = onButtonClicked,
        modifier = modifier,
        shape = RoundedCornerShape(6.dp)
    , colors = ButtonDefaults.buttonColors(containerColor = cyan),
        contentPadding = PaddingValues(vertical = 24.dp, horizontal = 28.dp)
        ){
        Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.weight(1f))
        Image(painter = painterResource(id =com.chatapp.R.drawable.ic_arrow_right),
            contentDescription = "arrow")
    }

}

@Composable
fun AddButton(title: String,modifier: Modifier = Modifier,onButtonClicked: () -> Unit) {
    Button(onClick = { onButtonClicked() },
        colors = ButtonDefaults.buttonColors(containerColor = cyan, contentColor = Color.White)
        , modifier = modifier.fillMaxWidth(.7f)
        ,contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp))
    {
        Text(text = title)

    }

}

@Composable
fun SendMessageButton(onButtonClicked: () -> Unit) {
    Button(onClick = { onButtonClicked() }, shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(vertical = 20.dp, horizontal = 20.dp)
    , colors = ButtonDefaults.buttonColors(containerColor = cyan)) {
        Text(text = "Send")
        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        Image(painter = painterResource(id = R.drawable.send), contentDescription = "send")
    }
    
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ChatAuthButton() {
//ChatAuthButton(title = "Login", modifier = Modifier,onButtonClicked = {})
//AddButton(title = stringResource(R.string.create), onButtonClicked =  {})
    SendMessageButton(){}



}
