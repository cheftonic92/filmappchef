package com.cheftonic.filmappchef3.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cheftonic.filmappchef3.repository.Repository
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val repository: Repository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository = repository) as T
    }
}