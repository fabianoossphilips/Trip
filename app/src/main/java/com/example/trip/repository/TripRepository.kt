package com.example.trip.repository

import android.app.Application
import com.example.trip.dao.TripDao
import com.example.trip.database.TripDatabase
import com.example.trip.entity.Trip

class TripRepository(application: Application) {

    private val tripDao: TripDao

    init {
        val database = TripDatabase.getDatabase(application)
        tripDao = database.tripDao()
    }

    suspend fun save(trip: Trip) {
        if (trip.id == 0)
            tripDao.insert(trip)
        else
            tripDao.update(trip)
    }


    suspend fun findByUser(user: String): List<Trip> {
        return tripDao.findByUser(user)
    }

    suspend fun findById(id: Int): Trip {
        return tripDao.findById(id)
    }


}