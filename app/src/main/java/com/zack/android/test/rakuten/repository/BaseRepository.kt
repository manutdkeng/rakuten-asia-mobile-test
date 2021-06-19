package com.zack.android.test.rakuten.repository

import com.zack.android.test.rakuten.api.ApiService
import com.zack.android.test.rakuten.api.model.RepoResponseModel
import com.zack.android.test.rakuten.utils.Result
import javax.inject.Inject

class BaseRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getRepositories(afterRequest: String?): Result<RepoResponseModel> {

    }
}