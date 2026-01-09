package com.shopl.sdg.template.list_header.icon

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.util.list_header_label.SDGListHeaderLabel
import com.shopl.sdg.template.list_header.icon.preview.SDGIconHeaderPreviewParam
import com.shopl.sdg.template.list_header.icon.preview.SDGIconHeaderPreviewParameterProvider
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing12
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing4
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing8
import com.shopl.sdg_common.ui.components.SDGImage

/**
 * SDG - List Header - Icon Header
 *
 * 리스트 최상단에 표시되는 정보와 아이콘이 조합된 템플릿
 *
 * @param count Local Format을 위한 String 타입
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=21463-14546&m=dev">Figma</a>
 */
@Composable
fun SDGIconHeader(
    label: String,
    count: String? = null,
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
        SDGListHeaderLabel(
            weight = 1f,
            title = label,
            count = count,
            onIconClick = onIconClick
        )

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
        iconHeaderRightItem = param.iconHeaderRightItem,
    )
}