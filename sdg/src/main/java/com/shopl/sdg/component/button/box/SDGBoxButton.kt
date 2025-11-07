package com.shopl.sdg.component.button.box

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Button - Box Button
 *
 * 화면의 내용 영역에 배치하여 사용하는 일반적인 사각 형태의 버튼 컴포넌트
 *
 * @param iconDownSize [SDGBoxButtonSize] 별 icon size down 여부(기존 사이즈에서 2dp만 down)
 * @param debounceTimeMills 중복 클릭 방지 시간
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=7102-15102&m=dev">Figma</a>
 */
@Composable
fun SDGBoxButton(
    size: SDGBoxButtonSize,
    type: SDGBoxButtonType,
    label: String,
    labelColor: Color,
    backgroundColor: Color,
    onClick: () -> Unit,
    iconDownSize: Boolean = false,
    enable: Boolean = true,
    isFillMaxWidth: Boolean = false,
    @DrawableRes leftIcon: Int? = null,
    leftIconTint: Color? = null,
    @DrawableRes rightIcon: Int? = null,
    rightIconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
    debounceTimeMills: Long = 500,
) {
    Box(
        modifier = if (isFillMaxWidth) {
            Modifier
                .padding(marginValues)
                .fillMaxWidth()
        } else {
            Modifier
                .padding(marginValues)
                .width(IntrinsicSize.Max)
        }
    ) {
        Row(
            modifier = Modifier
                .then(
                    if (isFillMaxWidth) {
                        Modifier.fillMaxWidth()
                    } else {
                        Modifier
                    }
                )
                .height(size.height)
                .background(
                    color = if (enable) backgroundColor else backgroundColor.copy(alpha = 0.3f),
                    shape = size.shape
                )
                .then(
                    if (type is SDGBoxButtonType.Line) {
                        Modifier.border(
                            width = 1.dp,
                            color = if (enable) type.lineColor else type.lineColor.copy(alpha = 0.3f),
                            shape = size.shape
                        )
                    } else {
                        Modifier
                    }
                )
                .clip(size.shape)
                .then(
                    if (enable) {
                        Modifier.clickable(
                            hasRipple = true,
                            rippleColor = SDGColor.Neutral350,
                            debounceTimeMills = debounceTimeMills,
                            onClick = onClick,
                        )
                    } else {
                        Modifier
                    }
                )
                .padding(horizontal = size.horizontalSpacing),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val iconSize = size.iconSize - if (iconDownSize) 2.dp else 0.dp

            if (leftIcon != null && leftIconTint != null) {
                SDGBoxButtonIcon(
                    size = iconSize,
                    iconResId = leftIcon,
                    iconTint = leftIconTint,
                    enable = enable
                )
                Spacer(modifier = Modifier.width(size.iconGap))
            }

            SDGText(
                text = label,
                textColor = if (enable) labelColor else labelColor.copy(alpha = 0.3f),
                textAlign = TextAlign.Center,
                typography = size.typography,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            if (rightIcon != null && rightIconTint != null) {
                Spacer(modifier = Modifier.width(size.iconGap))
                SDGBoxButtonIcon(
                    size = iconSize,
                    iconResId = rightIcon,
                    iconTint = rightIconTint,
                    enable = enable
                )
            }
        }
    }
}

@Composable
private fun SDGBoxButtonIcon(
    size: Dp,
    @DrawableRes iconResId: Int,
    iconTint: Color,
    enable: Boolean
) {
    SDGImage(
        modifier = Modifier.size(size),
        resId = iconResId,
        color = if (enable) iconTint else iconTint.copy(alpha = 0.3f)
    )
}