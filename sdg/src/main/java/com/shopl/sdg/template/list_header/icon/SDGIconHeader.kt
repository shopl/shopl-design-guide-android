package com.shopl.sdg.component.header.icon

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing12
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing3
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing4
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - List Header - Icon Header
 *
 * 리스트 최상단에 표시되는 정보와 아이콘이 조합된 템플릿
 */
@Composable
fun SDGIconHeader(
    label: String,
    count: String? = null,
    backgroundColor: Color = SDGColor.Transparent,
    marginValues: PaddingValues = PaddingValues(),
    @DrawableRes iconResId: Int? = null,
    iconColor: Color? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = backgroundColor)
            .padding(horizontal = Spacing4),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing12)
    ) {
        Row(
            modifier = Modifier.weight(1f, false),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing3)
        ) {
            if (iconResId != null && iconColor != null) {
                SDGImage(
                    modifier = Modifier.size(24.dp),
                    resId = iconResId,
                    color = iconColor
                )
            }

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
        }
    }
}
