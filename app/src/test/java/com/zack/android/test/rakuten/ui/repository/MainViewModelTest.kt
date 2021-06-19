package com.zack.android.test.rakuten.ui.repository

import com.google.common.truth.Truth.assertThat
import com.zack.android.test.rakuten.api.model.RepoResponseModel
import com.zack.android.test.rakuten.fake.FakeService
import com.zack.android.test.rakuten.repository.BaseRepository
import com.zack.android.test.rakuten.util.AbstractUnitTest
import com.zack.android.test.rakuten.util.LiveDataTestUtil
import com.zack.android.test.rakuten.util.MockitoHelper
import com.zack.android.test.rakuten.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when` as whenever

@ExperimentalCoroutinesApi
class MainViewModelTest : AbstractUnitTest() {
    private lateinit var repository: BaseRepository
    private lateinit var viewModel: MainViewModel
    private val fakeService = FakeService()

    @Before
    fun setUp() {
        repository = mock(BaseRepository::class.java)
        viewModel = MainViewModel(repository)
    }

    @Test
    fun `getRepositories success`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        val expected =
            RepoResponseModel(fakeService.repositories.size, fakeService.repositories, null)
        whenever(repository.getRepositories(MockitoHelper.anyObject()))
            .thenReturn(Result.Success(expected))
        mainCoroutineRule.testDispatcher.pauseDispatcher()

        viewModel.getRepositories()
        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isTrue()
        mainCoroutineRule.testDispatcher.resumeDispatcher()

        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isFalse()
        assertThat(LiveDataTestUtil.getValue(viewModel.repositoryLiveData)).isEqualTo(expected.values)
        assertThat(LiveDataTestUtil.getValue(viewModel.nextButtonLiveData)).isNull()
    }

    @Test
    fun `getRepositories error`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        whenever(repository.getRepositories(MockitoHelper.anyObject()))
            .thenReturn(Result.Error(messageId = SAMPLE_ERROR_ID))
        mainCoroutineRule.testDispatcher.pauseDispatcher()

        viewModel.getRepositories()
        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isTrue()
        mainCoroutineRule.testDispatcher.resumeDispatcher()

        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isFalse()
        val toastEvent = LiveDataTestUtil.getValue(viewModel.toastEvent)
        assertThat(toastEvent.getContentIfNotHandled()).isEqualTo(SAMPLE_ERROR_ID)
    }

    @Test
    fun `getRepositories hasNextLink`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        val expected = RepoResponseModel(
            fakeService.repositories.size,
            fakeService.repositories,
            "https://api.bitbucket.org/2.0/repositories?after=abc1234"
        )
        whenever(repository.getRepositories(MockitoHelper.anyObject())).thenReturn(
            Result.Success(
                expected
            )
        )

        viewModel.getRepositories()
        assertThat(LiveDataTestUtil.getValue(viewModel.nextButtonLiveData)).isEqualTo("abc1234")
    }

    @Test
    fun `nextRepositories success`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        val expected =
            RepoResponseModel(fakeService.repositories.size, fakeService.repositories, null)
        whenever(repository.getRepositories(MockitoHelper.anyObject()))
            .thenReturn(Result.Success(expected))
        viewModel.getRepositories()
        val firstRepoSize = LiveDataTestUtil.getValue(viewModel.repositoryLiveData).size
        mainCoroutineRule.testDispatcher.pauseDispatcher()

        viewModel.nextRepositories("abc1234")
        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isTrue()
        mainCoroutineRule.testDispatcher.resumeDispatcher()

        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isFalse()
        val newRepoList = LiveDataTestUtil.getValue(viewModel.repositoryLiveData)
        assertThat(newRepoList.size).isGreaterThan(firstRepoSize)
        assertThat(LiveDataTestUtil.getValue(viewModel.nextButtonLiveData)).isNull()
    }

    @Test
    fun `nextRepositories error`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        val expected =
            RepoResponseModel(fakeService.repositories.size, fakeService.repositories, null)
        whenever(repository.getRepositories(MockitoHelper.anyObject()))
            .thenReturn(Result.Success(expected))
        viewModel.getRepositories()
        whenever(repository.getRepositories(MockitoHelper.anyObject()))
            .thenReturn(Result.Error(messageId = SAMPLE_ERROR_ID))
        mainCoroutineRule.testDispatcher.pauseDispatcher()

        viewModel.nextRepositories("abc1234")
        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isTrue()
        mainCoroutineRule.testDispatcher.resumeDispatcher()

        assertThat(LiveDataTestUtil.getValue(viewModel.loading)).isFalse()
        val toastEvent = LiveDataTestUtil.getValue(viewModel.toastEvent)
        assertThat(toastEvent.getContentIfNotHandled()).isEqualTo(SAMPLE_ERROR_ID)
    }
}