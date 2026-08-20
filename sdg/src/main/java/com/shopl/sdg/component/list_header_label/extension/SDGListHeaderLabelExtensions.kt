package com.shopl.sdg.component.list_header_label.extension

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.list_header_label.SDGListHeaderLabel
import com.shopl.sdg.component.list_header_label.model.SDGListHeaderLabelShowCount
import com.shopl.sdg.component.list_header_label.model.SDGListHeaderLabelShowDropdown
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_resource.R

/**
 * [RowScope]에서 weight를 지정할 수 있는 [SDGListHeaderLabel]입니다.
 */
@Composable
fun RowScope.SDGListHeaderLabel(
    weight: Float,
    label: String,
    count: SDGListHeaderLabelShowCount,
    showDropdown: SDGListHeaderLabelShowDropdown,
) {
    Box(
        modifier = Modifier.weight(weight = weight),
    ) {
        SDGListHeaderLabel(
            label = label,
            showCount = count,
            showDropdown = showDropdown,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGListHeaderLabel() {
    Row(modifier = Modifier.fillMaxWidth()) {
        SDGListHeaderLabel(
            weight = 1f,
            label = "리스트 타이틀",
            count = SDGListHeaderLabelShowCount.True(countValue = "1"),
            showDropdown = SDGListHeaderLabelShowDropdown.True(onClick = {}),
        )

        SDGImage(
            resId = R.drawable.ic_alignup,
            color = SDGColor.Neutral700,
        )
    }
}
