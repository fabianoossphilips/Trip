package com.example.trip.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = arrayOf("name"),
        childColumns = arrayOf("user")
    )]
)
data class Trip (
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        val destiny: String,
        val type: TripType,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val budget: Double,
        val amountPeople: Int,
        val user: String

    ){
}

enum class TripType {
    LEISURE,
    BUSINESS
}
