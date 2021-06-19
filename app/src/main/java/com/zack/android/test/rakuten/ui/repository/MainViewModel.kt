package com.zack.android.test.rakuten.ui.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zack.android.test.rakuten.api.model.RepoModel
import com.zack.android.test.rakuten.repository.BaseRepository
import com.zack.android.test.rakuten.utils.Event
import com.zack.android.test.rakuten.utils.Result
import kotlinx.coroutines.launch
import java.net.URLDecoder
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: BaseRepository) : ViewModel() {
    companion object {
        private const val AFTER_PARAM = "after="
    }

    private val _repositoryLiveData = MutableLiveData<List<RepoModel>>()
    val repositoryLiveData: LiveData<List<RepoModel>> = _repositoryLiveData

    private val _nextButtonLiveData = MutableLiveData<String?>()
    val nextButtonLiveData: LiveData<String?> = _nextButtonLiveData

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _toastEvent = MutableLiveData<Event<Int>>()
    val toastEvent: LiveData<Event<Int>> = _toastEvent

    private val _repositoryList = mutableListOf<RepoModel>()

    fun getRepositories() {
        _loading.value = true
        viewModelScope.launch {
            when (val result = repository.getRepositories(null)) {
                is Result.Error -> _toastEvent.value = Event(result.messageId)
                is Result.Success -> {
                    _repositoryList.clear()
                    result.data.values?.let { _repositoryList.addAll(it) }
                    postNextButton(result.data.next)
                    postRepoList()
                }
            }
            _loading.value = false
        }
    }

    private fun postRepoList() {
        val postList = mutableListOf<RepoModel>().apply {
            addAll(_repositoryList)
        }
        _repositoryLiveData.value = postList
    }

    private fun postNextButton(next: String?) {
        val nextLink: String? = next?.split(AFTER_PARAM)?.let {
            if (it.size > 1) URLDecoder.decode(it[1], Charsets.UTF_8.displayName()) else null
        }
        _nextButtonLiveData.value = nextLink
    }

    fun nextRepositories(afterRequest: String) {
        _loading.value = true
        viewModelScope.launch {
            when (val result = repository.getRepositories(afterRequest)) {
                is Result.Error -> _toastEvent.value = Event(result.messageId)
                is Result.Success -> {
                    result.data.values?.let { _repositoryList.addAll(it) }
                    postNextButton(result.data.next)
                    postRepoList()
                }
            }
            _loading.value = false
        }
    }
}