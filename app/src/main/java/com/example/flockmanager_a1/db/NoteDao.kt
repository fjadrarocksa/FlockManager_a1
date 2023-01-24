package com.example.flockmanager_a1.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: Note)

    @Query("SELECT * FROM notes ORDER BY dateAdded DESC")
    fun getNotes(): Flow<List<Note>>

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

}