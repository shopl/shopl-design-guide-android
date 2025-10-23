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
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.checkbox.SDGCheckBox
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
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
    defaultTextColor: Color = SDGColor.Neutral700,
    checkTextColor: Color = SDGColor.Primary300,
    marginValues: PaddingValues = PaddingValues(),
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier.padding(marginValues),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = Spacing8),
    ) {
        SDGCheckBox(
            isChecked = if (enabled) isChecked else false,
            onClick = { if (enabled) onClick?.invoke() },
        )

        SDGText(
            modifier = Modifier.then(other = if (enabled) Modifier.clickable { onClick?.invoke() } else Modifier),
            text = label,
            textColor = if (enabled) {
                if (isChecked) checkTextColor else defaultTextColor
            } else {
                SDGColor.Neutral300
            },
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
        checkTextColor = params.checkTextColor
    )
}

private class SDGCheckboxLabelPreviewParameterProvider :
    PreviewParameterProvider<SDGCheckboxLabelPreviewParams> {

    override val values = sequenceOf(
        // Default - Normal
        SDGCheckboxLabelPreviewParams(
            type = SDGCheckboxLabelType.NORMAL,
            label = "Label",
            isChecked = false,
        ),
        // Default - Empha
        SDGCheckboxLabelPreviewParams(
            type = SDGCheckboxLabelType.EMPHA,
            label = "Label",
            isChecked = false,
        ),

        // Selected - Normal - Neutral700
        SDGCheckboxLabelPreviewParams(
            type = SDGCheckboxLabelType.NORMAL,
            label = "Label",
            isChecked = true,
        ),
        // Selected - Empha - Neutral700
        SDGCheckboxLabelPreviewParams(
            type = SDGCheckboxLabelType.EMPHA,
            label = "Label",
            isChecked = true,
        ),
        // Selected - Normal - Primary300
        SDGCheckboxLabelPreviewParams(
            type = SDGCheckboxLabelType.NORMAL,
            label = "Label",
            checkTextColor = SDGColor.Primary300,
            isChecked = true,
        ),
        // Selected - Empha - Neutral700
        SDGCheckboxLabelPreviewParams(
            type = SDGCheckboxLabelType.EMPHA,
            label = "Label",
            checkTextColor = SDGColor.Primary300,
            isChecked = true,
        ),

        // Disabled - Normal
        SDGCheckboxLabelPreviewParams(
            type = SDGCheckboxLabelType.NORMAL,
            label = "Label",
            isChecked = false,
            enabled = false
        ),
        // Disabled - Empha
        SDGCheckboxLabelPreviewParams(
            type = SDGCheckboxLabelType.EMPHA,
            label = "Label",
            isChecked = true,
            enabled = false
        ),
    )
}

private data class SDGCheckboxLabelPreviewParams(
    val type: SDGCheckboxLabelType,
    val label: String,
    val isChecked: Boolean,
    val enabled: Boolean = true,
    val checkTextColor: Color = SDGColor.Neutral700,
)
