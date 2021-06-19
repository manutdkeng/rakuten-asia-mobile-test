package com.zack.android.test.rakuten.api.model

data class RepoResponseModel(
    val pagelen: Int,
    val values: List<RepoModel>?,
    val next: String?
)
