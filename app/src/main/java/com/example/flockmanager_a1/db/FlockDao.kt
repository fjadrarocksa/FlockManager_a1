package com.example.flockmanager_a1.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FlockDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFlock(flock: Flock)

    @Query("SELECT * FROM flocks ORDER BY date_added DESC")
    fun getAll(): Flow<List<Flock>>

    @Update
    suspend fun updateFlock(flock: Flock)

    @Delete
    suspend fun deleteFlock(flock: Flock)

    @Query("DELETE FROM flocks")
    fun deleteAll()

}