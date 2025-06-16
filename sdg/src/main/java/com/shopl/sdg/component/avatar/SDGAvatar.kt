package com.shopl.sdg.component.avatar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.SDGAsyncImage
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_resource.R

/**
 * SDG - Avatar
 *
 * 직원, 작성자 등 사람의 사진과 정보를 보여주는 컴포넌트
 *
 * @param avatarSize [SDGAvatarSize]
 * @param avatarBadge [SDGAvatarBadge]
 * @param isMaternity 임산부 여부(프로필 사진 내 임산부 표시에 사용)
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=6837-15581&m=dev">Figma</a>
 */
@Composable
fun SDGAvatar(
    avatarSize: SDGAvatarSize,
    avatarBadge: SDGAvatarBadge,
    userRegImg: String?,
    @DrawableRes emptyImage: Int? = null,
    @DrawableRes badgeImage: Int? = null,
    badgeImageTintColor: Color = SDGColor.Neutral700,
    isMaternity: Boolean = false,
    alpha: Float = 1f,
    onClickAvatar: (() -> Unit)? = null
) {
    val empty = emptyImage ?: R.drawable.profile_small

    Box(
        modifier = Modifier
            .size(avatarSize.size)
            .clickable(hasRipple = false) { onClickAvatar?.invoke() }
    ) {
        SDGAsyncImage(
            imageModel = if (userRegImg.isNullOrBlank()) empty else userRegImg,
            failureImageResourceId = empty,
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .alpha(alpha)
                .then(
                    if (isMaternity) {
                        Modifier.border(
                            width = avatarSize.borderSize,
                            color = SDGColor.PinkPK,
                            shape = CircleShape
                        )
                    } else Modifier
                ),
        )

        badgeImage?.let {
            SDGImage(
                resId = it,
                color = badgeImageTintColor,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(18.dp)
                    .shadow(elevation = 4.dp)
                    .background(
                        color = SDGColor.Neutral0,
                        shape = CircleShape
                    )
                    .padding(3.dp),
            )
        } ?: Image(
            painter = avatarBadge.resId?.let { painterResource(it) } ?: ColorPainter(Color.Transparent),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(avatarSize.badgeSize),
        )
    }
}

@Preview
@Composable
private fun PrevAvatar() {
    Column(
        Modifier
            .background(SDGColor.Neutral400)
            .padding(20.dp)
    ) {
        SDGAvatar(avatarSize = SDGAvatarSize.XXS, avatarBadge = SDGAvatarBadge.Admin, userRegImg = null)
        SDGAvatar(avatarSize = SDGAvatarSize.XS, avatarBadge = SDGAvatarBadge.Leader, userRegImg = null)
        SDGAvatar(avatarSize = SDGAvatarSize.S, avatarBadge = SDGAvatarBadge.None, userRegImg = null)
        SDGAvatar(avatarSize = SDGAvatarSize.M, avatarBadge = SDGAvatarBadge.Admin, userRegImg = null)
        SDGAvatar(avatarSize = SDGAvatarSize.L, avatarBadge = SDGAvatarBadge.Leader, userRegImg = null)
        SDGAvatar(avatarSize = SDGAvatarSize.XL, avatarBadge = SDGAvatarBadge.None, userRegImg = null)
    }
}

@Deprecated("연관된 디자인 시스템으로 인한 임시 추가")
@Composable
fun IOAvatar(
    avatarSize: SDGAvatarSize,
    roleType: String,
    userRegImg: String?,
    @DrawableRes emptyImage: Int? = null,
    @DrawableRes badgeImage: Int? = null,
    badgeImageTintColor: Color = SDGColor.Neutral700,
    isMaternity: Boolean = false,
    alpha: Float = 1f,
    onClickAvatar: (() -> Unit)? = null
) {
    val empty = emptyImage ?: R.drawable.profile_small

    val badge = when (roleType) {
        in listOf("2", "7", "9") -> painterResource(id = R.drawable.admin_badge)
        "1" -> painterResource(id = R.drawable.leader_badge)
        else -> ColorPainter(Color.Transparent)
    }

    Box(
        modifier = Modifier
            .size(avatarSize.size)
            .clickable(false) { onClickAvatar?.invoke() }
    ) {
        SDGAsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .alpha(alpha)
                .then(
                    if (isMaternity) {
                        Modifier.border(
                            width = avatarSize.borderSize,
                            color = SDGColor.PinkPK,
                            shape = CircleShape
                        )
                    } else Modifier
                ),
            imageModel = if (userRegImg.isNullOrBlank()) empty else userRegImg,
            failureImageResourceId = empty,
        )
        badgeImage?.let {
            // 현재 사이즈 정의는 M 기준으로 되어있음
            // 이후 다른 사이즈 추가 시 수정 필요
            Image(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(18.dp)
                    .shadow(elevation = 4.dp)
                    .background(
                        color = SDGColor.Neutral0,
                        shape = CircleShape
                    )
                    .padding(3.dp),
                painter = painterResource(it),
                contentDescription = null,
                colorFilter = ColorFilter.tint(badgeImageTintColor)
            )
        } ?: Image(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(avatarSize.badgeSize),
            painter = badge,
            contentDescription = null
        )
    }
}