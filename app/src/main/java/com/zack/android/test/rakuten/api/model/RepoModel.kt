package com.zack.android.test.rakuten.api.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class RepoModel(
    val uuid: String,
    val name: String?,
    @SerializedName("full_name") val fullName: String?,
    val website: String?,
    val language: String?,
    @SerializedName("created_on") val createdOn: Date?,
    val owner: OwnerModel?,
    val type: String?,
    val description: String?
)
