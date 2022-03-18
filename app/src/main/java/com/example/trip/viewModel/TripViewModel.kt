package com.example.trip.viewModel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.trip.repository.TripRepository
import androidx.lifecycle.viewModelScope
import com.example.trip.TripConst
import com.example.trip.entity.Trip
import com.example.trip.entity.TripType
import kotlinx.coroutines.launch
import java.time.LocalDate

class TripViewModel(private val tripRepository: TripRepository): ViewModel() {

    var trips: MutableState<List<Trip>> = mutableStateOf(listOf())

    var tripId by mutableStateOf(0)

    var destiny by mutableStateOf("")

    var type by mutableStateOf(TripType.LEISURE)

    var startDate by mutableStateOf(LocalDate.now())

    var endDate : LocalDate? by mutableStateOf(null)

    var budget by mutableStateOf(0.0)

    var amountPeople by mutableStateOf(0)

    var user by mutableStateOf("")

    var isBusy by mutableStateOf(false)

    var errors by mutableStateOf(mutableMapOf<String, String>())
        private set

    init {
        Log.i(TripConst.TAG, "Init trip view Model")
    }

    private fun isValid(): Boolean {
        this.errors.clear()
        if (user.isEmpty()) {
            errors.put("user", "User is invalid")
        }
        if (destiny.isEmpty()) {
            errors.put("destiny", "Destiny is required")
        }
        if (endDate == null) {
            errors.put("endDate", "End date is required")
            if (endDate?.isBefore(startDate) == true) {
                errors.put("endDate", "End date is before start date")
            }
        }
        if (budget < 0) {
            errors.put("bugdget", "Budget must be greater than zero")
        }
        if (amountPeople < 0) {
            errors.put("amountPeople", "Amount people must be greater than zero")
        }
        return errors.size == 0
    }



    fun save(onSuccess:() -> Unit, onError: (String) -> Unit) {
        if (isValid()) {
            this.isBusy = true
            viewModelScope.launch {
                val trip = Trip(
                    id = tripId,
                    destiny = destiny,
                    amountPeople = amountPeople,
                    budget = budget,
                    endDate = endDate ?: LocalDate.now(),
                    startDate = startDate,
                    type = type,
                    user = user
                )
                try {
                    tripRepository.save(trip)
                    onSuccess()
                }
                catch (e : Exception) {
                    onError(e.localizedMessage ?: "Unknown error")
                }
                finally {
                    isBusy = false
                }
            }
        }
        else {
            onError(FormUtil.getErrorMessage(this.errors))
        }
    }

    fun load() {
        viewModelScope.launch {
            Log.i(TripConst.TAG, "Load trip")
            trips.value = tripRepository.findByUser(user)
        }
    }

    fun findById(id: Int) {
        viewModelScope.launch {
            Log.i(TripConst.TAG, "Find by Id: ${id}")
            val trip = tripRepository.findById(id)
            tripId = trip.id
            destiny = trip.destiny
            type = trip.type
            startDate = trip.startDate
            endDate = trip.endDate
            budget = trip.budget
            amountPeople = trip.amountPeople
        }
    }
}

