package com.example.trip.screen

import android.app.Application
import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.trip.TripConst
import com.example.trip.components.Message
import com.example.trip.entity.TripType
import com.example.trip.viewModel.LoginViewModel
import com.example.trip.viewModel.LoginViewModelFactory
import com.example.trip.viewModel.TripViewModel
import com.example.trip.viewModel.TripViewModelFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.FormatStyle

@Composable
fun TripFormScreen(nav: NavController, scaffoldState: ScaffoldState, model: TripViewModel) {
    FormTrip(scaffoldState, model, nav)
}


@Composable
fun FormTrip(scaffoldState: ScaffoldState, model: TripViewModel, nav: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(all = 16.dp)
            .fillMaxWidth(),
    ) {
        OutlinedTextField(value = model.destiny , onValueChange = { model.destiny = it },
            label = {
                Text(text = "Destiny")
            },
            modifier = Modifier.fillMaxWidth()
        )
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)

        ) {
            RadioButton(selected = model.type == TripType.LEISURE, onClick = { model.type = TripType.LEISURE })
            Text(text = "Leisure")
            Spacer(modifier = Modifier.size(16.dp))
            RadioButton(selected = model.type == TripType.BUSINESS, onClick = { model.type = TripType.BUSINESS })
            Text(text = "Business")
        }

        Row(modifier = Modifier.fillMaxWidth(),  horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(value = model.startDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)), onValueChange = {  },
                label = {
                    Text(text = "Start date")
                },
                modifier = Modifier.weight(1f),
                trailingIcon = {
                    IconButton(onClick = { com.example.trip.components.DatePicker.showDatePicker(context, model.startDate, { model.startDate = it}) }) {
                        Icon(imageVector = Icons.Filled.CalendarToday, contentDescription = "")
                    }
                },
                readOnly = true
            )
            OutlinedTextField(value = model.endDate?.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)) ?: "" , onValueChange = {  },
                label = {
                    Text(text = "End date")
                },

                modifier = Modifier.weight(1f),
                trailingIcon = {
                    IconButton(onClick = { com.example.trip.components.DatePicker.showDatePicker(context, model.endDate ?: LocalDate.now() , { model.endDate = it}) }) {
                        Icon(imageVector = Icons.Filled.CalendarToday, contentDescription = "")
                    }
                },
                readOnly = true

            )
        }
        OutlinedTextField(value = model.budget.toString() , onValueChange = { model.budget = it.toDouble() },
            label = {
                Text(text = "Budget")
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )

        )
        OutlinedTextField(value = model.amountPeople.toString() ,
            onValueChange = {
                Log.i(TripConst.TAG, "value: ${it}")
                if (it.isDigitsOnly() && it.length > 0)
                    model.amountPeople = it.toInt()
            },
            label = {
                Text(text = "Amount of people")
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        
        Button(
            onClick = {
                model.save(
                    onSuccess = {
                        val msg = if (model.tripId == 0) "New trip has been registered" else "Trip was changed"
                        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show()
                        nav.navigate("trip")
                    },
                    onError = {
                        Log.i(TripConst.TAG, "onError ${it}")
                        Message.showSnackBar(message = it, coroutineScope, scaffoldState)
                    }
                )
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Save")
            
        }




        
    }
}



