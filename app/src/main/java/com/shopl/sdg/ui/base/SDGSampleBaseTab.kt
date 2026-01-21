package com.shopl.sdg.ui.base

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.tab.scroll.SDGScrollTab
import com.shopl.sdg.component.tab.scroll.SDGScrollTabType
import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

/**
 * SDG 샘플에서 사용되는 Scroll Tab(Type, Spec, 등)
 *
 * @param tabTitle 탭의 타이틀
 * @param tabs 탭 선택가능 값
 */
@Composable
internal fun <T> SDGSampleBaseTab(
    tabTitle: String,
    tabs: PersistentList<SDGSampleBaseTabItem<T>>,
    selectedTabIndex: Int,
    onTabClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing8)
    ) {
        SDGText(
            text = tabTitle,
            textColor = SDGColor.Neutral350,
            typography = SDGTypography.Body3SB
        )
        SDGScrollTab(
            type = SDGScrollTabType.Text,
            titles = tabs.map { it.title }.toPersistentList(),
            selectedIndex = selectedTabIndex,
            onTabClick = onTabClick,
            backgroundColor = SDGColor.Transparent
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGSampleBaseTab() {
    val properties = persistentListOf<SDGSampleBaseTabItem<Any>>(
        SDGSampleBaseTabItem(
            title = "Property 1",
            item = 0
        ),
        SDGSampleBaseTabItem(
            title = "Property 2",
            item = 1
        ),
        SDGSampleBaseTabItem(
            title = "Property 3",
            item = 2
        ),
    )
    SDGSampleBaseTab(
        tabTitle = "Property",
        tabs = properties,
        selectedTabIndex = 0,
        onTabClick = {}
    )
}
