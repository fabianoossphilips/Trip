package com.example.trip.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.trip.dao.CategoryDao
import com.example.trip.dao.TripDao
import com.example.trip.dao.UserDao
import com.example.trip.entity.Category
import com.example.trip.entity.Trip
import com.example.trip.entity.User

@Database(entities = [User::class, Trip::class, Category::class], version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class TripDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun tripDao(): TripDao

    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private lateinit var _instance: TripDatabase

        fun getDatabase(context: Context): TripDatabase {
            if (!this::_instance.isInitialized) {
                synchronized(this) {
                    _instance = Room.databaseBuilder(
                        context.applicationContext,
                        TripDatabase::class.java,
                        "trip-database4"
                    ).build()
                }
            }
            return _instance
        }

    }
}