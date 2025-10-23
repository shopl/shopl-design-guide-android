package com.shopl.sdg.template.checkbox_label

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.shopl.sdg.component.checkbox.SDGCheckBox
import com.shopl.sdg.template.checkbox_label.preview.SDGCheckboxLabelPreviewParameterProvider
import com.shopl.sdg.template.checkbox_label.preview.SDGCheckboxLabelPreviewParams
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing8
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Checkbox Label
 *
 * 여러개의 옵션 중 다중 선택을 위한 Checkbox와 Label이 조합된 템플릿
 *
 * @param type [SDGCheckboxLabelType] 체크박스 라벨 타입
 * @param isChecked [Boolean] 체크박스 체크 여부
 * @param label [String] 체크박스 라벨
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19174-10597&p=f&m=dev">Figma</a>
 */

@Composable
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
        modifier = Modifier.padding(paddingValues = marginValues),
        horizontalArrangement = Arrangement.spacedBy(space = Spacing8),
    ) {
        SDGCheckBox(
            isChecked = isChecked,
            enabled = enabled,
            checkedBackgroundColor = checkedBackgroundColor ?: SDGColor.Primary300,
            onClick = { if (enabled) onClick?.invoke() },
            clickPadding = PaddingValues(vertical = SDGSpacing.Spacing2),
        )

        SDGText(
            modifier = Modifier.then(other = if (enabled) Modifier.clickable { onClick?.invoke() } else Modifier),
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
        type = params.type,
        label = params.label,
        isChecked = params.isChecked,
        enabled = params.enabled,
        checkedLabelColor = params.checkTextColor
    )
}
