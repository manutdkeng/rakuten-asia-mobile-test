package com.zack.android.test.rakuten.repository

import com.zack.android.test.rakuten.R
import com.zack.android.test.rakuten.api.ApiService
import com.zack.android.test.rakuten.fake.FakeService
import com.zack.android.test.rakuten.util.AbstractUnitTest
import com.zack.android.test.rakuten.util.assertResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class BaseRepositoryTest : AbstractUnitTest() {
    private lateinit var repository: BaseRepository
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        apiService = FakeService()
        repository = BaseRepository(apiService)
    }

    @Test
    fun `getRepositories success`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        val result = repository.getRepositories(null)

        assertResult(result).isSuccess((apiService as FakeService).repositories)
    }

    @Test
    fun `getRepositories exception`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        (apiService as FakeService).throwException = true
        val result = repository.getRepositories(null)

        assertResult(result).isError(R.string.api_error)
    }
}