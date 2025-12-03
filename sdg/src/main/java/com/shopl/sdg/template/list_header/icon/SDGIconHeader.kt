package com.shopl.sdg.template.list_header.icon

import androidx.annotation.DrawableRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg.template.list_header.icon.preview.SDGIconHeaderPreviewParam
import com.shopl.sdg.template.list_header.icon.preview.SDGIconHeaderPreviewParameterProvider
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing12
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing3
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing4
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing8
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - List Header - Icon Header
 *
 * 리스트 최상단에 표시되는 정보와 아이콘이 조합된 템플릿
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=21463-14546&m=dev">Figma</a>
 */
@Composable
fun SDGIconHeader(
    label: String,
    count: String? = null,
    @DrawableRes iconResId: Int? = null,
    iconColor: Color? = null,
    iconHeaderRightItem: SDGIconHeaderRightItem? = null,
    onIconClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing4),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing12)
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing3)
        ) {
            SDGText(
                text = label,
                textColor = SDGColor.Neutral700,
                typography = SDGTypography.Body1SB
            )

            if (count != null) {
                SDGText(
                    text = count,
                    textColor = SDGColor.Neutral700,
                    typography = SDGTypography.Body1SB
                )
            }

            if (iconResId != null) {
                SDGImage(
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onIconClick?.invoke() },
                    resId = iconResId,
                    color = iconColor
                )
            }
        }

        if (iconHeaderRightItem != null) {
            Row(
                modifier = if (iconHeaderRightItem.isBox) {
                    Modifier
                        .border(
                            width = 1.dp,
                            color = SDGColor.Neutral200,
                            shape = SDGCornerRadius.BoxRadius.Radius6
                        )
                        .padding(horizontal = Spacing8, vertical = Spacing4)
                } else {
                    Modifier
                        .padding(horizontal = Spacing8)
                },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Spacing8)
            ) {
                iconHeaderRightItem.icons.forEachIndexed { index, (resId, color, onClick) ->
                    SDGImage(
                        modifier = Modifier
                            .size(20.dp)
                            .clickable { onClick?.invoke() },
                        resId = resId,
                        color = color
                    )

                    if (iconHeaderRightItem.icons.size > 1 && index != iconHeaderRightItem.icons.lastIndex) {
                        VerticalDivider(
                            modifier = Modifier
                                .width(1.dp)
                                .height(14.dp),
                            color = SDGColor.Neutral200
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGIconHeader(
    @PreviewParameter(SDGIconHeaderPreviewParameterProvider::class)
    param: SDGIconHeaderPreviewParam
) {
    SDGIconHeader(
        label = param.label,
        count = param.count,
        iconResId = param.iconResId,
        iconColor = param.iconColor,
        iconHeaderRightItem = param.iconHeaderRightItem,
    )
}