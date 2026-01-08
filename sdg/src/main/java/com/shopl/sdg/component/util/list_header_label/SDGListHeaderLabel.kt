package com.shopl.sdg.component.util.list_header_label

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.list_header_label.SDGListHeaderLabel
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing4
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_resource.R

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

@Preview(showBackground = true)
@Composable
private fun PreviewSDGListHeaderLabel() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Spacing4)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            SDGListHeaderLabel(
                weight = 1f,
                title = "Weighted 1",
                count = "1"
            )
            
            SDGImage(
                resId = R.drawable.ic_alignup,
                color = SDGColor.Neutral700,
            )
        }
    }
}