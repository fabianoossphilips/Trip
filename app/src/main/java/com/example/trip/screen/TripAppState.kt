package com.example.trip.screen

import androidx.compose.material.ScaffoldState
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class TripAppState(
    val scaffoldState: ScaffoldState,
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val user: String

) {

    fun showSnackBar(message: String) {
        coroutineScope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                message = message,
            )
        }
    }


    fun navigate(route: String) {
        navController.navigate(route)
    }


    fun isShowNewButton(currentRoute: String?): Boolean {
        return currentRoute == "trip" ||
               currentRoute == "category"
    }
}