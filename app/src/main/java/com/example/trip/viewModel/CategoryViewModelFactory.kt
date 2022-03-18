package com.example.trip.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trip.repository.CategoryRepository

class CategoryViewModelFactory(private val app: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val repository = CategoryRepository(app)
        val categoryViewModel = CategoryViewModel(repository)
        return categoryViewModel as T
    }
}