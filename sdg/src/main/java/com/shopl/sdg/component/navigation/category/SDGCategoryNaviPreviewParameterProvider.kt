package com.shopl.sdg.component.navigation.category

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_resource.R
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

internal class SDGCategoryNaviPreviewParameterProvider :
    PreviewParameterProvider<SDGCategoryNaviPreviewParameter> {

    private val backIcon = SDGCategoryNaviIconItem(
        iconResId = R.drawable.ic_navi_back_android,
        onClick = {},
        contentDescription = "뒤로가기",
    )
    private val rightIcons = persistentListOf(
        SDGCategoryNaviIconItem(
            iconResId = R.drawable.ic_navi_search,
            onClick = {},
            contentDescription = "검색",
        ),
        SDGCategoryNaviIconItem(
            iconResId = R.drawable.ic_navi_filter,
            onClick = {},
            showDot = true,
            contentDescription = "필터",
        ),
        SDGCategoryNaviIconItem(
            iconResId = R.drawable.ic_navi_chat,
            onClick = {},
            contentDescription = "채팅",
        ),
    )
    private val defaultParameter = SDGCategoryNaviPreviewParameter(
        title = "Label",
        leftIcon = backIcon,
        rightIcons = rightIcons,
    )

    override val values: Sequence<SDGCategoryNaviPreviewParameter> = sequenceOf(
        defaultParameter,
        defaultParameter.copy(leftIcon = null),
        defaultParameter.copy(title = null),
        defaultParameter.copy(hasDropdown = SDGCategoryNaviHasDropdown.False),
        defaultParameter.copy(rightIcons = persistentListOf(rightIcons[0])),
        defaultParameter.copy(rightIcons = persistentListOf(rightIcons[0], rightIcons[1])),
        defaultParameter.copy(
            title = "긴 화면 타이틀이 들어가는 경우 말줄임표시 확인 필요",
        ),
        defaultParameter.copy(
            title = "우측 아이콘이 없는 긴 화면 타이틀이 들어가는 경우 말줄임표시 확인 필요",
            rightIcons = null,
        ),
        defaultParameter.copy(rightIcons = persistentListOf()),
        defaultParameter.copy(
            backgroundColor = SDGColor.Primary300,
            titleColor = SDGColor.Neutral0,
            leftIcon = backIcon.copy(iconColor = SDGColor.Neutral0),
            rightIcons = persistentListOf(
                rightIcons[0].copy(iconColor = SDGColor.Neutral0),
                rightIcons[1].copy(iconColor = SDGColor.Neutral0),
                rightIcons[2].copy(iconColor = SDGColor.Neutral0),
            ),
        ),
    )
}

internal data class SDGCategoryNaviPreviewParameter(
    val title: String?,
    val leftIcon: SDGCategoryNaviIconItem?,
    val rightIcons: PersistentList<SDGCategoryNaviIconItem>?,
    val backgroundColor: Color = SDGColor.Neutral100,
    val titleColor: Color = SDGColor.Neutral900,
    val hasDropdown: SDGCategoryNaviHasDropdown = SDGCategoryNaviHasDropdown.True(onClick = {}),
)
