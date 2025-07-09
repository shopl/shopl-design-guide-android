package com.shopl.sdg.template.profile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.sp
import com.shopl.sdg.component.avatar.IOAvatar
import com.shopl.sdg.component.avatar.SDGAvatarSize
import com.shopl.sdg_common.enums.DisplayType
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.IOText
import com.shopl.sdg_common.ui.components.IOTypeface


@Composable
fun SDGProfileBasic(
    roleType: String,
    userRegImg: String?,
    userName: String,
    groupName: String,
    backgroundColor: Color = SDGColor.Transparent,
    @DrawableRes emptyImage: Int? = null,
    @DrawableRes badgeImage: Int? = null,
    radius: Dp? = null,
    paddingValues: PaddingValues = PaddingValues(),
    profileType: DisplayType = DisplayType.NORMAL,
    onClickAvatar: (() -> Unit)? = null,
    onClickProfile: (() -> Unit)? = null
) {

    val profileModifier = Modifier
        .fillMaxWidth()
        .background(
            color = backgroundColor,
            shape = RoundedCornerShape(radius ?: 0.dp)
        )
        .clip(RoundedCornerShape(radius ?: 0.dp))
        .padding(paddingValues)

    onClickProfile?.let {
        profileModifier.clickable { it() }
    }

    Row(
        modifier = profileModifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IOAvatar(
            avatarSize = SDGAvatarSize.M,
            roleType = roleType,
            userRegImg = userRegImg,
            onClickAvatar = onClickAvatar,
            emptyImage = emptyImage,
            badgeImage = badgeImage,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
        ) {
            IOText(
                text = userName,
                typeface = profileType.typeface,
                textColor = SDGColor.Neutral700,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 16.sp,
            )
            Spacer(modifier = Modifier.height(2.dp))
            IOText(
                text = groupName,
                typeface = IOTypeface.REGULAR,
                textColor = profileType.subTextColor,
                fontSize = 14.sp,
                maxLines = 1,
                lineHeight = 14.sp,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
fun SDGProfileSecond(
    roleType: String,
    userRegImg: String?,
    userName: String,
    groupName: String,
    isMaternity: Boolean = false,
    backgroundColor: Color,
    radius: Dp? = null,
    paddingValues: PaddingValues = PaddingValues(),
    profileType: DisplayType = DisplayType.NORMAL,
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
        verticalAlignment = Alignment.CenterVertically
    ) {
        IOAvatar(
            avatarSize = SDGAvatarSize.S,
            roleType = roleType,
            userRegImg = userRegImg,
            onClickAvatar = onClickAvatar,
            isMaternity = isMaternity,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
        ) {
            IOText(
                text = userName,
                typeface = profileType.typeface,
                textColor = SDGColor.Neutral700,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            IOText(
                text = groupName,
                typeface = IOTypeface.REGULAR,
                textColor = SDGColor.Neutral500,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
fun RowScope.SDGProfileSecond(
    weight: Float,
    roleType: String,
    userRegImg: String?,
    userName: String,
    groupName: String,
    backgroundColor: Color,
    radius: Dp? = null,
    paddingValues: PaddingValues = PaddingValues(),
    profileType: DisplayType = DisplayType.NORMAL,
    onClickAvatar: (() -> Unit)? = null,
    onClickProfile: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier.weight(weight)
    ) {
        SDGProfileSecond(
            roleType = roleType,
            userRegImg = userRegImg,
            userName = userName,
            groupName = groupName,
            backgroundColor = backgroundColor,
            radius = radius,
            paddingValues = paddingValues,
            profileType = profileType,
            onClickAvatar = onClickAvatar,
            onClickProfile = onClickProfile,
        )
    }
}

@Composable
fun SDGProfileMini(
    roleType: String,
    userRegImg: String?,
    userName: String,
    backgroundColor: Color,
    radius: Dp? = null,
    paddingValues: PaddingValues = PaddingValues(),
    profileType: DisplayType = DisplayType.NORMAL,
    onClickAvatar: (() -> Unit)? = null,
    onClickProfile: (() -> Unit)? = null
) {

    val profileModifier = Modifier
        .fillMaxWidth()
        .background(
            color = backgroundColor,
            shape = RoundedCornerShape(radius ?: 0.dp)
        )
        .clip(RoundedCornerShape(radius ?: 0.dp))
        .padding(paddingValues)

    onClickProfile?.let {
        profileModifier.clickable { it() }
    }

    Row(
        modifier = profileModifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IOAvatar(
            avatarSize = SDGAvatarSize.XXS,
            roleType = roleType,
            userRegImg = userRegImg,
            onClickAvatar = onClickAvatar
        )
        Spacer(modifier = Modifier.width(6.dp))
        IOText(
            text = userName,
            typeface = profileType.typeface,
            textColor = SDGColor.Neutral700,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
fun PrevProfile() {
    Surface() {
        Column {
            SDGProfileBasic(
                roleType = "2",
                userRegImg = null,
                userName = "김철수",
                groupName = "행정팀",
                backgroundColor = SDGColor.Neutral100,
                radius = 10.dp,
                paddingValues = PaddingValues(12.dp),
                profileType = DisplayType.NORMAL,
                onClickAvatar = { },
                onClickProfile = { }
            )
        }
    }
}
