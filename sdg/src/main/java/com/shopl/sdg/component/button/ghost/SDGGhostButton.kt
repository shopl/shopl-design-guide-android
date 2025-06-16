package com.shopl.sdg.component.button.ghost

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.IOText
import com.shopl.sdg_common.ui.components.IOTypeface
import com.shopl.sdg_resource.R

sealed class SDGGhostButtonSize(
    val height: Dp,
    val horizontalPadding: Dp,
    val gap: Dp,
    val labelSize: TextUnit,
    val rippleRadius: Dp,
) {
    @Stable
    data object Large : SDGGhostButtonSize(
        height = 42.dp,
        horizontalPadding = 16.dp,
        gap = 6.dp,
        labelSize = 16.sp,
        rippleRadius = 14.dp,
    )

    @Stable
    data object Medium : SDGGhostButtonSize(
        height = 36.dp,
        horizontalPadding = 10.dp,
        gap = 4.dp,
        labelSize = 14.sp,
        rippleRadius = 12.dp,
    )

    @Stable
    data object Small : SDGGhostButtonSize(
        height = 28.dp,
        horizontalPadding = 6.dp,
        gap = 2.dp,
        labelSize = 12.sp,
        rippleRadius = 9.dp,
    )
}

/**
 * SDG - Button - Ghost Button
 *
 * 화면의 내용 영역에 배치하여 사용하는 배경이 없는 형태의 버튼 컴포넌트
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=7102-15126&m=dev">Figma</a>
 */
@Composable
fun SDGGhostButton(
    isFillMaxWidth: Boolean = false,
    enable: Boolean = true,
    size: SDGGhostButtonSize,
    label: String,
    labelColor: Color,
    labelTypeface: IOTypeface = IOTypeface.REGULAR,
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
                .then(
                    if (enable) {
                        Modifier
                            .clip(RoundedCornerShape(size.rippleRadius))
                            .clickable(
                                hasRipple = true,
                                rippleColor = SDGColor.Neutral350,
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
                typeface = labelTypeface,
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
fun RowScope.SDGGhostButton(
    weight: Float,
    enable: Boolean = true,
    size: SDGGhostButtonSize,
    label: String,
    labelColor: Color,
    labelTypeface: IOTypeface = IOTypeface.REGULAR,
    @DrawableRes leftIcon: Int? = null,
    leftIconTint: Color? = null,
    @DrawableRes rightIcon: Int? = null,
    rightIconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
    onClick: () -> Unit,
) {
    Box(Modifier.weight(weight)) {
        SDGGhostButton(
            isFillMaxWidth = true,
            enable = enable,
            size = size,
            label = label,
            labelColor = labelColor,
            labelTypeface = labelTypeface,
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
fun BoxScope.SDGGhostButton(
    alignment: Alignment,
    isFillMaxWidth: Boolean,
    enable: Boolean = true,
    size: SDGGhostButtonSize,
    label: String,
    labelColor: Color,
    labelTypeface: IOTypeface = IOTypeface.REGULAR,
    @DrawableRes leftIcon: Int? = null,
    leftIconTint: Color? = null,
    @DrawableRes rightIcon: Int? = null,
    rightIconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
    onClick: () -> Unit,
) {
    Box(Modifier.align(alignment)) {
        SDGGhostButton(
            isFillMaxWidth = isFillMaxWidth,
            enable = enable,
            size = size,
            label = label,
            labelColor = labelColor,
            labelTypeface = labelTypeface,
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

            SDGGhostButton(
                size = SDGGhostButtonSize.Medium,
                label = "작성하기",
                labelColor = SDGColor.Neutral700,
            ) {}

            SDGGhostButton(
                isFillMaxWidth = true,
                size = SDGGhostButtonSize.Medium,
                leftIcon = R.drawable.ic_common_edit,
                leftIconTint = SDGColor.Neutral700,
                label = "작성하기",
                labelColor = SDGColor.Neutral700,
            ) {}


            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                SDGGhostButton(
                    enable = false,
                    weight = 1f,
                    size = SDGGhostButtonSize.Medium,
                    label = "작성하기",
                    labelColor = SDGColor.Neutral700,
                ) {}

                SDGGhostButton(
                    enable = false,
                    weight = 1f,
                    size = SDGGhostButtonSize.Medium,
                    label = "작성하기",
                    labelColor = SDGColor.Neutral700,
                ) {}

                SDGGhostButton(
                    enable = false,
                    weight = 1f,
                    size = SDGGhostButtonSize.Medium,
                    label = "작성하기",
                    labelColor = SDGColor.Neutral700,
                ) {}

            }

        }
    }
}