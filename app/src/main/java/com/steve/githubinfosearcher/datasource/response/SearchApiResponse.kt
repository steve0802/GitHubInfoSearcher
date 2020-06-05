package com.steve.githubinfosearcher.datasource.response

data class SearchApiResponse(
    val incomplete_results: Boolean,
    val items: List<SearchApiResponseItem>,
    val total_count: Int
)