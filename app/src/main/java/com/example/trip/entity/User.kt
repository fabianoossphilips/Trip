package com.example.trip.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val name: String,
    val email: String,
    val password: String,
    val active: Boolean
) {
}