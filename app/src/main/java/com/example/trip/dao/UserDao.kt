package com.example.trip.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.trip.entity.User

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Update
    fun update(user: User)

    @Query("select name, email, password, active from User where name = :name and password = :password")
    suspend fun findByUserAndPassword(name: String, password: String): User


}