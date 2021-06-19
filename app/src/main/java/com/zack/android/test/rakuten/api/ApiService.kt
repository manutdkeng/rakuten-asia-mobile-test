package com.zack.android.test.rakuten.api

import com.zack.android.test.rakuten.api.model.RepoResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("repositories")
    suspend fun getRepositories(@Query("after") after: String?): RepoResponseModel
}