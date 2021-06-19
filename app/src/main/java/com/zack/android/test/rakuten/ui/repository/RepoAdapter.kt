package com.zack.android.test.rakuten.ui.repository

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zack.android.test.rakuten.api.model.RepoModel
import com.zack.android.test.rakuten.databinding.LayoutRepoItemBinding
import java.text.SimpleDateFormat
import java.util.*

class RepoAdapter : ListAdapter<RepoModel, RepoAdapter.RepoViewHolder>(RepoDiffCallback()) {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm Z", Locale.ENGLISH)

    inner class RepoViewHolder(private val binding: LayoutRepoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindView(model: RepoModel) {
            with(binding) {
                name.setNullableText(model.name)
                model.owner?.links?.avatar?.href?.let { Picasso.get().load(it).into(avatar) }
                ownerName.setNullableText(model.owner?.displayName)
                model.createdOn?.let { createdOn.text = sdf.format(it) }
                type.setNullableText(model.type)
            }
        }
    }

    class RepoDiffCallback : DiffUtil.ItemCallback<RepoModel>() {
        override fun areItemsTheSame(oldItem: RepoModel, newItem: RepoModel): Boolean {
            return oldItem.uuid == newItem.uuid
        }

        override fun areContentsTheSame(oldItem: RepoModel, newItem: RepoModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding =
            LayoutRepoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val model = getItem(position)
        holder.bindView(model)
    }
}

fun TextView.setNullableText(nullable: String?) {
    val nonNull: String = nullable ?: ""
    text = nonNull
}