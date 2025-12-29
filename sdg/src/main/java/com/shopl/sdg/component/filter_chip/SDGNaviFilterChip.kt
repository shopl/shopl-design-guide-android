package com.shopl.sdg.component.filter_chip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R
import kotlinx.collections.immutable.PersistentList

/**
 * SDG - Navi Filter Chip
 *
 * 상단 네비게이션바 바로 아래 위치하고, 조건에 맞는 결과값을 볼 수 있는 컴포넌트
 *
 * @param [filters] 표시할 필터 아이템 리스트
 * @param [onDeleteChipClick] 개별 칩 삭제 클릭 시 호출, index는 삭제할 칩의 위치
 * @param [onResetChipClick] 전체 초기화 버튼 클릭 시 호출
 * @param [chipBackgroundColor] 칩의 배경색 (지정 색상: WHITE 또는 LIGHT_GRAY)
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=21569-17672&m=dev">Figma</a>
 */
@Composable
fun SDGNaviFilterChip(
    filters: PersistentList<SDGNaviFilterItem>,
    onDeleteChipClick: (index: Int) -> Unit,
    onResetChipClick: () -> Unit,
    chipBackgroundColor: SDGNaviFilterChipColor = SDGNaviFilterChipColor.WHITE,
) {
    LazyRow(
        contentPadding = PaddingValues(
            horizontal = SDGSpacing.Spacing16,
            vertical = SDGSpacing.Spacing10,
        ),
        horizontalArrangement = Arrangement.spacedBy(space = SDGSpacing.Spacing10),
        modifier = Modifier.fillMaxWidth(),
    ) {
        item {
            ResetButton(
                onResetClick = onResetChipClick,
                backgroundColor = chipBackgroundColor.backgroundColor,
            )
        }

        itemsIndexed(items = filters) { index, filterItem ->
            NaviFilterChip(
                label = filterItem.label,
                count = filterItem.count,
                onDeleteClick = { onDeleteChipClick(index) },
                backgroundColor = chipBackgroundColor.backgroundColor,
            )
        }
    }
}

@Composable
private fun ResetButton(
    backgroundColor: Color,
    onResetClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clickable(onClick = onResetClick)
            .size(size = 24.dp)
            .background(
                color = backgroundColor,
                shape = SDGCornerRadius.BoxRadius.Radius8,
            )
    ) {
        SDGImage(
            resId = R.drawable.ic_common_refresh,
            color = SDGColor.Neutral400,
        )
    }
}

private const val MAX_LABEL_LENGTH = 16
private const val ELLIPSIS = "..."

@Composable
private fun NaviFilterChip(
    label: String,
    count: Int,
    onDeleteClick: () -> Unit,
    backgroundColor: Color,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = SDGSpacing.Spacing4),
        modifier = Modifier
            .height(height = 24.dp)
            .background(
                color = backgroundColor,
                shape = SDGCornerRadius.BoxRadius.Radius8
            )
            .padding(start = SDGSpacing.Spacing8, end = SDGSpacing.Spacing4)
    ) {
        Row {
            SDGText(
                text = if (label.length > MAX_LABEL_LENGTH) label.take(n = MAX_LABEL_LENGTH) + ELLIPSIS else label,
                typography = SDGTypography.Body2R,
                textColor = SDGColor.Neutral500,
                overflow = TextOverflow.Ellipsis
            )

            if (count > 1) {
                SDGText(
                    text = "+$count",
                    typography = SDGTypography.Body2R,
                    textColor = SDGColor.Neutral500,
                )
            }
        }

        Box {
            SDGImage(
                resId = R.drawable.ic_common_x,
                color = SDGColor.Neutral300,
                modifier = Modifier
                    .clickable(onClick = onDeleteClick)
                    .padding(all = SDGSpacing.Spacing4)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSDGNaviFilterChip(
    @PreviewParameter(SDGNaviFilterChipPreviewParameterProvider::class)
    params: SDGNaviFilterChipPreviewParams,
) {
    Box(modifier = Modifier.background(color = params.backgroundColor)) {
        SDGNaviFilterChip(
            filters = params.filters,
            chipBackgroundColor = params.chipBackgroundColor,
            onDeleteChipClick = { },
            onResetChipClick = { },
        )
    }
}
