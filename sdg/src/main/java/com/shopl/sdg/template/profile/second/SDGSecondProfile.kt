package com.shopl.sdg.template.profile.second

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
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
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing8
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Profile - Second Profile
 *
 * 직원, 작성자 등 사람의 사진과 정보를 보여주는 컴포넌트
 * avatar S(size 30)을 기준으로 이름과 서브정보를 표시하는 컴포넌트
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=6840-15005&m=dev">Figma</a>
 */
@Composable
fun SDGSecondProfile(
    userRegImg: String?,
    userName: String?,
    groupName: String?,
    backgroundColor: Color,
    type: SDGSecondProfileType,
    avatarBadge: SDGAvatarBadge,
    isMultiLine: Boolean = false,
    isMaternity: Boolean = false,
    radius: Dp? = null,
    paddingValues: PaddingValues = PaddingValues(),
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

        Column(
            modifier = Modifier
        ) {
            userName?.let {
                SDGText(
                    text = it,
                    typography = type.userNameTypography,
                    textColor = type.userNameColor,
                    maxLines = if (isMultiLine) Int.MAX_VALUE else 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            groupName?.let {
                SDGText(
                    text = it,
                    typography = type.groupNameTypography,
                    textColor = type.groupNameColor,
                    maxLines = if (isMultiLine) Int.MAX_VALUE else 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSDGSecondProfile() {
    SDGSecondProfile(
        userRegImg = null,
        userName = "김사플",
        groupName = "Design Team",
        backgroundColor = SDGColor.Neutral0,
        type = SDGSecondProfileType.Normal,
        avatarBadge = SDGAvatarBadge.Admin,
    )
}