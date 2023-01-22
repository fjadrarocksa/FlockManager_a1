package com.example.flockmanager_a1

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.flockmanager_a1.db.Chicken
import com.example.flockmanager_a1.db.ChickenDao
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class ChickenRepository(private val chickenDao: ChickenDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allChickens: Flow<List<Chicken>> = chickenDao.getAll()
    //val allChickens: LiveData<List<Chicken>> = chickenDao.getAll().asLiveData()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(chicken: Chicken) {
        chickenDao.insert(chicken)
    }

}