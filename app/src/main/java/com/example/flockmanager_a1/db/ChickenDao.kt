package com.example.flockmanager_a1.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ChickenDao {
    @Query("SELECT * FROM chickens")
    fun getAll(): Flow<List<Chicken>>

    @Insert
    fun insertAll(vararg chickens: Chicken)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(chicken: Chicken)

    @Query("DELETE FROM chickens")
    suspend fun deleteAll()

    /*
    @Delete
    suspend fun delete(chicken: Chicken)

    @Update
     */

}