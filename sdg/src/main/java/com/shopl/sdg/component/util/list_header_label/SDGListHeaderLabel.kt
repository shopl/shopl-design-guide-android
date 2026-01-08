package com.shopl.sdg.component.util.list_header_label

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shopl.sdg.component.list_header_label.SDGListHeaderLabel

/**
 * [RowScope] [SDGListHeaderLabel]
 * weight 값 지정 가능
 */
@Composable
fun RowScope.SDGListHeaderLabel(
    weight: Float,
    title: String,
    count: String?,
    onIconClick: (() -> Unit)? = null,
) {
    Box(
        modifier = Modifier.weight(weight)
    ) {
        SDGListHeaderLabel(
            title = title,
            count = count,
            onIconClick = onIconClick
        )
    }
}

