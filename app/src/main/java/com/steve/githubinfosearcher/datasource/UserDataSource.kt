package com.steve.githubinfosearcher.datasource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.steve.githubinfosearcher.dataModel.UserModel
import com.steve.githubinfosearcher.datasource.response.SearchApiResponseItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class UserDataSource(private val scope: CoroutineScope, private val query: String) : PageKeyedDataSource<String, UserModel>() {
    private val searchApi = GitHubApiClient.searchApi
    private val pageSize = 20
    private var currentPageIndex = 1

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, UserModel>) {
        this.currentPageIndex = 1

        scope.launch {
            try {
                val response = searchApi.getUserList(getQueryParam(query), pageSize, currentPageIndex)
                when{
                    response.isSuccessful -> {
                        val responseItems = response.body()?.items ?: return@launch
                        callback.onResult(convertToUserModelList(responseItems), (currentPageIndex-1).toString(), (currentPageIndex+1).toString())
                    }
                }
            }catch (exception : Exception){
                Log.e("PostsDataSource", "Failed to fetch data!")
            }
        }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, UserModel>) {
        this.currentPageIndex += 1

        scope.launch {
            try {
                val response = searchApi.getUserList(getQueryParam(query), pageSize, currentPageIndex)
                when{
                    response.isSuccessful -> {
                        val responseItems = response.body()?.items ?: return@launch
                        callback.onResult(convertToUserModelList(responseItems), (currentPageIndex+1).toString())
                    }
                }

            }catch (exception : Exception){
                Log.e("PostsDataSource", "Failed to fetch data!")
            }
        }

    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, UserModel>) {
        if (this.currentPageIndex < 2)
            return

        this.currentPageIndex -= 1

        scope.launch {
            try {
                val response = searchApi.getUserList(getQueryParam(query), pageSize, currentPageIndex)
                when{
                    response.isSuccessful -> {
                        val responseItems = response.body()?.items ?: return@launch
                        callback.onResult(convertToUserModelList(responseItems), (currentPageIndex-1).toString())
                    }
                }

            }catch (exception : Exception){
                Log.e("PostsDataSource", "Failed to fetch data!")
            }
        }
    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }

    private fun convertToUserModelList(responseItemList: List<SearchApiResponseItem>): List<UserModel> {
        val userModelList = ArrayList<UserModel>()
        for (i in responseItemList.indices){
            val userModel = UserModel()
            userModel.login = responseItemList[i].login
            userModel.home_url = responseItemList[i].html_url
            userModel.avatar_url = responseItemList[i].avatar_url
            userModelList.add(userModel)
        }
        return userModelList
    }

    private fun getQueryParam(query: String): String {
        var queryParam = "type:user"
        if (!query.isEmpty())
            queryParam += " $query in:login"
        return queryParam
    }
}