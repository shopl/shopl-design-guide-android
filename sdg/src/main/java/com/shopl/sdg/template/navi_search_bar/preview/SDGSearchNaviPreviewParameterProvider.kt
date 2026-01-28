package com.shopl.sdg.template.navi_search_bar.preview

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.template.navi_search_bar.SDGSearchNaviType
import com.shopl.sdg_common.foundation.SDGColor

internal class SDGSearchNaviPreviewParameterProvider :
    PreviewParameterProvider<SDGSearchNaviPreviewData> {
    override val values: Sequence<SDGSearchNaviPreviewData> = sequenceOf(
        SDGSearchNaviPreviewData(
            type = SDGSearchNaviType.Full(
                onClickClose = {},
                iconColor = SDGColor.Neutral0
            ),
            input = "베스트 상품",
            hint = "검색어를 입력하세요",
            backgroundColor = SDGColor.Secondary300
        ),
        SDGSearchNaviPreviewData(
            type = SDGSearchNaviType.Back(
                onClickBack = {},
                iconColor = SDGColor.Neutral0
            ),
            input = "",
            hint = "브랜드를 검색해보세요",
            backgroundColor = SDGColor.Primary300
        )
    )
}

internal data class SDGSearchNaviPreviewData(
    val type: SDGSearchNaviType,
    val input: String,
    val hint: String,
    val backgroundColor: Color
)

