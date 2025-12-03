package com.shopl.sdg.component.header.icon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing12
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing4

/**
 * SDG - List Header - Icon Header
 *
 * 리스트 최상단에 표시되는 정보와 아이콘이 조합된 템플릿
 */
@Composable
fun SDGIconHeader(
    label: String,
    backgroundColor: Color = SDGColor.Transparent,
    marginValues: PaddingValues = PaddingValues(),
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = backgroundColor)
            .padding(horizontal = Spacing4),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing12)
    ) {
        // TODO: Implement Content
    }
}