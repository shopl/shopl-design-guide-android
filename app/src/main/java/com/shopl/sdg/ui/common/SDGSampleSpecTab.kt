package com.shopl.sdg.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.button.box.SDGBoxButtonSize
import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg.ui.base.SDGSampleBaseTab
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

/**
 * SDG 샘플에서 Spec 선택을 위한 Scroll Tab
 */
@Composable
internal fun <T> SDGSampleSpecTab(
    tabs: PersistentList<SDGSampleBaseTabItem<T>>,
    selectedTabIndex: Int,
    onTabClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    SDGSampleBaseTab(
        tabTitle = "Spec",
        tabs = tabs,
        selectedTabIndex = selectedTabIndex,
        onTabClick = onTabClick,
        modifier = modifier,
    )
}


@Preview
@Composable
private fun PreviewSDGSampleSpecTab() {
    val specs = persistentListOf<SDGSampleBaseTabItem<SDGBoxButtonSize>>(
        SDGSampleBaseTabItem(
            title = SDGBoxButtonSize.Medium::class.simpleName.orEmpty(),
            item = SDGBoxButtonSize.Medium
        ),
        SDGSampleBaseTabItem(
            title = SDGBoxButtonSize.Small::class.simpleName.orEmpty(),
            item = SDGBoxButtonSize.Small
        ),
        SDGSampleBaseTabItem(
            title = SDGBoxButtonSize.XSmall::class.simpleName.orEmpty(),
            item = SDGBoxButtonSize.XSmall
        ),
    )
    SDGSampleSpecTab(
        tabs = specs,
        selectedTabIndex = 0,
        onTabClick = {}
    )
}