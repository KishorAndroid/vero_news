package com.kishordahiwadkar.veronews

data class Result(
        val status: String?,
        val totalResults: Int?,
        val articles: List<Article?>?
)