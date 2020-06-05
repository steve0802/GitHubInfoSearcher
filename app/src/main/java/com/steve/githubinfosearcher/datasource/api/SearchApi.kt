package com.steve.githubinfosearcher.datasource.api

import com.steve.githubinfosearcher.datasource.response.SearchApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {
    @GET("search/users")
    suspend fun getUserList(@Query("q") filter: String?,
                    @Query("per_page") perPage: Int?,
                    @Query("page") page: Int? ): Response<SearchApiResponse>
}