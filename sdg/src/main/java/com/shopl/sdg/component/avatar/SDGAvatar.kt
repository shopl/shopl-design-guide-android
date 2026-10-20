package com.shopl.sdg.component.avatar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.SDGAsyncImage
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_resource.R

/**
 * SDG - Avatar
 *
 * 유저의 얼굴 사진 또는 설정된 이미지를 보여주는 컴포넌트
 *
 * 얼굴, 외곽 테두리, 우측 하단 아이콘과 재시도 표시를 각각 제어합니다.
 * 재시도 상태에서는 프로필 클릭 대신 [onClickRetry]를 호출하며, 호출부에서 재요청과
 * [showRetry] 상태 갱신을 처리합니다. 테두리는 레이아웃 크기를 유지하면서 원 바깥에 그립니다.
 *
 * @param avatarSize 얼굴, 테두리 두께, 아이콘 크기를 결정하는 규격
 * @param userRegImg 표시할 프로필 이미지. 비어 있으면 기본 이미지를 표시합니다.
 * @param emptyImage 프로필 미등록 또는 로딩 실패 시 원형 영역을 채우도록 Crop하는 대체 이미지.
 * null이면 SDG 기본 이미지를 사용합니다.
 * @param showFace false이면 등록된 이미지 대신 기본 이미지를 표시합니다.
 * @param showOutline 외곽 테두리 표시 여부
 * @param outlineColor 외곽 테두리에 사용할 SDG 컬러
 * @param showIc 우측 하단 아이콘 표시 여부
 * @param iconResId 우측 하단에 표시할 아이콘. null이면 표시하지 않습니다.
 * @param iconColor 아이콘 색상. null이면 리소스의 원래 색상을 유지합니다.
 * @param showRetry CG 배경의 재시도 아이콘 표시 여부
 * @param onClickRetry 재시도 상태에서 아바타를 선택했을 때 호출
 * @param onClickAvatar 정상 상태에서 아바타를 선택했을 때 호출
 *
 * @version 2.3.50
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=27130-4525&m=dev">Figma</a>
 */
@Composable
fun SDGAvatar(
    avatarSize: SDGAvatarSize,
    userRegImg: String?,
    @DrawableRes emptyImage: Int? = null,
    showFace: Boolean = true,
    showOutline: Boolean = false,
    outlineColor: Color = SDGColor.SpecialPK,
    showIc: Boolean = true,
    @DrawableRes iconResId: Int? = null,
    iconColor: Color? = null,
    showRetry: Boolean = false,
    onClickRetry: (() -> Unit)? = null,
    onClickAvatar: (() -> Unit)? = null,
) {
    val empty = emptyImage ?: R.drawable.profile_small
    val onClick = if (showRetry) onClickRetry else onClickAvatar

    Box(
        modifier = Modifier
            .size(avatarSize.size)
            .then(
                if (showOutline) {
                    Modifier.drawBehind {
                        val strokeWidth = avatarSize.borderSize.toPx()
                        drawCircle(
                            color = outlineColor,
                            radius = size.minDimension / 2 + strokeWidth / 2,
                            style = Stroke(width = strokeWidth),
                        )
                    }
                } else Modifier
            )
            .then(
                if (onClick != null) {
                    Modifier.clickable(hasRipple = false, onClick = onClick)
                } else Modifier
            ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            if (showRetry) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(SDGColor.SpecialCG),
                    contentAlignment = Alignment.Center,
                ) {
                    SDGImage(
                        resId = R.drawable.ic_retry,
                        color = SDGColor.Neutral600,
                        modifier = Modifier.size(avatarSize.retryIconSize),
                    )
                }
            } else if (!showFace || userRegImg.isNullOrBlank()) {
                SDGImage(
                    resId = empty,
                    color = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            } else {
                SDGAsyncImage(
                    imageModel = userRegImg,
                    failureImage = {
                        SDGImage(
                            resId = empty,
                            color = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                        )
                    },
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    previewContent = {
                        SDGImage(
                            resId = empty,
                            color = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                        )
                    },
                )
            }
        }

        if (showIc && iconResId != null) {
            SDGImage(
                resId = iconResId,
                color = iconColor,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(avatarSize.badgeSize),
            )
        }
    }
}

/**
 * SDG - Avatar
 *
 * 직원, 작성자 등 사람의 사진과 정보를 보여주는 컴포넌트
 *
 * 기존 배지·임산부 표시 호출부의 호환성을 유지하는 API입니다.
 * 독립적인 테두리·아이콘·재시도 표시는 `showRetry`를 제공하는 새 오버로드를 사용합니다.
 *
 * @param avatarSize [SDGAvatarSize]
 * @param avatarBadge [SDGAvatarBadge]
 * @param isMaternity 임산부 여부(프로필 사진 내 임산부 표시에 사용)
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=6837-15581&m=dev">Figma</a>
 */
@Deprecated("Avatar 2.3.50의 showOutline, iconResId, showRetry 기반 SDGAvatar를 사용하세요.")
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

@Preview(showBackground = true)
@Composable
private fun PreviewSDGAvatar(
    @PreviewParameter(SDGAvatarPreviewParameterProvider::class)
    parameter: SDGAvatarPreviewParameter,
) {
    Box(
        Modifier
            .padding(20.dp)
    ) {
        SDGAvatar(
            avatarSize = parameter.avatarSize,
            userRegImg = null,
            showFace = parameter.showFace,
            showOutline = parameter.showOutline,
            showIc = parameter.showIc,
            iconResId = R.drawable.admin_badge,
            showRetry = parameter.showRetry,
        )
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
