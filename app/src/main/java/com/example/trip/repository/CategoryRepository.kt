package com.example.trip.repository

import android.app.Application
import com.example.trip.dao.CategoryDao
import com.example.trip.database.TripDatabase
import com.example.trip.entity.Category

class CategoryRepository(application: Application) {
    private val categoryDao: CategoryDao

    init {
        this.categoryDao = TripDatabase.getDatabase(application).categoryDao()
    }

    suspend fun save(category: Category) {
        if (category.id == 0) {
            categoryDao.insert(category)
        }
        else {
            categoryDao.update(category)
        }
    }

    suspend fun update(category: Category) {
        categoryDao.update(category = category)
    }

    suspend fun findAll(): List<Category> {
        return categoryDao.findAll()
    }

    suspend fun delete(category: Category) {
        categoryDao.delete(category = category)
    }

    suspend fun findById(id: Int): Category? {
        return categoryDao.findById(id)
    }
}