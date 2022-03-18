package com.example.trip.dao

import androidx.room.*
import com.example.trip.entity.Category

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: Category)

    @Update
    suspend fun update(category: Category)

    @Delete
    suspend fun delete(category: Category)

    @Query("select id, description from category order by description")
    suspend fun findAll(): List<Category>

    @Query("select id, description from category c where c.id = :id")
    suspend fun findById(id : Int): Category?
}