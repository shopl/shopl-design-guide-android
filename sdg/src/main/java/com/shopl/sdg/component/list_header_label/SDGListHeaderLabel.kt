package com.shopl.sdg.component.list_header_label

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.list_header_label.model.SDGListHeaderLabelCount
import com.shopl.sdg.component.list_header_label.model.SDGListHeaderLabelShowDropdown
import com.shopl.sdg.component.list_header_label.preview.SDGListHeaderLabelPreviewParameterProvider
import com.shopl.sdg.component.list_header_label.preview.SDGListHeaderLabelPreviewParams
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing2
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

/**
 * SDG - Component - List Header Label
 *
 * 리스트의 그룹 타이틀과 수량, 정렬 드롭다운을 통합 제공하는 리스트 상단 타이틀 컴포넌트
 *
 * @version 2.1.23
 *
 * @param label 리스트 그룹의 성격이나 분류 명칭을 전달하는 메인 타이틀
 * @param count 리스트 그룹에 포함된 전체 데이터 항목의 개수를 (숫자) 포맷으로 시각화하는 보조 인디케이터
 * @param showDropdown 하위 정렬 옵션이나 필터 팝업을 호출하기 위해 우측에 노출되는 선택적(Optional) 드롭다운 화살표 아이콘 및 클릭 처리
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=22084-3889&m=dev">Figma</a>
 */
@Composable
fun SDGListHeaderLabel(
    label: String,
    count: SDGListHeaderLabelCount,
    showDropdown: SDGListHeaderLabelShowDropdown,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = Spacing2),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = showDropdown.enabled,
                onClick = showDropdown.onClick,
            ),
    ) {
        SDGText(
            text = label,
            textColor = SDGColor.Neutral700,
            typography = SDGTypography.Body1SB,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(
                weight = 1f,
                fill = false,
            ),
        )

        if (count is SDGListHeaderLabelCount.True) {
            SDGText(
                text = "(${count.countValue})",
                textColor = SDGColor.Neutral700,
                typography = SDGTypography.Body1SB,
            )
        }

        if (showDropdown.enabled) {
            SDGImage(
                resId = R.drawable.ic_common_dropdown,
                color = SDGColor.Neutral700,
                modifier = Modifier.size(size = 20.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGListHeaderLabel(
    @PreviewParameter(provider = SDGListHeaderLabelPreviewParameterProvider::class)
    params: SDGListHeaderLabelPreviewParams,
) {
    SDGListHeaderLabel(
        label = params.label,
        count = params.count,
        showDropdown = params.showDropdown,
    )
}
