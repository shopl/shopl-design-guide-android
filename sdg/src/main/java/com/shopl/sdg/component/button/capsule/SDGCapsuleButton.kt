package com.shopl.sdg.component.button.capsule

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.ext.dpToSp
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.IOText
import com.shopl.sdg_common.ui.components.IOTypeface
import com.shopl.sdg_resource.R

sealed class SDGCapsuleButtonSize(
    val height: Dp,
    val horizontalPadding: Dp,
    val gap: Dp,
    val radius: Dp,
    val labelSize: TextUnit,
) {
    @Stable
    object Large : SDGCapsuleButtonSize(
        height = 50.dp,
        horizontalPadding = 20.dp,
        gap = 8.dp,
        radius = 25.dp,
        labelSize = 16.sp,
    )

    @Stable
    object Medium : SDGCapsuleButtonSize(
        height = 36.dp,
        horizontalPadding = 12.dp,
        gap = 4.dp,
        radius = 18.dp,
        labelSize = 14.sp,
    )

    @Stable
    object Small : SDGCapsuleButtonSize(
        height = 28.dp,
        horizontalPadding = 8.dp,
        gap = 2.dp,
        radius = 14.dp,
        labelSize = 12.sp,
    )

    @Stable
    object XSmall : SDGCapsuleButtonSize(
        height = 20.dp,
        horizontalPadding = 6.dp,
        gap = 2.dp,
        radius = 10.dp,
        labelSize = 12.sp,
    )
}

sealed class SDGCapsuleButtonType {
    @Stable
    object Solid : SDGCapsuleButtonType()

    @Stable
    data class Line(val lineColor: Color) : SDGCapsuleButtonType()
}

/**
 * SDG - Button - Capsule Button
 *
 * 화면의 내용 영역에 배치하여 사용한느 좌우가 동그란 형태의 버튼 컴포넌트
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=7102-15078&m=dev">Figma</a>
 */
@Composable
fun SDGCapsuleButton(
    isFillMaxWidth: Boolean = false,
    enable: Boolean = true,
    size: SDGCapsuleButtonSize,
    type: SDGCapsuleButtonType,
    label: String,
    labelColor: Color,
    backgroundColor: Color,
    @DrawableRes leftIcon: Int? = null,
    leftIconTint: Color? = null,
    @DrawableRes rightIcon: Int? = null,
    rightIconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
    onClick: () -> Unit,
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
                    shape = RoundedCornerShape(size.radius)
                )
                .then(
                    if (type is SDGCapsuleButtonType.Line) {
                        Modifier.border(
                            width = 1.dp,
                            color = type.lineColor,
                            shape = RoundedCornerShape(size = size.radius),
                        )
                    } else {
                        Modifier
                    }
                )
                .clip(RoundedCornerShape(size.radius))
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
                Image(
                    modifier = Modifier.size(14.dp),
                    painter = painterResource(id = leftIcon),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(
                        color = leftIconTint
                    )
                )
                Spacer(modifier = Modifier.width(size.gap))
            }
            IOText(
                text = label,
                textColor = labelColor,
                fontSize = size.labelSize,
                typeface = IOTypeface.REGULAR,
                lineHeight = dpToSp(dp = size.height)
            )
            if (rightIcon != null && rightIconTint != null) {
                Spacer(modifier = Modifier.width(size.gap))
                Image(
                    painter = painterResource(id = rightIcon),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(
                        color = rightIconTint
                    )
                )
            }
        }
    }

}

