package com.chatapp.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chatapp.ui.theme.ChatAppTheme
import com.chatapp.widget.ChatAuthTextField
import com.chatapp.widget.ChatTopBar
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chatapp.home.HomeActivity
import com.chatapp.register.RegisterActivity
import com.chatapp.ui.theme.black2
import com.chatapp.widget.ChatAuthButton
import com.chatapp.widget.LoadingDialog


class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatAppTheme {
                LoginScreen(){
                    finish()
                }

            }
        }
    }
}

@Composable
fun LoginScreen(viewModel: LoginViewModel = viewModel(),onLoginSuccess: ()->Unit ) {
    Scaffold(topBar = {
        ChatTopBar(title = stringResource(com.chatapp.R.string.login))

    }) { paddingValues ->
        paddingValues
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = com.chatapp.R.drawable.bg),
                    contentScale = ContentScale.FillBounds
                )
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(.35f))
            Text(
                text = stringResource(com.chatapp.R.string.welcome_back),
                color = Color.Black,
                fontWeight = FontWeight.Bold, fontSize = 24.sp, modifier = Modifier.padding(12.dp)
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            ChatAuthTextField(
                state = viewModel.emailState,
                errorState = viewModel.emailErrorState.value,
                label = stringResource(com.chatapp.R.string.email)
            )
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            ChatAuthTextField(
                state = viewModel.passwordState,
                label = stringResource(com.chatapp.R.string.password),
                errorState = viewModel.passwordErrorState.value, isPassword = true
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            ChatAuthButton(modifier = Modifier
                .fillMaxWidth(.9f)
                .align(Alignment.CenterHorizontally),
                title = stringResource(id = com.chatapp.R.string.login),
                onButtonClicked = {
                    viewModel.authenticateUser()
                })
            Spacer(modifier = Modifier.padding(vertical = 16.dp))
            TextButton(
                onClick = { viewModel.navigateToRegistration() } ,
                colors = ButtonDefaults.textButtonColors(contentColor = black2),
                modifier = Modifier.padding(horizontal = 16.dp)){
                Text(
                    text = stringResource(com.chatapp.R.string.or_create_my_account),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )

            }

        }


    }
    LoadingDialog(isLoading = viewModel.isLoading)
TriggerEvent(events = viewModel.events.value){
    onLoginSuccess()
}
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(){}

}

@Composable
fun TriggerEvent(events: LoginViewEvent, viewModel: LoginViewModel = viewModel(),onLoginSuccess: ()->Unit) {
    when(events){
        LoginViewEvent.Idle -> {
        }

        is LoginViewEvent.NavigateToHome -> {
            val context = LocalContext.current
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
            viewModel.resetToIdle()
            onLoginSuccess()
        }
        LoginViewEvent.NavigateToRegistration -> {
            val context = LocalContext.current
            LaunchedEffect(Unit) {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
                viewModel.resetToIdle()

        }}
    }

}

