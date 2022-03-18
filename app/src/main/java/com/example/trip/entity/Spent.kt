package com.example.trip.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("categoryId")
        ),
        ForeignKey(
                entity = Trip::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("tripId")
        )
    ]
)
data class Spent (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val value: Double,
    val date: LocalDate,
    val description: String,
    val local: String,
    val categoryId: Int,
    val tripId: Int,
        ) {
}
