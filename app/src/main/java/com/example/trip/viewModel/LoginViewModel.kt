package com.example.trip.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trip.repository.UserRepository
import io.reactivex.internal.operators.single.SingleDoOnSuccess
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository): ViewModel() {

    var user by mutableStateOf("")

    var password by mutableStateOf("")

    var isInvalidForm by mutableStateOf(false)

    private fun validateFields() {
        this.isInvalidForm = (user.isBlank() || password.isBlank())
    }

    fun login(onSuccess: () -> Unit, onError: (String) -> Unit) {
        this.validateFields()
        if (!isInvalidForm) {
            viewModelScope.launch {
                val u = userRepository.findByUserAndPassword(user, password)
                if (u != null) {
                    onSuccess()
                }
                else {
                    onError("User/password not found")
                }
            }
        }
        else {
            onError("Inform user and password")
        }

    }

}
