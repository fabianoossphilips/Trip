package com.example.trip.components

import androidx.compose.material.ScaffoldState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


object Message  {
    fun showSnackBar(message: String, coroutineScope: CoroutineScope, scaffoldState: ScaffoldState) {
        coroutineScope.launch {
            val snackbar = scaffoldState.snackbarHostState.showSnackbar(
                message = message,
            )
        }
    }
}