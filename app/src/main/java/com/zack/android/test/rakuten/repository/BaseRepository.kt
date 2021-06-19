package com.zack.android.test.rakuten.repository

import android.util.Log
import com.zack.android.test.rakuten.R
import com.zack.android.test.rakuten.api.ApiService
import com.zack.android.test.rakuten.api.model.RepoResponseModel
import com.zack.android.test.rakuten.utils.Result
import javax.inject.Inject

class BaseRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getRepositories(afterRequest: String?): Result<RepoResponseModel> {
        return try {
            val response = apiService.getRepositories(afterRequest)
            Result.Success(response)
        } catch (e: Exception) {
            Log.e("BaseRepository", "API exception",e)
            Result.Error(messageId = R.string.api_error)
        }
    }
}