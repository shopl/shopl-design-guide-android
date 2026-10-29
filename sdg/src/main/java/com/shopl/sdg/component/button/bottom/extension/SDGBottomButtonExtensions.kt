package com.shopl.sdg.component.button.bottom.extension

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.button.bottom.SDGBottomButton
import com.shopl.sdg.component.button.bottom.SDGBottomButtonSpec
import com.shopl.sdg.component.button.bottom.SDGBottomButtonType
import com.shopl.sdg_common.foundation.spacing.SDGSpacing

/**
 * Box 내부에서 지정한 위치에 Bottom Button을 배치하는 기존 API입니다.
 * 크기와 유형을 변환해 신규 [SDGBottomButton]에 직접 위임합니다.
 *
 * @param align Box 내부에서 버튼을 정렬할 위치
 * @param title 버튼명
 * @param marginValues 버튼 외부 여백
 * @param spec 기존 Full 또는 Adaptive 크기
 * @param type 신규 색상 유형으로 변환할 기존 버튼 유형
 * @param enabled 버튼 활성화 여부
 * @param onClick 버튼 클릭 시 실행할 동작
 */
@Deprecated("BoxScope에서 size/style/label을 사용하는 신규 SDGBottomButton을 배치하세요.")
@Composable
@Suppress("DEPRECATION")
fun BoxScope.SDGBottomButton(
    align: Alignment,
    title: String,
    marginValues: PaddingValues = PaddingValues(),
    spec: SDGBottomButtonSpec = SDGBottomButtonSpec.FULL,
    type: SDGBottomButtonType = SDGBottomButtonType.POSITIVE,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Box(Modifier.align(align)) {
        SDGBottomButton(
            label = title,
            marginValues = marginValues,
            size = spec.toSize(),
            style = type.toStyle(),
            enabled = enabled,
            onClick = onClick,
        )
    }
}

/**
 * Row 내부에서 지정한 너비 비율로 Bottom Button을 배치하는 기존 API입니다.
 * 크기와 유형을 변환해 신규 [SDGBottomButton]에 직접 위임합니다.
 *
 * @param weight Row의 남은 너비를 나눠 가질 비율
 * @param title 버튼명
 * @param marginValues 버튼 외부 여백
 * @param size 기존 Full 또는 Adaptive 크기
 * @param type 신규 색상 유형으로 변환할 기존 버튼 유형
 * @param enabled 버튼 활성화 여부
 * @param onClick 버튼 클릭 시 실행할 동작
 */
@Deprecated("RowScope에서 size/style/label을 사용하는 신규 SDGBottomButton을 배치하세요.")
@Composable
@Suppress("DEPRECATION")
fun RowScope.SDGBottomButton(
    weight: Float,
    title: String,
    marginValues: PaddingValues = PaddingValues(),
    size: SDGBottomButtonSpec = SDGBottomButtonSpec.FULL,
    type: SDGBottomButtonType = SDGBottomButtonType.POSITIVE,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Box(Modifier.weight(weight)) {
        SDGBottomButton(
            label = title,
            marginValues = marginValues,
            size = size.toSize(),
            style = type.toStyle(),
            enabled = enabled,
            onClick = onClick,
        )
    }
}

@Preview
@Composable
@Suppress("DEPRECATION")
private fun PreviewBottomButton() {
    Surface {
        Column(
            verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing10)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing10)
            ) {
                SDGBottomButton(
                    title = "NORMAL",
                    type = SDGBottomButtonType.NORMAL,
                    weight = 1f
                ) {}
                SDGBottomButton(
                    title = "POSITIVE",
                    type = SDGBottomButtonType.POSITIVE,
                    weight = 1f
                ) {}
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing10)
            ) {
                SDGBottomButton(
                    title = "NEGATIVE",
                    type = SDGBottomButtonType.NEGATIVE,
                    weight = 1f
                ) {}
                SDGBottomButton(
                    title = "POSITIVE",
                    type = SDGBottomButtonType.POSITIVE,
                    weight = 1f
                ) {}
            }
        }
    }
}
