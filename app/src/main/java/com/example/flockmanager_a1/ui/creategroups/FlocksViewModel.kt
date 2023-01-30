package com.example.flockmanager_a1.ui.creategroups

import androidx.lifecycle.*
import com.example.flockmanager_a1.FlockRepository
//import com.example.flockmanager_a1.ChickenRepository
import com.example.flockmanager_a1.db.Chicken
import com.example.flockmanager_a1.db.Flock
import kotlinx.coroutines.launch

class FlockViewModel(private val repository: FlockRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    //private val allChickens: LiveData<List<Chicken>
    val allFlocks: LiveData<List<Flock>> = repository.allFlocks.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(flock: Flock) = viewModelScope.launch {
        repository.insert(flock)
    }

    fun update(flock: Flock) = viewModelScope.launch {
        repository.updateFlock(flock)
    }
}

class FlockViewModelFactory(private val repository: FlockRepository)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlockViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FlockViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

