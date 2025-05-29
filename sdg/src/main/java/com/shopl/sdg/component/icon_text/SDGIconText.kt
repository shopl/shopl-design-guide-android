package com.shopl.sdg.component.icon_text

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_common.util.textCenterAlignment
import com.shopl.sdg_resource.R

/**
 * DesignSystem - Icon List Item
 *
 * @param size [SDGIconListItemSize] 내부 텍스트 Size, LineHeight 조절
 * @param type [SDGIconListItemType] 내부 텍스트 Weight 조절
 * @param spacing [SDGIconListItemSpacing] 아이콘과 텍스트 사이 간격
 */
//TODO: SDGIconText로 이름 수정 필요
@Composable
fun SDGIconListItem(
    text: String,
    textColor: Color,
    size: SDGIconListItemSize,
    type: SDGIconListItemType,
    spacing: SDGIconListItemSpacing,
    modifier: Modifier = Modifier,
    @DrawableRes leftIconResId: Int? = null,
    leftIconTint: Color? = null,
    onClickLeftIcon: (() -> Unit)? = null,
    @DrawableRes rightIconResId: Int? = null,
    rightIconTint: Color? = null,
    onClickRightIcon: (() -> Unit)? = null,
    maxLines: Int = Int.MAX_VALUE,
    isFillMaxWidth: Boolean = true,
) {
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    val alignment = remember(textLayoutResult) {
        textCenterAlignment(textLayoutResult)
    }

    val typography = getIconListItemTypography(type = type, size = size)

    Row(
        modifier = modifier.then(
            if (isFillMaxWidth) {
                Modifier.fillMaxWidth()
            } else {
                Modifier
            }
        ),
        horizontalArrangement = Arrangement.spacedBy(spacing.value)
    ) {
        if (leftIconResId != null && leftIconTint != null) {
            Image(
                modifier = Modifier
                    .align(alignment)
                    .size(14.dp)
                    .then(
                        if (onClickLeftIcon != null) {
                            Modifier.clickable { onClickLeftIcon() }
                        } else {
                            Modifier
                        }
                    ),
                painter = painterResource(id = leftIconResId),
                contentDescription = null,
                colorFilter = ColorFilter.tint(leftIconTint)
            )
        }
        SDGText(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f, isFillMaxWidth),
            text = text,
            textColor = textColor,
            typography = typography,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResult = it }
        )
        if (rightIconResId != null && rightIconTint != null) {
            Image(
                modifier = Modifier
                    .align(alignment)
                    .size(14.dp)
                    .then(
                        if (onClickRightIcon != null) {
                            Modifier.clickable { onClickRightIcon() }
                        } else {
                            Modifier
                        }
                    ),
                painter = painterResource(id = rightIconResId),
                contentDescription = null,
                colorFilter = ColorFilter.tint(rightIconTint)
            )
        }
    }
}

private fun getIconListItemTypography(
    type: SDGIconListItemType,
    size: SDGIconListItemSize
): SDGTypography {
    return when (type) {
        SDGIconListItemType.Basic -> when (size) {
            SDGIconListItemSize.Size12 -> SDGTypography.Body3R
            SDGIconListItemSize.Size14 -> SDGTypography.Body2R
            SDGIconListItemSize.Size16 -> SDGTypography.Body1R
        }

        SDGIconListItemType.Empha -> when (size) {
            SDGIconListItemSize.Size12 -> SDGTypography.Body3SB
            SDGIconListItemSize.Size14 -> SDGTypography.Body2SB
            SDGIconListItemSize.Size16 -> SDGTypography.Body1SB
        }
    }
}

enum class SDGIconListItemType {
    Basic,
    Empha
}

enum class SDGIconListItemSize {
    Size12,
    Size14,
    Size16
}

enum class SDGIconListItemSpacing(val value: Dp) {
    Spacing2(value = 2.dp),
    Spacing4(value = 4.dp),
}

@Preview
@Composable
private fun PrevIOIconListItem(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier
            .background(color = SDGColor.Neutral0)
            .padding(10.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            SDGIconListItem(
                text = "IOIconText",
                textColor = SDGColor.Neutral700,
                size = SDGIconListItemSize.Size16,
                type = SDGIconListItemType.Empha,
                spacing = SDGIconListItemSpacing.Spacing4,
                leftIconResId = R.drawable.ic_common_warning,
                leftIconTint = SDGColor.Neutral700,
            )
            SDGIconListItem(
                isFillMaxWidth = false,
                text = "IOIconText",
                textColor = SDGColor.Neutral700,
                size = SDGIconListItemSize.Size14,
                type = SDGIconListItemType.Empha,
                spacing = SDGIconListItemSpacing.Spacing2,
                rightIconResId = R.drawable.ic_common_warning,
                rightIconTint = SDGColor.Neutral700,
            )
            SDGIconListItem(
                text = "IOIconText IOIconText IOIconText IOIconText IOIconText IOIconText IOIconText IOIconText",
                textColor = SDGColor.Neutral700,
                size = SDGIconListItemSize.Size12,
                type = SDGIconListItemType.Basic,
                spacing = SDGIconListItemSpacing.Spacing4,
                leftIconResId = R.drawable.ic_common_warning,
                leftIconTint = SDGColor.Neutral700,
                rightIconResId = R.drawable.ic_common_warning,
                rightIconTint = SDGColor.Neutral700,
            )
        }
    }
}