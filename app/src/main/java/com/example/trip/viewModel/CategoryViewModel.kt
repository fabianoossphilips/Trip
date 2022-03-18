package com.example.trip.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trip.TripConst
import com.example.trip.entity.Category
import com.example.trip.repository.CategoryRepository
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoryRepository: CategoryRepository): ViewModel() {


    // var list: MutableList<Category> = mutableListOf()
    var list: MutableState<List<Category>> = mutableStateOf(listOf())

    var categoryId by mutableStateOf(0)

    var description by mutableStateOf("")

    var categoryForDelete: Category? by mutableStateOf(null)

    fun remove() {
       viewModelScope.launch {
           categoryForDelete?.let {
               categoryRepository.delete(it)
               list.value = categoryRepository.findAll()
           }
        }
    }

    fun load() {
        viewModelScope.launch {
            list.value = categoryRepository.findAll()
        }
    }

    fun findById(id: Int) {
        viewModelScope.launch {
            Log.i(TripConst.TAG, "load category id: ${id}")
            val category = categoryRepository.findById(id)
            Log.i(TripConst.TAG, "load category : ${category}")
            if (category != null) {
                description = category.description
                categoryId = category.id
            }
        }
    }

    fun save(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val c = Category(id = categoryId, description = description)
                categoryRepository.save(c)
                onSuccess()
            }
            catch (e: Exception) {
                onError(e.localizedMessage ?: "Error when inserting category")
            }
        }
    }
}