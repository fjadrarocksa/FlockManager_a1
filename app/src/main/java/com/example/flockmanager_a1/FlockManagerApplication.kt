package com.example.flockmanager_a1

import android.app.Application
import com.example.flockmanager_a1.db.FlockDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class FlockManagerApplication : Application() {

    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { FlockDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { ChickenRepository(database.chickenDao()) }

    //static fun getAllicationContext() = getAllicationContext() //TODO

}