package com.shopl.sdg.ui.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.empty_icon.SDGEmptyIcon
import com.shopl.sdg.scene.ComponentScene
import com.shopl.sdg.ui.base.SDGSampleBaseScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_resource.R

@Composable
internal fun EmptyIconScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {
    SDGSampleBaseScaffold(
        name = ComponentScene.EmptyIcon.BasicEmptyIcon.displayLabel,
        description = "표시할 컨텐츠가 없는 최초 상태 또는 검색 결과가 없거나 데이터를 불러오지 못한 경우 아이콘으로 가볍게 표시하는 컴포넌트",
        bodyContent = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing40),
            ) {
                SDGEmptyIcon(
                    iconResId = R.drawable.ic_empty_data,
                    label = "결과가 없습니다.",
                    marginValues = PaddingValues(
                        start = SDGSpacing.Spacing28,
                        top = SDGSpacing.Spacing60,
                        end = SDGSpacing.Spacing28,
                    ),
                )
                SDGEmptyIcon(
                    iconResId = R.drawable.empty_member,
                    label = "등록된 구성원이 없습니다.",
                    marginValues = PaddingValues(horizontal = SDGSpacing.Spacing28),
                )
                SDGEmptyIcon(
                    iconResId = R.drawable.ic_empty_data,
                    label = "검색 결과가 없습니다. 검색어를 확인한 후 다시 검색해 주세요.",
                    marginValues = PaddingValues(horizontal = SDGSpacing.Spacing28),
                )
                SDGEmptyIcon(
                    iconResId = R.drawable.ic_empty_data,
                    label = "데이터를 불러오지 못했습니다.\n잠시 후 다시 시도해 주세요.",
                    marginValues = PaddingValues(horizontal = SDGSpacing.Spacing28),
                )
            }
        },
        onClickBack = onClickBack,
        onClickMenu = onClickMenu,
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewEmptyIconScreen() {
    ShoplDesignGuideTheme {
        EmptyIconScreen(onClickBack = {}, onClickMenu = {})
    }
}