@Composable
fun RowScope.SDGCapsuleButton(
    weight: Float,
    enable: Boolean = true,
    size: SDGCapsuleButtonSize,
    type: SDGCapsuleButtonType,
    label: String,
    labelColor: Color,
    backgroundColor: Color,
    @DrawableRes leftIcon: Int? = null,
    leftIconTint: Color? = null,
    @DrawableRes rightIcon: Int? = null,
    rightIconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
    onClick: () -> Unit,
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
    isFillMaxWidth: Boolean = false,
    enable: Boolean = true,
    size: SDGCapsuleButtonSize,
    type: SDGCapsuleButtonType,
    label: String,
    labelColor: Color,
    backgroundColor: Color,
    @DrawableRes leftIcon: Int? = null,
    leftIconTint: Color? = null,
    @DrawableRes rightIcon: Int? = null,
    rightIconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
    onClick: () -> Unit,
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
private fun PrevTest() {
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
                backgroundColor = SDGColor.Neutral600
            ) {}

            SDGCapsuleButton(
                size = SDGCapsuleButtonSize.Medium,
                type = SDGCapsuleButtonType.Line(SDGColor.Neutral700),
                leftIcon = R.drawable.ic_common_edit,
                leftIconTint = SDGColor.Neutral700,
                label = "작성하기",
                labelColor = SDGColor.Neutral700,
                backgroundColor = SDGColor.Neutral0
            ) {}

            SDGCapsuleButton(
                isFillMaxWidth = true,
                size = SDGCapsuleButtonSize.Medium,
                type = SDGCapsuleButtonType.Line(SDGColor.Neutral700),
                leftIcon = R.drawable.ic_common_edit,
                leftIconTint = SDGColor.Neutral700,
                label = "작성하기",
                labelColor = SDGColor.Neutral700,
                backgroundColor = SDGColor.Neutral0
            ) {}

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                SDGCapsuleButton(
                    weight = 1f,
                    size = SDGCapsuleButtonSize.Medium,
                    type = SDGCapsuleButtonType.Line(SDGColor.Neutral700),
                    leftIcon = R.drawable.ic_common_edit,
                    leftIconTint = SDGColor.Neutral700,
                    label = "작성하기",
                    labelColor = SDGColor.Neutral700,
                    backgroundColor = SDGColor.Neutral0
                ) {}

                SDGCapsuleButton(
                    weight = 1f,
                    enable = false,
                    size = SDGCapsuleButtonSize.Medium,
                    type = SDGCapsuleButtonType.Line(SDGColor.Neutral700),
                    leftIcon = R.drawable.ic_common_edit,
                    leftIconTint = SDGColor.Neutral700,
                    label = "작성하기",
                    labelColor = SDGColor.Neutral700,
                    backgroundColor = SDGColor.Neutral0
                ) {}

                SDGCapsuleButton(
                    weight = 1f,
                    size = SDGCapsuleButtonSize.Medium,
                    type = SDGCapsuleButtonType.Solid,
                    leftIcon = R.drawable.ic_common_edit,
                    leftIconTint = SDGColor.Neutral700,
                    label = "작성하기",
                    labelColor = SDGColor.Neutral700,
                    backgroundColor = SDGColor.Neutral200
                ) {}
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                SDGCapsuleButton(
                    weight = 1f,
                    size = SDGCapsuleButtonSize.Medium,
                    type = SDGCapsuleButtonType.Solid,
                    label = "작성하기",
                    labelColor = SDGColor.Neutral0,
                    backgroundColor = SDGColor.Neutral700
                ) {}

                SDGCapsuleButton(
                    weight = 1f,
                    size = SDGCapsuleButtonSize.Medium,
                    type = SDGCapsuleButtonType.Solid,
                    label = "작성하기",
                    labelColor = SDGColor.Neutral0,
                    backgroundColor = SDGColor.Primary300
                ) {}

                SDGCapsuleButton(
                    weight = 1f,
                    size = SDGCapsuleButtonSize.Medium,
                    type = SDGCapsuleButtonType.Solid,
                    label = "작성하기",
                    labelColor = SDGColor.Neutral600,
                    backgroundColor = SDGColor.Neutral200
                ) {}

            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                SDGCapsuleButton(
                    enable = false,
                    weight = 1f,
                    size = SDGCapsuleButtonSize.Medium,
                    type = SDGCapsuleButtonType.Solid,
                    label = "작성하기",
                    labelColor = SDGColor.Neutral0,
                    backgroundColor = SDGColor.Neutral700
                ) {}

                SDGCapsuleButton(
                    enable = false,
                    weight = 1f,
                    size = SDGCapsuleButtonSize.Medium,
                    type = SDGCapsuleButtonType.Solid,
                    label = "작성하기",
                    labelColor = SDGColor.Neutral0,
                    backgroundColor = SDGColor.Primary300
                ) {}

                SDGCapsuleButton(
                    enable = false,
                    weight = 1f,
                    size = SDGCapsuleButtonSize.Medium,
                    type = SDGCapsuleButtonType.Solid,
                    label = "작성하기",
                    labelColor = SDGColor.Neutral600,
                    backgroundColor = SDGColor.Neutral200
                ) {}

            }

            SDGCapsuleButton(
                enable = false,
                size = SDGCapsuleButtonSize.Medium,
                type = SDGCapsuleButtonType.Solid,
                label = "작성하기",
                labelColor = SDGColor.Neutral0,
                backgroundColor = SDGColor.Neutral700
            ) {}
        }
    }
}