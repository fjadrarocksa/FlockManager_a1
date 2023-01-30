package com.example.flockmanager_a1.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "flocks")
data class Flock(
    @PrimaryKey
    @ColumnInfo(name = "date_added") val dateAdded: Date,
    @ColumnInfo(name = "flock_name") val flockName: String,
    @ColumnInfo(name = "egg_count", defaultValue = "0") val eggCount: Int,
    @ColumnInfo(name = "bird_count", defaultValue = "0") val birdCount: Int,
    @ColumnInfo(name = "comments", defaultValue = "") val comments: String
)