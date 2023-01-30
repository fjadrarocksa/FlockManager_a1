package com.example.flockmanager_a1

import androidx.annotation.WorkerThread
import com.example.flockmanager_a1.db.Chicken
import com.example.flockmanager_a1.db.Flock
import com.example.flockmanager_a1.db.FlockDao
import kotlinx.coroutines.flow.Flow

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO

class FlockRepository(private val flockDao: FlockDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allFlocks: Flow<List<Flock>> = flockDao.getAll()
    //val allChickens: LiveData<List<Chicken>> = chickenDao.getAll().asLiveData()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    //@Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(flock: Flock) {
        flockDao.addFlock(flock)
    }

    @WorkerThread
    suspend fun updateFlock(flock: Flock) {
        flockDao.updateFlock(flock)
    }
    @WorkerThread
    suspend fun deleteFlock(flock: Flock) {
        flockDao.addFlock(flock)
    }
    @WorkerThread
    suspend fun deleteAll() {
        flockDao.deleteAll()
    }


}
