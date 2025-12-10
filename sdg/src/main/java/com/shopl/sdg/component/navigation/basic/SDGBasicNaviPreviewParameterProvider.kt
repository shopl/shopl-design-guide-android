package com.shopl.sdg.component.navigation.basic

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_resource.R
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

internal class SDGBasicNaviPreviewParameterProvider :
    PreviewParameterProvider<SDGBasicNaviPreviewParameter> {

    override val values: Sequence<SDGBasicNaviPreviewParameter> = sequenceOf(
        기본_화면_타이틀(),
        우측_아이콘이_없는_긴_화면_타이틀(),
        우측_아이콘만_있는_경우(),
        긴_화면_타이틀(),
        배경색_변경_Primary()
    )

    private fun 기본_화면_타이틀(): SDGBasicNaviPreviewParameter {
        return SDGBasicNaviPreviewParameter(
            title = "화면 타이틀",
            backgroundColor = SDGColor.Neutral0,
            leftIcon = SDGBasicNaviIconItem(
                resId = R.drawable.ic_navi_back_android,
                onClick = {}
            ),
            rightIcons = null
        )
    }

    private fun 우측_아이콘이_없는_긴_화면_타이틀(): SDGBasicNaviPreviewParameter {
        return SDGBasicNaviPreviewParameter(
            title = "우측 아이콘이 없는 긴 화면 타이틀이 들어가는 경우 말줄임표시 확인 필요",
            backgroundColor = SDGColor.Neutral0,
            leftIcon = SDGBasicNaviIconItem(
                resId = R.drawable.ic_navi_back_android,
                onClick = {}
            ),
            rightIcons = null
        )
    }

    private fun 긴_화면_타이틀(): SDGBasicNaviPreviewParameter {
        return SDGBasicNaviPreviewParameter(
            title = "긴 화면 타이틀이 들어가는 경우 말줄임표시 확인 필요",
            backgroundColor = SDGColor.Neutral0,
            leftIcon = SDGBasicNaviIconItem(
                resId = R.drawable.ic_navi_back_android,
                onClick = {}
            ),
            rightIcons = persistentListOf(
                SDGBasicNaviIconItem(R.drawable.ic_navi_search, {}),
                SDGBasicNaviIconItem(R.drawable.ic_navi_filter, {}),
                SDGBasicNaviIconItem(R.drawable.ic_navi_chat, {})
            )
        )
    }

    private fun 우측_아이콘만_있는_경우(): SDGBasicNaviPreviewParameter {
        return SDGBasicNaviPreviewParameter(
            title = "우측 아이콘만 있는 경우",
            backgroundColor = SDGColor.Neutral0,
            leftIcon = null,
            rightIcons = persistentListOf(SDGBasicNaviIconItem(R.drawable.ic_navi_filter, {}))
        )
    }

    private fun 배경색_변경_Primary(): SDGBasicNaviPreviewParameter {
        return SDGBasicNaviPreviewParameter(
            title = "배경색 변경된 긴 글자 텍스트",
            backgroundColor = SDGColor.Primary300,
            titleColor = SDGColor.Neutral0,
            leftIcon = SDGBasicNaviIconItem(
                resId = R.drawable.ic_navi_back_android,
                onClick = {},
                color = SDGColor.Neutral0
            ),
            rightIcons = persistentListOf(
                SDGBasicNaviIconItem(R.drawable.ic_navi_close, {}, color = SDGColor.Neutral0)
            )
        )
    }
}

internal data class SDGBasicNaviPreviewParameter(
    val title: String,
    val backgroundColor: Color,
    val titleColor: Color = SDGColor.Neutral900,
    val leftIcon: SDGBasicNaviIconItem?,
    val rightIcons: PersistentList<SDGBasicNaviIconItem>?
)
