package com.example.trip.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trip.database.TripDatabase
import com.example.trip.repository.TripRepository
import com.example.trip.repository.UserRepository


class TripViewModelFactory(private val app: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val tripRepository = TripRepository(app)
        return TripViewModel(tripRepository) as T
    }
}