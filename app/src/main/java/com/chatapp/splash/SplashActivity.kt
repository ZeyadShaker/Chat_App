package com.chatapp.splash

import android.content.Intent
import android.hardware.TriggerEvent
import android.os.Bundle
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.chatapp.R
import com.chatapp.login.LoginActivity
import com.chatapp.ui.theme.ChatAppTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import android.os.Handler
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.chatapp.Constants
import com.chatapp.home.HomeActivity

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SplashScreen{
                finish()
            }
        }
    }
}


@Composable
fun SplashScreen(viewModel: SplashViewModel = viewModel(),onFinish:()->Unit) {
    LaunchedEffect(Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.navigate()
        }, 1500)

    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "chatLogo",
            modifier = Modifier.fillMaxHeight(.2f),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.signature),
            contentDescription = "signature",
            modifier = Modifier.fillMaxHeight(.20f),
            contentScale = ContentScale.Crop
        )

    }
    com.chatapp.splash.TriggerEvent(events = viewModel.events.value){
        onFinish()
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SplashScreenPreview() {
    SplashScreen(){}

}

@Composable
fun TriggerEvent(viewModel: SplashViewModel = viewModel(), events: SplashViewEvents,onFinish:()->Unit) {
    val context = LocalContext.current
    when (events) {
        SplashViewEvents.Idle -> {}
        is SplashViewEvents.NavigateToHome -> {
            val intent = Intent(context, HomeActivity::class.java)
            val user=events.user
            intent.putExtra(Constants.USER_INTENT_EXTRA,user)
            context.startActivity(intent)
            //viewModel.resetToIdle()
            onFinish()
        }

        SplashViewEvents.NavigateToLogin -> {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
            //viewModel.resetToIdle()
            onFinish()
        }
    }

}