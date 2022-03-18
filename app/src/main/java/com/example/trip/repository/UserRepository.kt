package com.example.trip.repository

import android.app.Application
import com.example.trip.dao.UserDao
import com.example.trip.database.TripDatabase
import com.example.trip.entity.User

class UserRepository(application: Application) {

    private val userDao: UserDao

    init {
        val database = TripDatabase.getDatabase(application)
        userDao = database.userDao()
    }

    suspend fun insert(user: User) {
        this.userDao.insert(user)
    }

    suspend fun findByUserAndPassword(name: String, password: String): User {
        return this.userDao.findByUserAndPassword(name, password)
    }


}