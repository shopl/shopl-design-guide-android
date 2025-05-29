package com.shopl.sdg.component.avatar

import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * [SDGAvatar] Size
 * 직원의 얼굴 사진을 보여주는 컴포넌트 Size
 */
sealed class SDGAvatarSize(
    val size: Dp,
    val badgeSize: Dp,
    val borderSize: Dp,
) {
    @Stable
    data object XXS: SDGAvatarSize(size = 20.dp, badgeSize = 8.dp, borderSize = 1.5.dp)
    @Stable
    data object XS: SDGAvatarSize(size = 24.dp, badgeSize = 10.dp, borderSize = 1.8.dp)
    @Stable
    data object S: SDGAvatarSize(size = 30.dp, badgeSize = 12.dp, borderSize = 2.4.dp)
    @Stable
    data object M: SDGAvatarSize(size = 40.dp, badgeSize = 14.dp, borderSize = 3.dp)
    @Stable
    data object L: SDGAvatarSize(size = 50.dp, badgeSize = 16.dp, borderSize = 3.6.dp)
    @Stable
    data object XL: SDGAvatarSize(size = 90.dp, badgeSize = 24.dp, borderSize = 6.dp)
}