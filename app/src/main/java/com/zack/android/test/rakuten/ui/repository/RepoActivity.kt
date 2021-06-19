package com.zack.android.test.rakuten.ui.repository

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.zack.android.test.rakuten.MyApplication
import com.zack.android.test.rakuten.databinding.ActivityRepoBinding
import com.zack.android.test.rakuten.utils.EventObserver
import javax.inject.Inject

class RepoActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    private lateinit var adapter: RepoAdapter
    private lateinit var binding: ActivityRepoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RepoAdapter()
        binding.repoList.adapter = adapter
        viewModel.getRepositories()
    }

    override fun onStart() {
        super.onStart()
        setObserver()
    }

    private fun setObserver() {
        with(viewModel) {
            loading.observe(this@RepoActivity) {
                binding.loadingBar.visibility = if (it) View.VISIBLE else View.GONE
            }
            toastEvent.observe(this@RepoActivity, EventObserver {
                Toast.makeText(this@RepoActivity, it, Toast.LENGTH_SHORT).show()
            })
            repositoryLiveData.observe(this@RepoActivity) {
                adapter.submitList(it)
            }
            nextButtonLiveData.observe(this@RepoActivity) {
                if (it.isNullOrEmpty()) {
                    binding.nextButton.visibility = View.GONE
                } else {
                    binding.nextButton.setOnClickListener { _ ->
                        viewModel.nextRepositories(it)
                    }
                    binding.nextButton.visibility = View.VISIBLE
                }
            }
        }
    }
}