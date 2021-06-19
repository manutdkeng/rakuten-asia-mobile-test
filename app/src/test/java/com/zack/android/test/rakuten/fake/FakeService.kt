package com.zack.android.test.rakuten.fake

import com.zack.android.test.rakuten.api.ApiService
import com.zack.android.test.rakuten.api.model.OwnerModel
import com.zack.android.test.rakuten.api.model.RepoModel
import com.zack.android.test.rakuten.api.model.RepoResponseModel
import java.lang.Exception
import java.util.*

class FakeService : ApiService {
    var throwException = false

    val repositories = listOf(
        RepoModel(
            "1",
            "Test Repo1",
            "Test Repo One",
            "https://global.rakuten.com/corp/",
            "java",
            Calendar.getInstance().time,
            OwnerModel("1", "Test Owner", null, "User", null),
            "",
            ""
        ), RepoModel(
            "2",
            "Test Repo2",
            "Test Repo Two",
            "https://global.rakuten.com/corp/",
            "java",
            Calendar.getInstance().time,
            OwnerModel("1", "Test Owner", null, "User", null),
            "",
            ""
        )
    )

    override suspend fun getRepositories(after: String?): RepoResponseModel {
        val afterUrl = if (after == null) {
            "https://api.bitbucket.org/2.0/repositories?after=2011-09-03T12%3A33%3A16.028393%2B00%3A00"
        } else {
            null
        }
        if (throwException) {
            throw Exception("Fake Network Exception")
        }

        return RepoResponseModel(repositories.size, repositories, afterUrl)
    }
}