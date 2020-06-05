package com.steve.githubinfosearcher.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.steve.githubinfosearcher.dataModel.UserModel
import com.steve.githubinfosearcher.datasource.UserDataSource

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var userListLiveData  :LiveData<PagedList<UserModel>>
    private val config: PagedList.Config = PagedList.Config.Builder()
        .setPageSize(30)
        .setEnablePlaceholders(false)
        .build()

    init {
        userListLiveData  = createLivePagedListBuilder("", config).build()
    }

    fun searchUser(query: String): LiveData<PagedList<UserModel>> {
        userListLiveData = createLivePagedListBuilder(query, config).build()
        return userListLiveData
    }

    private fun createLivePagedListBuilder(query: String, config: PagedList.Config): LivePagedListBuilder<String, UserModel> {
        val dataSourceFactory = object : DataSource.Factory<String, UserModel>() {
            override fun create(): DataSource<String, UserModel> {
                return UserDataSource(viewModelScope, query)
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }
}