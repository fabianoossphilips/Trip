package com.example.trip.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trip.repository.UserRepository


class RegisterNewVieModelFactory(private val app: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val userRepository = UserRepository(app)
        return RegisterNewUserViewModel(userRepository) as T
    }
}