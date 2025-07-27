package com.shopl.sdg.component.button.ghost

import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.button.SDGButtonFontWeight
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

/**
 * SDG - Button - Ghost Button
 *
 * 화면의 내용 영역에 배치하여 사용하는 배경이 없는 형태의 버튼 컴포넌트
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=7102-15126&m=dev">Figma</a>
 */
@Composable
fun SDGGhostButton(
    size: SDGGhostButtonSize,
    label: String,
    labelColor: Color,
    onClick: () -> Unit,
    labelWeight: SDGButtonFontWeight = SDGButtonFontWeight.R,
    isFillMaxWidth: Boolean = false,
    enable: Boolean = true,
    @DrawableRes leftIcon: Int? = null,
    leftIconTint: Color? = null,
    @DrawableRes rightIcon: Int? = null,
    rightIconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
) {
    val typography = getTypography(size, labelWeight)

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
                SDGImage(
                    modifier = Modifier.size(14.dp),
                    resId = leftIcon,
                    contentDescription = null,
                    color = if (enable) leftIconTint else leftIconTint.copy(alpha = 0.3f)
                )
                Spacer(modifier = Modifier.width(size.gap))
            }
            SDGText(
                text = label,
                textColor = if (enable) labelColor else labelColor.copy(alpha = 0.3f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                typography = typography
            )
            if (rightIcon != null && rightIconTint != null) {
                Spacer(modifier = Modifier.width(size.gap))

                SDGImage(
                    resId = rightIcon,
                    contentDescription = null,
                    color = if (enable) rightIconTint else rightIconTint.copy(alpha = 0.3f)
                )
            }
        }
    }
}

private fun getTypography(size: SDGGhostButtonSize, weight: SDGButtonFontWeight): SDGTypography {
    return when (size) {
        SDGGhostButtonSize.Large -> {
            when (weight) {
                SDGButtonFontWeight.R -> SDGTypography.Body1R
                SDGButtonFontWeight.SB -> SDGTypography.Body1SB
            }
        }

        SDGGhostButtonSize.Medium -> {
            when (weight) {
                SDGButtonFontWeight.R -> SDGTypography.Body2R
                SDGButtonFontWeight.SB -> SDGTypography.Body2SB
            }
        }

        SDGGhostButtonSize.Small -> {
            when (weight) {
                SDGButtonFontWeight.R -> SDGTypography.Body3R
                SDGButtonFontWeight.SB -> SDGTypography.Body3SB
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewSDGGhostButton() {
    SDGGhostButton(
        size = SDGGhostButtonSize.Large,
        label = "Ghost Button",
        labelColor = SDGColor.Neutral600,
        onClick = {},
        labelWeight = SDGButtonFontWeight.R,
        isFillMaxWidth = false,
        enable = true,
        leftIcon = null,
        leftIconTint = null,
        rightIcon = null,
        rightIconTint = null,
        marginValues = PaddingValues(0.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGGhostButtonWithIcon() {
    SDGGhostButton(
        size = SDGGhostButtonSize.Large,
        label = "Ghost Button",
        labelColor = SDGColor.Neutral600,
        onClick = {},
        labelWeight = SDGButtonFontWeight.SB,
        isFillMaxWidth = false,
        enable = false,
        leftIcon = R.drawable.ic_common_edit,
        leftIconTint = SDGColor.Neutral600,
        rightIcon = null,
        rightIconTint = null,
        marginValues = PaddingValues(0.dp)
    )
}