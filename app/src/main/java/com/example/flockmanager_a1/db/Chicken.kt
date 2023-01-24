package com.example.flockmanager_a1.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "chickens")

data class Chicken(
    @PrimaryKey val uid: Int,
    //@ColumnInfo(name = "date_added") val dateAdded: Date?,
    @ColumnInfo(name = "bird_type") val birdType: String?,
    @ColumnInfo(name = "breed") val breedType: String?,
)
/*data class Chicken(
    @PrimaryKey val uid: Int,
    //@ColumnInfo(name = "date_added") val dateAdded: Date?,
    @ColumnInfo(name = "bird_type") val birdType: String?,
    @ColumnInfo(name = "breed") val breedType: String?,
)
 */
@Entity(tableName = "groups")
data class Group(
    @PrimaryKey val uid: Int,
    //@ColumnInfo(name = "date_added") val dateAdded: Date?,
    @ColumnInfo(name = "group_name") val groupName: String?
    //@ColumnInfo(name = "bird_list") vararg birdList: ChickenDao?
)