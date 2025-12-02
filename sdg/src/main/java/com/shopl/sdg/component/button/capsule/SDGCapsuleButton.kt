package com.shopl.sdg.component.button.capsule

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.button.capsule.preview.SDGCapsuleButtonPreviewParam
import com.shopl.sdg.component.button.capsule.preview.SDGCapsuleButtonPreviewParameterProvider
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Button - Capsule Button
 *
 * 화면의 내용 영역에 배치하여 사용하는 좌우가 동그란 형태의 버튼 컴포넌트
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=7102-15078&m=dev">Figma</a>
 */
@Composable
fun SDGCapsuleButton(
    size: SDGCapsuleButtonSize,
    type: SDGCapsuleButtonType,
    label: String,
    labelColor: Color,
    backgroundColor: Color,
    onClick: () -> Unit,
    iconDownSized: Boolean = false,
    isFillMaxWidth: Boolean = false,
    enable: Boolean = true,
    @DrawableRes leftIcon: Int? = null,
    leftIconTint: Color? = null,
    @DrawableRes rightIcon: Int? = null,
    rightIconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
) {

    Box(
        modifier = if (isFillMaxWidth) {
            Modifier.fillMaxWidth()
        } else {
            Modifier.width(IntrinsicSize.Max)
        }
    ) {
        Row(
            modifier = Modifier
                .padding(marginValues)
                .then(
                    if (isFillMaxWidth) {
                        Modifier.fillMaxWidth()
                    } else {
                        Modifier
                    }
                )
                .height(size.height)
                .background(
                    color = backgroundColor,
                    shape = size.cornerRadius
                )
                .then(
                    if (type is SDGCapsuleButtonType.Line) {
                        Modifier.border(
                            width = 1.dp,
                            color = type.lineColor,
                            shape = size.cornerRadius,
                        )
                    } else {
                        Modifier
                    }
                )
                .clip(size.cornerRadius)
                .then(
                    if (enable) {
                        Modifier.clickable(
                            hasRipple = true,
                            rippleColor = SDGColor.Neutral900,
                            onClick = onClick,
                        )
                    } else {
                        Modifier
                    }
                )
                .padding(horizontal = size.horizontalPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val iconSize = if (iconDownSized) size.defaultIconSize - 2.dp else size.defaultIconSize

            if (leftIcon != null && leftIconTint != null) {
                SDGImage(
                    modifier = Modifier.size(iconSize),
                    resId = leftIcon,
                    color = leftIconTint
                )
                Spacer(modifier = Modifier.width(size.gap))
            }
            SDGText(
                text = label,
                textColor = labelColor,
                typography = size.typography,
            )
            if (rightIcon != null && rightIconTint != null) {
                Spacer(modifier = Modifier.width(size.gap))
                SDGImage(
                    modifier = Modifier.size(iconSize),
                    resId = rightIcon,
                    color = rightIconTint
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSDGCapsuleButton(
    @PreviewParameter(SDGCapsuleButtonPreviewParameterProvider::class)
    param: SDGCapsuleButtonPreviewParam
) {
    SDGCapsuleButton(
        size = param.size,
        type = param.type,
        label = param.label,
        labelColor = param.labelColor,
        backgroundColor = param.backgroundColor,
        onClick = {},
        enable = param.enable,
        leftIcon = param.leftIcon,
        leftIconTint = param.leftIconTint,
        rightIcon = param.rightIcon,
        rightIconTint = param.rightIconTint,
        isFillMaxWidth = param.isFillMaxWidth,
        iconDownSized = param.iconDownSized
    )
}
