package com.shopl.sdg.component.list_header_label.legacy.extension

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.list_header_label.legacy.SDGListHeaderLabel
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_resource.R

/** 신규 List Header Label RowScope API와의 하위 호환성을 위한 레거시 API입니다. */
@Deprecated("v2.1.23 이상 SDGListHeaderLabel을 사용하세요.")
@Composable
fun RowScope.SDGListHeaderLabel(
    weight: Float,
    title: String,
    count: String?,
    dropdownIcon: Boolean,
    onIconClick: (() -> Unit)? = null,
) {
    Box(
        modifier = Modifier.weight(weight)
    ) {
        SDGListHeaderLabel(
            title = title,
            count = count,
            dropdownIcon = dropdownIcon,
            onIconClick = onIconClick,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGListHeaderLabel() {
    Row(modifier = Modifier.fillMaxWidth()) {
        SDGListHeaderLabel(
            weight = 1f,
            title = "Weighted 1",
            count = "1",
            dropdownIcon = true,
        )

        SDGImage(
            resId = R.drawable.ic_alignup,
            color = SDGColor.Neutral700,
        )
    }
}
