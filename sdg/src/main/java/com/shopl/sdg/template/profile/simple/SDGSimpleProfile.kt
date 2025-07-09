package com.shopl.sdg.template.profile.simple

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.avatar.SDGAvatar
import com.shopl.sdg.component.avatar.SDGAvatarBadge
import com.shopl.sdg.component.avatar.SDGAvatarSize
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing8
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Profile - Simple Profile
 *
 * avatar S(size 30)을 기준으로 1개의 정보만 표시하는 컴포넌트
 *
 * @param userRegImg 유저 이미지
 * @param activate 활성화 여부(활성화 여부에 따라 투명/반투명)
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=6840-15011&m=dev">Figma</a>
 */
@Composable
fun SDGSimpleProfile(
    userRegImg: String?,
    userName: String,
    backgroundColor: Color,
    avatarBadge: SDGAvatarBadge,
    radius: Dp? = null,
    isMaternity: Boolean = false,
    paddingValues: PaddingValues = PaddingValues(),
    type: SDGSimpleProfileType = SDGSimpleProfileType.NORMAL,
    activate: Boolean = true,
    onClickAvatar: (() -> Unit)? = null,
    onClickProfile: (() -> Unit)? = null
) {
    val profileModifier = Modifier
        .fillMaxWidth()
        .then(
            if (!activate) Modifier.alpha(0.6F) else Modifier
        )
        .then(
            if (onClickProfile != null) {
                Modifier.clickable(hasRipple = false) { onClickProfile() }
            } else {
                Modifier
            }
        )
        .background(
            color = backgroundColor,
            shape = RoundedCornerShape(radius ?: 0.dp)
        )
        .clip(RoundedCornerShape(radius ?: 0.dp))
        .padding(paddingValues)

    Row(
        modifier = profileModifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = spacedBy(Spacing8)
    ) {
        SDGAvatar(
            avatarSize = SDGAvatarSize.S,
            avatarBadge = avatarBadge,
            userRegImg = userRegImg,
            onClickAvatar = onClickAvatar,
            isMaternity = isMaternity,
        )
        SDGText(
            text = userName,
            typography = type.typography,
            textColor = type.textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
fun SDGSimpleProfilePreview() {
    SDGSimpleProfile(
        userRegImg = "https://picsum.photos/200",
        userName = "김샤플",
        backgroundColor = Color.White,
        avatarBadge = SDGAvatarBadge.Admin,
        type = SDGSimpleProfileType.NORMAL,
    )
}
