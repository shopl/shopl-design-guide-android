package com.shopl.sdg.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.badge.box.SDGBoxBadgeType
import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg.ui.base.SDGSampleBaseTab
import com.shopl.sdg_common.foundation.SDGColor
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

/**
 * SDG 샘플에서 Type 선택을 위한 Scroll Tab
 */
@Composable
internal fun <T> SDGSampleTypeTab(
    tabs: PersistentList<SDGSampleBaseTabItem<T>>,
    selectedTabIndex: Int,
    onTabClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    SDGSampleBaseTab(
        tabTitle = "Type",
        tabs = tabs,
        selectedTabIndex = selectedTabIndex,
        onTabClick = onTabClick,
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGSampleTypeTab() {
    val types = persistentListOf<SDGSampleBaseTabItem<SDGBoxBadgeType>>(
        SDGSampleBaseTabItem(
            title = SDGBoxBadgeType.Solid::class.simpleName.orEmpty(),
            item = SDGBoxBadgeType.Solid
        ),
        SDGSampleBaseTabItem(
            title = SDGBoxBadgeType.Line::class.simpleName.orEmpty(),
            item = SDGBoxBadgeType.Line(SDGColor.Neutral350)
        ),
    )
    SDGSampleTypeTab(
        tabs = types,
        selectedTabIndex = 0,
        onTabClick = {},
    )
}