package com.example.flockmanager_a1.ui.creategroups

import androidx.lifecycle.*
import com.example.flockmanager_a1.ChickenRepository
import com.example.flockmanager_a1.db.Chicken
import com.example.flockmanager_a1.db.FlockDatabase
import kotlinx.coroutines.launch

class CreateGroupsViewModel(private val repository: ChickenRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.

    //private val allChickens: LiveData<List<Chicken>
    val allChickens: LiveData<List<Chicken>> = repository.allChickens.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(chicken: Chicken) = viewModelScope.launch {
        repository.insert(chicken)
    }
}

class CreateGroupsViewModelFactory(private val repository: ChickenRepository)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateGroupsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreateGroupsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}