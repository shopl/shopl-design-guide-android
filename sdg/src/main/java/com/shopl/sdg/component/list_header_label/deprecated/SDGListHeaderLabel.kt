package com.shopl.sdg.component.list_header_label.deprecated

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing2
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

/**
 * 신규 List Header Label API와의 하위 호환성을 위한 레거시 API입니다.
 */
@Deprecated("v2.1.23 이상 SDGListHeaderLabel을 사용하세요.")
@Composable
fun SDGListHeaderLabel(
    title: String,
    count: String?,
    dropdownIcon: Boolean,
    onIconClick: (() -> Unit)? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = Spacing2),
        modifier = Modifier.fillMaxWidth(),
    ) {
        SDGText(
            text = title,
            textColor = SDGColor.Neutral700,
            typography = SDGTypography.Body1SB,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(weight = 1f, fill = false),
        )

        if (count != null) {
            SDGText(
                text = "($count)",
                textColor = SDGColor.Neutral700,
                typography = SDGTypography.Body1SB,
            )
        }

        if (dropdownIcon) {
            SDGImage(
                resId = R.drawable.ic_common_dropdown,
                color = SDGColor.Neutral700,
                modifier = Modifier
                    .size(size = 20.dp)
                    .clickable { onIconClick?.invoke() },
            )
        }
    }
}
