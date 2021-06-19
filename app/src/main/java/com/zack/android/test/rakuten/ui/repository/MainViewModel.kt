package com.zack.android.test.rakuten.ui.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zack.android.test.rakuten.api.model.RepoModel
import com.zack.android.test.rakuten.repository.BaseRepository
import com.zack.android.test.rakuten.utils.Event
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: BaseRepository) {
    private val _repositoryLiveData = MutableLiveData<List<RepoModel>>()
    val repositoryLiveData: LiveData<List<RepoModel>> = _repositoryLiveData

    private val _nextButtonLiveData = MutableLiveData<String?>()
    val nextButtonLiveData: LiveData<String?> = _nextButtonLiveData

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _toastEvent = MutableLiveData<Event<Int>>()
    val toastEvent: LiveData<Event<Int>> = _toastEvent

    fun getRepositories() {

    }

    fun nextRepositories(afterRequest: String) {

    }
}