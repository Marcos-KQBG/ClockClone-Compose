package com.marcos.clockclone.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marcos.clockclone.data.local.AlarmDao
import com.marcos.clockclone.ui.screens.detail.DetailViewModel
import com.marcos.clockclone.ui.screens.list.ListViewModel

class ViewModelFactory(private val dao: AlarmDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListViewModel(dao) as T
        }
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}