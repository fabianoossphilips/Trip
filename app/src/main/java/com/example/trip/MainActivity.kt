package com.example.trip

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trip.screen.LoginScreen
import com.example.trip.screen.MenuScreen
import com.example.trip.screen.RegisterScreen
import com.example.trip.ui.theme.TripTheme


class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TripTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    App()
                }
            }
        }

    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    val ctx = LocalContext.current

    NavHost(navController = navController, startDestination = "login" ) {
        composable("login") { LoginScreen(navController = navController) }
        composable("forgetPassword") { ForgetPassword() }
        composable("register") { RegisterScreen(nav = navController) }
        composable("menu/{user}") {
            val user = it.arguments?.getString("user")
            if (user == null) {
                Toast.makeText(ctx, "Unknow user", Toast.LENGTH_SHORT).show()
            }
            else {
                MenuScreen(user)
            }
        }
    }
}

@Composable
fun ForgetPassword() {
    Text(text = "Forget password")
}




