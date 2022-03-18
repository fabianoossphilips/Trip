package com.example.trip.screen

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trip.R
import com.example.trip.TripConst
import com.example.trip.components.Message
import com.example.trip.components.PasswordField
import com.example.trip.viewModel.RegisterNewUserViewModel
import com.example.trip.viewModel.RegisterNewVieModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(nav: NavController) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        topBar = { RegisterTopBar(onBack = {
            nav.navigateUp()
        }) },
        content = {
           Form(nav, scaffoldState = scaffoldState)
        },
        scaffoldState = scaffoldState
    )
}


@Composable
fun RegisterTopBar(onBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = "Register new user")
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "")
            }
        }
    )
}

@Composable
fun Form(nav: NavController, scaffoldState: ScaffoldState) {
    val context = LocalContext.current
    val application = context.applicationContext as Application

    val coroutineScope = rememberCoroutineScope()
    var messageState by remember {
        mutableStateOf("")
    }

    val model: RegisterNewUserViewModel = viewModel(
        factory = RegisterNewVieModelFactory(application)
    )

    Column(
        modifier = Modifier
            .padding(all = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (model.user.isNotBlank()) {
            Text(text = "Hello, ${model.user.trim()}", style = MaterialTheme.typography.body1)
        }

        OutlinedTextField(
            value = model.user,
            onValueChange = {
                model.onChangeUser(it)
            },
            label = {
                Text(text = stringResource(id = R.string.user))
            },
            singleLine = true,
            isError = model.errors.get("user") != null,
        )
        if (model.errors.get("user") != null) {
            Text(
                text = model.errors.get("user") ?: "",
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
            )
        }

        OutlinedTextField(
            value = model.email,
            onValueChange = { model.email = it },
            label = {
                Text(text = stringResource(R.string.email))
            },
        )

        PasswordField(
            value = model.password,
            onChange = { model.password = it },
        )

        PasswordField(
            value = model.confirmPassword, onChange = { model.confirmPassword = it },
            label = stringResource(id = R.string.confirm_password)
        )
        Button(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {
                model.register(
                    onSuccess = {
                        Toast.makeText(
                            context,
                            "Welcome user ${model.user}",
                            Toast.LENGTH_SHORT
                        ).show()
                        nav.navigateUp()
                        Log.i(TripConst.TAG, "onSuccess")
                    },
                    onError = {
                        Log.i(TripConst.TAG, "onError ${it}")
                        Message.showSnackBar(message = it, coroutineScope, scaffoldState)
                    }
                )
            }) {

            Text(text = "Register new user")
        }
    }

}




