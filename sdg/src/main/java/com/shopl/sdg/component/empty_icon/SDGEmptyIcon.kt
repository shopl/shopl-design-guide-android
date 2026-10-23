package com.shopl.sdg.component.empty_icon

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.empty_icon.preview.SDGEmptyIconPreviewParameter
import com.shopl.sdg.component.empty_icon.preview.SDGEmptyIconPreviewParameterProvider
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText

private val SDGEmptyIconSize = 76.dp

/**
 * SDG - Empty Icon
 *
 * 표시할 컨텐츠가 없는 최초 상태 또는 검색 결과가 없거나 데이터를 불러오지 못한 경우 아이콘으로 가볍게 표시하는 컴포넌트
 *
 * @version 2.3.35
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=8638-35877&m=dev">Figma</a>
 */
@Composable
fun SDGEmptyIcon(
    @DrawableRes iconResId: Int,
    label: String,
    marginValues: PaddingValues = PaddingValues(),
) {
    Column(
        modifier = Modifier
            .padding(marginValues)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = spacedBy(SDGSpacing.Spacing10),
    ) {
        SDGImage(
            modifier = Modifier.size(SDGEmptyIconSize),
            resId = iconResId,
            color = SDGColor.Neutral300,
        )
        SDGText(
            text = label,
            textColor = SDGColor.Neutral300,
            typography = SDGTypography.Body1R,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true, widthDp = 375)
@Composable
private fun PreviewSDGEmptyIcon(
    @PreviewParameter(SDGEmptyIconPreviewParameterProvider::class)
    parameter: SDGEmptyIconPreviewParameter,
) {
    with(parameter) {
        SDGEmptyIcon(
            iconResId = iconResId,
            label = label,
            marginValues = marginValues,
        )
    }
}
