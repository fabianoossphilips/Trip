package com.example.trip.viewModel

object FormUtil {

    fun getErrorMessage(errors: Map<String, String>): String {
        val fieldErrors = errors.map {
            it.value
        }.joinToString("\n")
        return "Some fields are invalid!\n${fieldErrors}"
    }
}