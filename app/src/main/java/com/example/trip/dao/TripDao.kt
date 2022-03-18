package com.example.trip.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.trip.entity.Trip

@Dao
interface TripDao {

    @Insert
    suspend fun insert(trip: Trip)

    @Update
    suspend fun update(trip: Trip)

    @Query(value = "select * from Trip t where t.user = :user order by t.startDate")
    suspend fun findByUser(user: String): List<Trip>

    @Query(value = "select * from Trip t where t.id = :id")
    suspend fun findById(id: Int): Trip

}