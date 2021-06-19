package com.zack.android.test.rakuten.api.model

import com.google.gson.annotations.SerializedName

data class OwnerModel(
    val uuid: String,
    @SerializedName("display_name") val displayName: String?,
    val links: OwnerLinksModel?,
    val type: String?,
    val nickname: String?
)

data class OwnerLinksModel(
    val avatar: LinkModel
)

data class LinkModel(
    val href: String?
)
