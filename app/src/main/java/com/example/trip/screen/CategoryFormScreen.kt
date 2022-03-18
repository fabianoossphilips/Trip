package com.example.trip.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.trip.TripConst
import com.example.trip.viewModel.CategoryViewModel


@Composable
fun CategoryFormScreen(model: CategoryViewModel, app: TripAppState) {
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(all = 16.dp)
            .fillMaxWidth(),
    ) {
        OutlinedTextField(value = model.description , onValueChange = { model.description = it },
            label = {
                Text(text = "Description")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                model.save(
                    onSuccess = {
                        Toast.makeText(context,"New trip has been registered", Toast.LENGTH_SHORT).show()
                        app.navigate("category")
                    },
                    onError = {
                        Log.i(TripConst.TAG, "onError ${it}")
                        app.showSnackBar(it)
                    }
                )
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Save")

        }
    }
}



