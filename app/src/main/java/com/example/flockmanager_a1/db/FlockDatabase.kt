package com.example.flockmanager_a1.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Chicken::class], version = 1, exportSchema = false)
public abstract class FlockDatabase : RoomDatabase() {
    abstract fun chickenDao(): ChickenDao

    private class FlockDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.chickenDao())
                }
            }
        }

        suspend fun populateDatabase(chickenDao: ChickenDao) {
            // Delete all content here.
            chickenDao.deleteAll()

            // Add sample words.
            var chicken = Chicken(1, "Chicken", "Blomme")
            chickenDao.insert(chicken)
            chicken = Chicken(2, "Turkey", "Welsumer")
            chickenDao.insert(chicken)

            // TODO: Add your own words!
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: FlockDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): FlockDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FlockDatabase::class.java,
                    "word_database"
                )
                    .addCallback(FlockDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}