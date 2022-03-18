package com.example.trip.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trip.TripConst
import com.example.trip.entity.Trip
import com.example.trip.entity.TripType
import com.example.trip.viewModel.TripViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlin.math.exp


@Composable
fun TripScreen(model: TripViewModel,
               onAddTrip: () -> Unit,
               onEditTrip: (trip: Trip) -> Unit,
               onDelete: (trip: Trip) -> Unit,
               onAddSpent: (trip: Trip) -> Unit
) {
    model.load()
    TripList(trips = model.trips.value, onEditTrip = {
        onEditTrip(it)
    })
}


@Composable
fun TripList(trips: List<Trip>, onEditTrip: (trip: Trip) -> Unit) {

   LazyColumn(
       contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
       verticalArrangement = Arrangement.spacedBy(4.dp)) {
       items(items = trips) {
               trip -> TripItem(trip = trip, onEditTrip = {
                   onEditTrip(it)
                })
       }
   }
}

@Composable
fun DropDownMenuTrip(expanded: Boolean, onClose: () -> Unit, onEditTrip: () -> Unit) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onClose() }
    ) {
        DropdownMenuItem(onClick = { onClose() }) {
            Text("Add a spent")
        }
        Divider()
        DropdownMenuItem(onClick = {
            onClose()
            onEditTrip()
        }) {
            Text("Edit trip")
        }
        DropdownMenuItem(onClick = { onClose() }) {
            Text("Remove trip")
        }
    }
}

@Composable
fun TripItem(trip: Trip, onEditTrip: (trip: Trip) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Card(elevation = 16.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp)
            .clickable { expanded = true }
    ) {

        
        Row(modifier = Modifier.padding(start = 8.dp)) {
            Image(imageVector = Icons.Default.Business, contentDescription = "",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 4.dp)
            )
            Column(modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f) ) {
                Text(text = trip.destiny, style = MaterialTheme.typography.subtitle2)
                Row() {
                    Text(text = "${trip.startDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))} - ${trip.endDate.format(
                        DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))}")
                }
            }
            IconButton(onClick = { expanded = true }) {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "")
                DropDownMenuTrip(expanded = expanded,  onClose = {expanded = false}, onEditTrip = { onEditTrip(trip) })
            }

        }

    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    val trips = listOf<Trip>( Trip(destiny = "ab", user = "", type = TripType.LEISURE, startDate = LocalDate.now(), endDate = LocalDate.now(), budget = 0.0, amountPeople = 0),
        Trip(destiny = "Sao paulo", user = "", type = TripType.LEISURE, startDate = LocalDate.now(), endDate = LocalDate.now(), budget = 0.0, amountPeople = 0)
    )
    TripList(trips = trips, onEditTrip = {

    })
}