package com.chatapp.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chatapp.R
import com.chatapp.ui.theme.cyan
import com.chatapp.ui.theme.lightGray

@Composable
fun ChatAuthTextField(state: MutableState<String>, errorState: String?,label:String,isPassword:Boolean = false) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {


        TextField(
            value = state.value, onValueChange = { newValue ->
                state.value = newValue

            }, colors = TextFieldDefaults
                .colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = lightGray,
                    focusedIndicatorColor = cyan,
                    errorContainerColor = Color.Transparent,
                    errorIndicatorColor = Color.Red
                ), keyboardOptions =
            if (isPassword)KeyboardOptions(keyboardType = KeyboardType.Password)else KeyboardOptions(),
            visualTransformation = if (isPassword)PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier.fillMaxWidth(.8f),
            label = {
                Text(text = label, color = Color.Gray)

            }

        )
        if (!errorState.isNullOrEmpty()) {
            Text(text = errorState, color = Color.Red, fontSize = 16.sp
                ,modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.Start))

        }

    }
}

@Composable
fun ChatMessageTextField(state: MutableState<String>) {
    OutlinedTextField(value = state.value, onValueChange = {value ->
        state.value = value
    },placeholder = {
        Text(text = stringResource(R.string.type_a_message))

    }, shape = RoundedCornerShape(topEnd = 12.dp)
        ,modifier = Modifier.fillMaxWidth(.65f))



}

@Preview(showBackground = true)
@Composable
private fun ChatMessageTextFieldPreview() {
    val state = rememberSaveable {
        mutableStateOf("Type a message")
    }
    ChatMessageTextField(state)
    
}

@Preview
@Composable
private fun ChatAuthTextFieldPreview() {
    val emailState = rememberSaveable {
        mutableStateOf("mohamed@gmail.com")
    }
    ChatAuthTextField(emailState,"",label = "Email")
}