package com.chatapp.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chatapp.R
import com.chatapp.widget.ChatAuthTextField
import com.chatapp.widget.ChatTopBar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chatapp.home.HomeActivity
import com.chatapp.widget.ChatAuthButton
import com.chatapp.widget.LoadingDialog

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegisterScreen(onBackClick = {
                finish()
            }){
                finish()
            }

        }
    }
}

@Composable
fun RegisterScreen(viewModel: RegisterViewModel=viewModel(), onBackClick: () -> Unit,onLoginSuccess: () -> Unit) {
    Scaffold(topBar = {
        ChatTopBar(title = stringResource(R.string.create_account)) {
            onBackClick()
        }
    }) { paddingValues ->
        paddingValues
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.bg),
                    contentScale = ContentScale.FillBounds
                )
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.35f))
            ChatAuthTextField(state =viewModel.firstNameState
                , errorState =viewModel.firstNameErrorState.value
                , label = stringResource(
                R.string.first_name) )
            Spacer(modifier = Modifier.padding(8.dp))
            ChatAuthTextField(state =viewModel.emailState
                , errorState =viewModel.emailErrorState.value
                , label = stringResource(
                    R.string.email_address) )
            Spacer(modifier = Modifier.padding(8.dp))
            ChatAuthTextField(state =viewModel.passwordState
                , errorState =viewModel.passwordErrorState.value
                , label = stringResource(
                    R.string.password) , isPassword = true)
            Spacer(modifier = Modifier.weight(1f))
            ChatAuthButton(title = stringResource(id = R.string.create_account),
                modifier = Modifier.padding(16.dp) ) {
                viewModel.authenticateUser()
                
            }

        }

    }
    LoadingDialog(isLoading = viewModel.isLoading)
 TriggerEvent(event = viewModel.events.value){
     onLoginSuccess()
 }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegisterScreenPreview() {
    RegisterScreen(onBackClick = {}){}

}

@Composable
fun TriggerEvent(event: RegisterViewEvent, viewModel: RegisterViewModel = viewModel(),onLoginSuccess: () -> Unit) {
    when (event) {
        RegisterViewEvent.Idle -> {
        }

        is RegisterViewEvent.NavigateToHome -> {
            val context = LocalContext.current
            LaunchedEffect(Unit) {
                val intent= Intent(context, HomeActivity::class.java)
                context.startActivity(intent)
                viewModel.resetToIdle()
                onLoginSuccess()
            }
        }
    }
}
