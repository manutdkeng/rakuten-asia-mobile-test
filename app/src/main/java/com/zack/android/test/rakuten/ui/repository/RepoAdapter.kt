package com.zack.android.test.rakuten.ui.repository

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zack.android.test.rakuten.api.model.RepoModel
import com.zack.android.test.rakuten.databinding.LayoutRepoItemBinding

class RepoAdapter : ListAdapter<RepoModel, RepoAdapter.RepoViewHolder>(RepoDiffCallback()){
    inner class RepoViewHolder(private val binding: LayoutRepoItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bindView(model: RepoModel) {

        }
    }

    class RepoDiffCallback: DiffUtil.ItemCallback<RepoModel>() {
        override fun areItemsTheSame(oldItem: RepoModel, newItem: RepoModel): Boolean {
            return oldItem.uuid == newItem.uuid
        }

        override fun areContentsTheSame(oldItem: RepoModel, newItem: RepoModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding = LayoutRepoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val model = getItem(position)
        holder.bindView(model)
    }
}