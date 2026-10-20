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
    /** 재시도 아이콘의 크기. Avatar 2.3.50의 크기별 규격을 따릅니다. */
    val retryIconSize: Dp
        get() = when (this) {
            XXS -> 10.dp
            XS -> 12.dp
            S -> 16.dp
            M -> 20.dp
            L -> 22.dp
            XL -> 28.dp
        }

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
