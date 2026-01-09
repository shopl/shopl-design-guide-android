package com.shopl.sdg.template.list_header.icon.preview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.template.list_header.icon.SDGIconHeaderRightItem
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_resource.R
import kotlinx.collections.immutable.persistentListOf

internal class SDGIconHeaderPreviewParameterProvider : PreviewParameterProvider<SDGIconHeaderPreviewParam> {
    override val values: Sequence<SDGIconHeaderPreviewParam> = sequenceOf(
        기본_헤더(),
        아이콘_헤더(),
        카운트_포함_헤더(),
        우측_단일아이콘_헤더(),
        우측_박스형_아이콘_헤더()
    )

    private fun 기본_헤더(): SDGIconHeaderPreviewParam {
        return SDGIconHeaderPreviewParam(
            label = "기본 라벨"
        )
    }

    private fun 아이콘_헤더(): SDGIconHeaderPreviewParam {
        return SDGIconHeaderPreviewParam(
            label = "아이콘 라벨",
        )
    }

    private fun 카운트_포함_헤더(): SDGIconHeaderPreviewParam {
        return SDGIconHeaderPreviewParam(
            label = "메일",
            count = "12",
        )
    }

    private fun 우측_단일아이콘_헤더(): SDGIconHeaderPreviewParam {
        return SDGIconHeaderPreviewParam(
            label = "검색",
            iconHeaderRightItem = SDGIconHeaderRightItem(
                icons = persistentListOf(
                    SDGIconHeaderRightItem.SDGIconHeaderIcon(R.drawable.ic_common_search, SDGColor.Neutral700)
                ),
                isBox = false
            )
        )
    }

    private fun 우측_박스형_아이콘_헤더(): SDGIconHeaderPreviewParam {
        return SDGIconHeaderPreviewParam(
            label = "멀티 아이콘",
            iconHeaderRightItem = SDGIconHeaderRightItem(
                icons = persistentListOf(
                    SDGIconHeaderRightItem.SDGIconHeaderIcon(R.drawable.ic_common_company, SDGColor.Neutral700),
                    SDGIconHeaderRightItem.SDGIconHeaderIcon(R.drawable.ic_common_staff, SDGColor.Neutral700)
                ),
                isBox = true
            )
        )
    }
}

internal data class SDGIconHeaderPreviewParam(
    val label: String = "Label",
    val count: String? = null,
    val marginValues: PaddingValues = PaddingValues(),
    val iconHeaderRightItem: SDGIconHeaderRightItem? = null
)