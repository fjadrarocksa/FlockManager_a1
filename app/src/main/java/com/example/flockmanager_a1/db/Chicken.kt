package com.example.flockmanager_a1.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "chickens")
data class Chicken(
    @PrimaryKey
    @ColumnInfo(name = "date_added") val dateAdded: Date,
    @ColumnInfo(name = "breed") val breedText: String,
    @ColumnInfo(name = "sex") val sexText: String,
    @ColumnInfo(name = "group_id", defaultValue = "" ) val groupId: String // V3

)