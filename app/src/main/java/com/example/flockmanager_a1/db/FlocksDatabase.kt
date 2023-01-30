package com.example.flockmanager_a1.db

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope

@Database(
    version = 1,
    entities = [Chicken::class, Flock::class],
//    autoMigrations = [
//        AutoMigration (from = 2, to = 3)
//    ],
    exportSchema = true
)
@TypeConverters(DateConverters::class)
abstract class FlocksDatabase : RoomDatabase() {

    abstract fun chickenDao(): ChickenDao
    abstract fun flockDao(): FlockDao
    private class FlocksDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
//            INSTANCE?.let { database ->
//                scope.launch {
//                    populateDatabase(database.chickenDao())
//                }
//            }

            suspend fun populateDatabase(chickenDao: ChickenDao) {
                // Delete all content here.
                //chickenDao.deleteAll()

                // Add sample words.
                //var chicken = Chicken(0,"Chicken2", "Blomme")
                //chickenDao.insert(chicken)
                //chicken = Chicken(1,"Turkey", "Welsumer")
                //chickenDao.insert(chicken)

                // TODO: Add your own words!
            }
        }
    }


    companion object {
            @Volatile
            private var INSTANCE: FlocksDatabase? = null

            fun getDatabase(context: Context, scope: CoroutineScope): FlocksDatabase {
                // if the INSTANCE is not null, then return it,
                // if it is, then create the database
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        FlocksDatabase::class.java,
                        "flocks"
                    )
                        //.addCallback(FlocksDatabaseCallback(scope))
                        .build()
                    INSTANCE = instance
                    // return instance
                    instance
                }
            }
            /*
        fun getDatabase(context: Context): FlocksDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            if (INSTANCE == null) {
                synchronized(this) {
                    // Pass the database to the INSTANCE
                    INSTANCE = buildDatabase(context)
                }
            }
            // Return database.
            return INSTANCE!!
        }*/


            private val MIGRATION_2_3: Migration = object : Migration(2, 3) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    // The following query will add a new column called lastUpdate to the notes database
                    database.execSQL("ALTER TABLE chickens ADD COLUMN group_id TEXT")
                }
            }


            private fun buildDatabase(context: Context): FlocksDatabase {
                return Room.databaseBuilder(
                    context.applicationContext,
                    FlocksDatabase::class.java,
                    "flock_database"
                )
                    //.addMigrations(MIGRATION_2_3)
                    .build()
            }
        }

}



/*
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
            var chicken = Chicken(0,"Chicken2", "Blomme")
            chickenDao.insert(chicken)
            chicken = Chicken(1,"Turkey", "Welsumer")
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
 */