package com.shopl.sdg.template.checkbox_label

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.shopl.sdg.component.checkbox.SDGCheckBox
import com.shopl.sdg.template.checkbox_label.preview.SDGCheckboxLabelPreviewParameterProvider
import com.shopl.sdg.template.checkbox_label.preview.SDGCheckboxLabelPreviewParams
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing2
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing8
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Template - Checkbox Label
 *
 * 여러 옵션 중 하나 이상을 자유롭게 복수 선택할 수 있도록 체크박스 버튼과 데이터 텍스트(Label)를 결합한 템플릿
 *
 * @version 2.3.41
 *
 * @param label 체크박스 옆에 표시되는 텍스트 라벨
 * @param state 체크박스 라벨 상태
 * @param selectType 선택된 체크박스와 라벨의 색상 타입
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=27349-43543&m=dev">Figma</a>
 */
@Composable
fun SDGCheckboxLabel(
    label: String,
    state: SDGCheckboxLabelState,
    selectType: SDGCheckboxLabelSelectType,
    onClick: () -> Unit,
    marginValues: PaddingValues = PaddingValues(),
) {
    Row(
        modifier = Modifier
            .padding(marginValues)
            .then(
                if (state.isEnabled) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                }
            ),
        horizontalArrangement = Arrangement.spacedBy(space = Spacing8),
        verticalAlignment = Alignment.Top,
    ) {
        SDGCheckBox(
            isChecked = state.isChecked,
            enabled = state.isEnabled,
            checkedBackgroundColor = selectType.checkedBackgroundColor,
            clickPadding = PaddingValues(vertical = Spacing2),
        )

        SDGText(
            text = label,
            textColor = state.labelColor(selectType),
            typography = SDGTypography.Body1R,
        )
    }
}

/**
 * 신규 Checkbox Label API와의 하위 호환성을 위한 레거시 API입니다.
 */
@Deprecated(
    message = "SDGCheckboxLabel의 state와 selectType 기반 API를 사용하세요.",
)
@Composable
@Suppress("DEPRECATION")
fun SDGCheckboxLabel(
    type: SDGCheckboxLabelType,
    label: String,
    isChecked: Boolean,
    enabled: Boolean = true,
    defaultLabelColor: Color = SDGColor.Neutral700,
    checkedLabelColor: Color? = null,
    checkedBackgroundColor: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .padding(marginValues)
            .then(
                if (onClick != null && enabled) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                }
            ),
        horizontalArrangement = Arrangement.spacedBy(space = Spacing8),
        verticalAlignment = Alignment.Top,
    ) {
        SDGCheckBox(
            isChecked = isChecked,
            enabled = enabled,
            checkedBackgroundColor = checkedBackgroundColor ?: SDGColor.Primary300,
            clickPadding = PaddingValues(vertical = Spacing2),
        )

        SDGText(
            text = label,
            textColor = if (enabled) checkedLabelColor ?: defaultLabelColor else SDGColor.Neutral300,
            typography = type.typography,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGCheckboxLabel(
    @PreviewParameter(SDGCheckboxLabelPreviewParameterProvider::class)
    params: SDGCheckboxLabelPreviewParams
) {
    SDGCheckboxLabel(
        label = params.label,
        state = params.state,
        selectType = params.selectType,
        onClick = {},
    )
}
