package com.example.flockmanager_a1.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ChickenDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addChicken(chicken: Chicken)

    @Query("SELECT * FROM chickens ORDER BY date_added DESC")
    fun getAllChickens(): Flow<List<Chicken>>

    @Update
    suspend fun updateChicken(chicken: Chicken)

    @Delete
    suspend fun deleteChicken(chicken: Chicken)

}