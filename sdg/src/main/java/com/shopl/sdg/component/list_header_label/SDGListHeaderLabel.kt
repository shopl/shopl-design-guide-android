package com.shopl.sdg.component.list_header_label

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing2
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing4
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

/**
 * SDG - List Header Label
 *
 * 리스트 상단에 위치하는 타이틀 컴포넌트
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=22084-3889&m=dev">Figma</a>
 */
@Composable
fun SDGListHeaderLabel(
    title: String,
    count: String?,
    onIconClick: (() -> Unit)? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing2),
        modifier = Modifier.fillMaxWidth()
    ) {
        SDGText(
            text = title,
            textColor = SDGColor.Neutral700,
            typography = SDGTypography.Body1SB
        )

        if (count != null) {
            SDGText(
                text = "($count)",
                textColor = SDGColor.Neutral700,
                typography = SDGTypography.Body1SB
            )
        }

        SDGImage(
            resId = R.drawable.ic_common_dropdown,
            color = SDGColor.Neutral700,
            modifier = Modifier
                .size(20.dp)
                .clickable { onIconClick?.invoke() }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGListHeaderLabel() {
    Column(
        modifier = Modifier.padding(Spacing4),
        verticalArrangement = Arrangement.spacedBy(Spacing4)
    ) {
        SDGListHeaderLabel(
            title = "Title Only",
            count = "1"
        )
    }
}