package com.shopl.sdg.component.navigation.category.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.navigation.category.SDGCategoryNaviHasDropdown
import com.shopl.sdg.component.navigation.category.SDGCategoryNaviIconItem
import com.shopl.sdg.component.navigation.category.SDGCategoryNaviLabel
import com.shopl.sdg.component.navigation.category.SDGCategoryNaviLeftIconItem
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_resource.R
import kotlinx.collections.immutable.persistentListOf

internal class SDGCategoryNaviPreviewParameterProvider :
    PreviewParameterProvider<SDGCategoryNaviPreviewParameter> {

    private val backIcon = SDGCategoryNaviLeftIconItem(
        onClick = {},
        contentDescription = "뒤로가기",
    )
    private val rightIcons = persistentListOf(
        SDGCategoryNaviIconItem(
            iconResId = R.drawable.ic_navi_search,
            onClick = {},
            iconColor = null,
            showDot = false,
        ),
        SDGCategoryNaviIconItem(
            iconResId = R.drawable.ic_navi_filter,
            onClick = {},
            iconColor = null,
            showDot = true,
        ),
        SDGCategoryNaviIconItem(
            iconResId = R.drawable.ic_navi_chat,
            onClick = {},
            iconColor = null,
            showDot = false,
        ),
    )
    private val defaultParameter = SDGCategoryNaviPreviewParameter(
        label = SDGCategoryNaviLabel(text = "Label", color = SDGColor.Neutral900),
        leftIcon = backIcon,
        rightIcons = rightIcons,
    )

    override val values: Sequence<SDGCategoryNaviPreviewParameter> = sequenceOf(
        기본_상태(),
        뒤로가기_아이콘_미노출(),
        라벨_미노출(),
        드롭다운_미노출(),
        우측_아이콘_한_개(),
        우측_아이콘_두_개(),
        긴_라벨_말줄임(),
        우측_아이콘이_없는_긴_라벨_말줄임(),
        우측_아이콘_미노출(),
        배경색_변경_Primary(),
    )

    private fun 기본_상태() = defaultParameter

    private fun 뒤로가기_아이콘_미노출() = defaultParameter.copy(leftIcon = null)

    private fun 라벨_미노출() = defaultParameter.copy(label = defaultParameter.label.copy(text = null))

    private fun 드롭다운_미노출() = defaultParameter.copy(hasDropdown = SDGCategoryNaviHasDropdown.False)

    private fun 우측_아이콘_한_개() = defaultParameter.copy(rightIcons = persistentListOf(rightIcons[0]))

    private fun 우측_아이콘_두_개() = defaultParameter.copy(
        rightIcons = persistentListOf(rightIcons[0], rightIcons[1]),
    )

    private fun 긴_라벨_말줄임() = defaultParameter.copy(
        label = defaultParameter.label.copy(
            text = "긴 화면 타이틀이 들어가는 경우 말줄임표시 확인 필요",
        ),
    )

    private fun 우측_아이콘이_없는_긴_라벨_말줄임() = defaultParameter.copy(
        label = defaultParameter.label.copy(
            text = "우측 아이콘이 없는 긴 화면 타이틀이 들어가는 경우 말줄임표시 확인 필요",
        ),
        rightIcons = null,
    )

    private fun 우측_아이콘_미노출() = defaultParameter.copy(rightIcons = persistentListOf())

    private fun 배경색_변경_Primary() = defaultParameter.copy(
        backgroundColor = SDGColor.Primary300,
        label = defaultParameter.label.copy(color = SDGColor.Neutral0),
        rightIcons = persistentListOf(
            rightIcons[0].copy(iconColor = SDGColor.Neutral0),
            rightIcons[1].copy(iconColor = SDGColor.Neutral0),
            rightIcons[2].copy(iconColor = SDGColor.Neutral0),
        ),
    )
}
