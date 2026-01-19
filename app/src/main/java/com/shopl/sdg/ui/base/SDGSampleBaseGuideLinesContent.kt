package com.shopl.sdg.ui.base

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

/**
 * SDG 샘플 가이드 라인 표기를 위한 Base Content
 *
 * @param guideLineDescriptions 가이드 라인에 대한 설명
 */
@Composable
internal fun SDGSampleBaseGuideLinesContent(
    guideLineDescriptions: PersistentList<String>
) {
    Column {
        guideLineDescriptions.forEach {
            SDGText(
                text = "# $it",
                textColor = SDGColor.Neutral500,
                typography = SDGTypography.Body2SB
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSDGSampleBaseGuideLinesContent() {
    SDGSampleBaseGuideLinesContent(
        guideLineDescriptions = persistentListOf(
            "첫번째 가이드 라인입니다.",
            "두번째 가이드 라인입니다.",
            "세번째 가이드 라인입니다.",
        )
    )
}