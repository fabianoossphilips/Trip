package com.example.trip.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trip.entity.User
import com.example.trip.repository.UserRepository
import kotlinx.coroutines.launch

class RegisterNewUserViewModel(private val userRepository: UserRepository): ViewModel() {

    var user by mutableStateOf("")
        private set

    var email by mutableStateOf("")

    var password by mutableStateOf("")

    var confirmPassword by mutableStateOf("")

    var errors by mutableStateOf(mutableMapOf<String, String>())
        private set

    fun validate(): Boolean {
        this.errors.clear()
        if (user.isBlank()) {
            errors.put("user", "User is required")
        }
        if (email.isBlank()) {
            errors.put("email", "Email is required")
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errors.put("email", "Email is invalid")
        }
        if (password.isBlank()) {
            errors.put("password", "Password is invalid")
        }
        if (password != confirmPassword) {
            errors.put("confirmPassword", "The passwords are differents")
        }
        return this.errors.isEmpty()
    }

    fun register(onSuccess:() -> Unit, onError:(String) -> Unit) {
        if (this.validate()) {
            viewModelScope.launch {
                try {
                    val user = User(user, email, password, true)
                    userRepository.insert(user)
                    onSuccess()
                }
                catch (e: Exception) {
                    e.printStackTrace()
                    onError(e.localizedMessage ?: "Unknow error")
                }
            }
        }
        else {
            onError(FormUtil.getErrorMessage(this.errors))
        }
    }

    fun onChangeUser(newUser: String) {
        if (newUser.length < 20) {
            this.user = newUser.trim()
            this.errors.remove("user")
        }
    }


}

