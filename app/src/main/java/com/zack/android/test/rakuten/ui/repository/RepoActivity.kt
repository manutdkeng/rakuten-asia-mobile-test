package com.zack.android.test.rakuten.ui.repository

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.zack.android.test.rakuten.MyApplication
import com.zack.android.test.rakuten.api.model.RepoModel
import com.zack.android.test.rakuten.databinding.ActivityRepoBinding
import com.zack.android.test.rakuten.databinding.LayoutRepoMoreInfoBinding
import com.zack.android.test.rakuten.utils.EventObserver
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class RepoActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    private lateinit var adapter: RepoAdapter
    private lateinit var binding: ActivityRepoBinding

    private val sdf = SimpleDateFormat("yyyy-MM-dd HH:ss Z", Locale.ENGLISH)

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RepoAdapter {
            showMoreInfo(it)
        }
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

    private fun showMoreInfo(model: RepoModel) {
        val moreInfoBinding = LayoutRepoMoreInfoBinding.inflate(layoutInflater, binding.root, false)
        with(moreInfoBinding) {
            name.setNullableText(model.name)
            fullName.setNullableText(model.fullName)
            model.owner?.links?.avatar?.href?.let { Picasso.get().load(it).into(avatar) }
            ownerName.setNullableText(model.owner?.displayName)
            model.createdOn?.let { createdOn.text = sdf.format(it) }
            type.setNullableText(model.type)
            language.setNullableText(model.language)
            description.setNullableText(model.description)
            website.setNullableText(model.website)
        }
        val builder = AlertDialog.Builder(this)
            .setView(moreInfoBinding.root)
            .create()
        builder.show()
    }
}