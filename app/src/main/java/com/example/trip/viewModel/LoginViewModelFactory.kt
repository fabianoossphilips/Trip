package com.example.trip.viewModel

import android.app.Application
import android.view.View
import android.widget.ViewSwitcher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trip.repository.UserRepository

class LoginViewModelFactory(private val app: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val userRepository = UserRepository(app)
        return LoginViewModel(userRepository) as T
    }
}