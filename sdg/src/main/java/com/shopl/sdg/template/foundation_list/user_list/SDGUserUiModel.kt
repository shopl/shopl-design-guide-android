package com.shopl.sdg.template.foundation_list.user_list

import androidx.compose.ui.graphics.Color
import com.shopl.sdg.component.avatar.SDGAvatarBadge
import com.shopl.sdg.template.profile.second.SDGSecondProfileType
import com.shopl.sdg_common.foundation.SDGColor

data class SDGUserUiModel(
    val userId: String,
    val profileUserName: String,
    val profileUserRegImg: String? = null,
    val profileGroupName: String? = null,
    val profileBackgroundColor: Color = SDGColor.Transparent,
    val profileType: SDGSecondProfileType = SDGSecondProfileType.Normal,
    val profileAvatarBadge: SDGAvatarBadge = SDGAvatarBadge.None,
)