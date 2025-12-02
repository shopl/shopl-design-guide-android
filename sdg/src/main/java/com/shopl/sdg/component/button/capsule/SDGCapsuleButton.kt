package com.shopl.sdg.component.button.capsule

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

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
            if (leftIcon != null && leftIconTint != null) {
                SDGImage(
                    modifier = Modifier.size(14.dp),
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
                    resId = rightIcon,
                    color = rightIconTint
                )
            }
        }
    }

}

@Composable
fun RowScope.SDGCapsuleButton(
    weight: Float,
    size: SDGCapsuleButtonSize,
    type: SDGCapsuleButtonType,
    label: String,
    labelColor: Color,
    backgroundColor: Color,
    onClick: () -> Unit,
    enable: Boolean = true,
    @DrawableRes leftIcon: Int? = null,
    leftIconTint: Color? = null,
    @DrawableRes rightIcon: Int? = null,
    rightIconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
) {
    Box(Modifier.weight(weight)) {
        SDGCapsuleButton(
            isFillMaxWidth = true,
            enable = enable,
            size = size,
            type = type,
            label = label,
            labelColor = labelColor,
            backgroundColor = backgroundColor,
            leftIcon = leftIcon,
            leftIconTint = leftIconTint,
            rightIcon = rightIcon,
            rightIconTint = rightIconTint,
            marginValues = marginValues,
            onClick = onClick,
        )
    }
}

@Composable
fun BoxScope.SDGCapsuleButton(
    alignment: Alignment,
    size: SDGCapsuleButtonSize,
    type: SDGCapsuleButtonType,
    label: String,
    labelColor: Color,
    backgroundColor: Color,
    onClick: () -> Unit,
    isFillMaxWidth: Boolean = false,
    enable: Boolean = true,
    @DrawableRes leftIcon: Int? = null,
    leftIconTint: Color? = null,
    @DrawableRes rightIcon: Int? = null,
    rightIconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
) {
    Box(Modifier.align(alignment)) {
        SDGCapsuleButton(
            isFillMaxWidth = isFillMaxWidth,
            enable = enable,
            size = size,
            type = type,
            label = label,
            labelColor = labelColor,
            backgroundColor = backgroundColor,
            leftIcon = leftIcon,
            leftIconTint = leftIconTint,
            rightIcon = rightIcon,
            rightIconTint = rightIconTint,
            marginValues = marginValues,
            onClick = onClick,
        )
    }
}

@Composable
@Preview
private fun PrevSDGCapsuleButton() {
    Surface(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SDGCapsuleButton(
                size = SDGCapsuleButtonSize.Medium,
                type = SDGCapsuleButtonType.Solid,
                enable = false,
                leftIcon = R.drawable.ic_common_edit,
                leftIconTint = SDGColor.Neutral0,
                label = "작성하기",
                labelColor = SDGColor.Neutral0,
                backgroundColor = SDGColor.Neutral600,
                onClick = {}
            )

            SDGCapsuleButton(
                size = SDGCapsuleButtonSize.Medium,
                type = SDGCapsuleButtonType.Line(SDGColor.Neutral700),
                leftIcon = R.drawable.ic_common_edit,
                leftIconTint = SDGColor.Neutral700,
                label = "작성하기",
                labelColor = SDGColor.Neutral700,
                backgroundColor = SDGColor.Neutral0,
                onClick = {}
            )

            SDGCapsuleButton(
                isFillMaxWidth = true,
                size = SDGCapsuleButtonSize.Medium,
                type = SDGCapsuleButtonType.Line(SDGColor.Neutral700),
                leftIcon = R.drawable.ic_common_edit,
                leftIconTint = SDGColor.Neutral700,
                label = "작성하기",
                labelColor = SDGColor.Neutral700,
                backgroundColor = SDGColor.Neutral0,
                onClick = {}
            )
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            SDGCapsuleButton(
                weight = 1f,
                size = SDGCapsuleButtonSize.Medium,
                type = SDGCapsuleButtonType.Line(SDGColor.Neutral700),
                leftIcon = R.drawable.ic_common_edit,
                leftIconTint = SDGColor.Neutral700,
                label = "작성하기",
                labelColor = SDGColor.Neutral700,
                backgroundColor = SDGColor.Neutral0,
                onClick = {}
            )

            SDGCapsuleButton(
                weight = 1f,
                enable = false,
                size = SDGCapsuleButtonSize.Medium,
                type = SDGCapsuleButtonType.Line(SDGColor.Neutral700),
                leftIcon = R.drawable.ic_common_edit,
                leftIconTint = SDGColor.Neutral700,
                label = "작성하기",
                labelColor = SDGColor.Neutral700,
                backgroundColor = SDGColor.Neutral0,
                onClick = {}
            )

            SDGCapsuleButton(
                weight = 1f,
                size = SDGCapsuleButtonSize.Medium,
                type = SDGCapsuleButtonType.Solid,
                leftIcon = R.drawable.ic_common_edit,
                leftIconTint = SDGColor.Neutral700,
                label = "작성하기",
                labelColor = SDGColor.Neutral700,
                backgroundColor = SDGColor.Neutral200,
                onClick = {}
            )
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {

            SDGCapsuleButton(
                weight = 1f,
                size = SDGCapsuleButtonSize.Medium,
                type = SDGCapsuleButtonType.Solid,
                label = "작성하기",
                labelColor = SDGColor.Neutral0,
                backgroundColor = SDGColor.Neutral700,
                onClick = {}
            )

            SDGCapsuleButton(
                weight = 1f,
                size = SDGCapsuleButtonSize.Medium,
                type = SDGCapsuleButtonType.Solid,
                label = "작성하기",
                labelColor = SDGColor.Neutral0,
                backgroundColor = SDGColor.Primary300,
                onClick = {}
            )

            SDGCapsuleButton(
                weight = 1f,
                size = SDGCapsuleButtonSize.Medium,
                type = SDGCapsuleButtonType.Solid,
                label = "작성하기",
                labelColor = SDGColor.Neutral600,
                backgroundColor = SDGColor.Neutral200,
                onClick = {}
            )

        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {

            SDGCapsuleButton(
                enable = false,
                weight = 1f,
                size = SDGCapsuleButtonSize.Medium,
                type = SDGCapsuleButtonType.Solid,
                label = "작성하기",
                labelColor = SDGColor.Neutral0,
                backgroundColor = SDGColor.Neutral700,
                onClick = {}
            )

            SDGCapsuleButton(
                enable = false,
                weight = 1f,
                size = SDGCapsuleButtonSize.Medium,
                type = SDGCapsuleButtonType.Solid,
                label = "작성하기",
                labelColor = SDGColor.Neutral0,
                backgroundColor = SDGColor.Primary300,
                onClick = {}
            )

            SDGCapsuleButton(
                enable = false,
                weight = 1f,
                size = SDGCapsuleButtonSize.Medium,
                type = SDGCapsuleButtonType.Solid,
                label = "작성하기",
                labelColor = SDGColor.Neutral600,
                backgroundColor = SDGColor.Neutral200,
                onClick = {}
            )

        }

        SDGCapsuleButton(
            enable = false,
            size = SDGCapsuleButtonSize.Medium,
            type = SDGCapsuleButtonType.Solid,
            label = "작성하기",
            labelColor = SDGColor.Neutral0,
            backgroundColor = SDGColor.Neutral700,
            onClick = {}
        )
    }
}
