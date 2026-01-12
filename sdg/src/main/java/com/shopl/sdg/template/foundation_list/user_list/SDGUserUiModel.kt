package com.shopl.sdg.template.foundation_list.user_list

import com.shopl.sdg.component.avatar.SDGAvatarBadge

data class SDGUserUiModel(
    val userId: String,
    val profileUserName: String,
    val profileUserRegImg: String?,
    val profileGroupName: String?,
    val profileAvatarBadge: SDGAvatarBadge,
)
