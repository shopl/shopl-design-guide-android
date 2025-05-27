package com.shopl.sdg.component.avatar

import androidx.annotation.DrawableRes
import com.shopl.sdg_resource.R

/**
 * [SDGAvatar] Badge
 * 리더 또는 관리자를 표시하는 컴포넌트로 avatar의 사이즈에 따라 badge의 사이즈도 다르게 적용
 */
enum class SDGAvatarBadge(@DrawableRes val resId: Int?) {
    Admin(resId = R.drawable.admin_badge),
    Leader(resId = R.drawable.leader_badge),
    None(resId = null)
}