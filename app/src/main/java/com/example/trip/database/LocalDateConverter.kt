package com.example.trip.database

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

class LocalDateConverter {

    @TypeConverter
    fun fromLong(value: Long?): LocalDate? {
        return value?.let { LocalDate.ofEpochDay(value) }
    }

    @TypeConverter
    fun dateToLong(date: LocalDate?): Long? {
        return date?.toEpochDay()
    }

}