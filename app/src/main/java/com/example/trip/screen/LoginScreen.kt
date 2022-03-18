package com.example.trip.screen

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.trip.viewModel.LoginViewModel
import com.example.trip.R;
import com.example.trip.components.Message
import com.example.trip.components.PasswordField
import com.example.trip.viewModel.LoginViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

@Composable
fun LoginScreen(navController: NavHostController) {
    val context = LocalContext.current
    val application = context.applicationContext as Application

    //viewModel(LocalContext.current as ComponentActivity)
    val model: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(application),

    )

    Column(
        modifier = Modifier.padding(all = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()
        Form(model = model, nav = navController)
    }
}

@Composable
fun Logo() {
    Image(
        painterResource(id = R.drawable.viagem),
        contentDescription = "Logo",
        alignment = Alignment.CenterStart,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth()
    )
}

suspend fun startTimer(time: Long, onTimerEnd: () -> Unit) {
    delay(timeMillis = time)
    onTimerEnd()
}

@Composable
fun Form(model: LoginViewModel, nav: NavHostController) {
    val ctx = LocalContext.current
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = model.user,
            onValueChange =  { model.user = it },
            label = {
                Text(text = stringResource(R.string.user))
            },
        )
        PasswordField(value = model.password, onChange = { model.password = it})

        Button(
            onClick = {
                model.login(
                    onSuccess = {
                        Toast.makeText(ctx, "Login successful", Toast.LENGTH_SHORT).show()
                        nav.navigate("menu/${model.user}")
                    },
                    onError = {
                        Toast.makeText(ctx, it, Toast.LENGTH_SHORT).show()
                    }
                )
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Login")
        }

        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.padding(top = 16.dp)) {
            Text(
                text = "Register",
                style = TextStyle(
                    textDecoration = TextDecoration.Underline,
                    color = MaterialTheme.colors.secondaryVariant
                ),
                modifier = Modifier.clickable { nav.navigate("register") },

                )
            Spacer(modifier = Modifier.padding(start = 16.dp))
            Text(
                text = "Forgot you password?",
                style = TextStyle(
                    textDecoration = TextDecoration.Underline,
                    color = MaterialTheme.colors.secondaryVariant
                ),
                modifier = Modifier.clickable { nav.navigate("forgetPassword") },
            )
        }

    }
}


