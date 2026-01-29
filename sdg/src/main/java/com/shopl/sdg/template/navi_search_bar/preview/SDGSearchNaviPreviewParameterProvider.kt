package com.shopl.sdg.template.navi_search_bar.preview

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.template.navi_search_bar.SDGNaviSearchBarType
import com.shopl.sdg_common.foundation.SDGColor

internal class SDGSearchNaviPreviewParameterProvider :
    PreviewParameterProvider<SDGSearchNaviPreviewData> {
    override val values: Sequence<SDGSearchNaviPreviewData> = sequenceOf(
        SDGSearchNaviPreviewData(
            type = SDGNaviSearchBarType.Full(
                onClickBack = {},
                onClickClose = {},
                leftIconColor = SDGColor.Neutral0,
                rightIconColor = SDGColor.Neutral0
            ),
            input = "베스트 상품",
            hint = "검색어를 입력하세요",
            backgroundColor = SDGColor.Secondary300
        ),
        SDGSearchNaviPreviewData(
            type = SDGNaviSearchBarType.Back(
                onClickBack = {},
                rightIconColor = SDGColor.Neutral0
            ),
            input = "",
            hint = "브랜드를 검색해보세요",
            backgroundColor = SDGColor.Primary300
        ),
        SDGSearchNaviPreviewData(
            type = SDGNaviSearchBarType.Close(
                onClickClose = {},
                iconColor = SDGColor.Neutral0
            ),
            input = "검색 결과",
            hint = "검색어를 입력하세요",
            backgroundColor = SDGColor.Neutral900
        )
    )
}

internal data class SDGSearchNaviPreviewData(
    val type: SDGNaviSearchBarType,
    val input: String,
    val hint: String,
    val backgroundColor: Color
)

