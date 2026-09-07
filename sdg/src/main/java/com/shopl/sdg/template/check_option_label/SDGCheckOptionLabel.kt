package com.shopl.sdg.template.check_option_label

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.shopl.sdg.component.check_option.SDGCheckOption
import com.shopl.sdg.component.check_option.model.SDGCheckOptionSelectedBackgroundColor
import com.shopl.sdg.component.check_option.model.SDGCheckOptionSize
import com.shopl.sdg.component.check_option.model.SDGCheckOptionStyle
import com.shopl.sdg.template.check_option_label.preview.SDGCheckOptionLabelPreviewParameterProvider
import com.shopl.sdg.template.check_option_label.preview.SDGCheckOptionLabelPreviewParams
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Template - Check Option Label
 *
 * 하나의 옵션을 선택 또는 확인하는 Check Option과 Label이 조합된 템플릿
 *
 * @version 2.3.42
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=27684-6923&m=dev">Figma</a>
 */
@Composable
fun SDGCheckOptionLabel(
    label: String,
    state: SDGCheckOptionLabelState,
    selectedType: SDGCheckOptionLabelSelectedType,
    size: SDGCheckOptionLabelSize,
    onClick: () -> Unit,
    marginValues: PaddingValues = PaddingValues(),
) {
    SDGCheckOptionLabelContent(
        label = AnnotatedString(label),
        state = state,
        selectedBackgroundColor = selectedType.selectedBackgroundColor,
        size = size,
        labelColor = state.labelColor(selectedType),
        marginValues = marginValues,
        onClick = onClick,
    )
}


@Composable
internal fun SDGCheckOptionLabelContent(
    label: AnnotatedString,
    state: SDGCheckOptionLabelState,
    selectedBackgroundColor: SDGCheckOptionSelectedBackgroundColor,
    size: SDGCheckOptionLabelSize,
    labelColor: Color,
    marginValues: PaddingValues,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(marginValues)
            .then(
                if (state.isEnabled) {
                    Modifier.clickable(onClick = onClick)
                } else {
                    Modifier
                },
            ),
        horizontalArrangement = Arrangement.spacedBy(space = size.gap),
        verticalAlignment = Alignment.Top,
    ) {
        Box(
            modifier = Modifier.padding(vertical = size.checkOptionVerticalPadding),
        ) {
            SDGCheckOption(
                state = state.checkOptionState,
                selectedBackgroundColor = selectedBackgroundColor,
                size = SDGCheckOptionSize.MEDIUM,
                style = SDGCheckOptionStyle.SOLID,
                onClick = null,
            )
        }

        SDGText(
            text = label,
            textColor = labelColor,
            typography = size.typography,
        )
    }
}


/**
 * 신규 Check Option Label API와의 하위 호환성을 위한 레거시 API입니다.
 */
@Deprecated(
    message = "SDGCheckOptionLabel의 state와 selectType 기반 API를 사용하세요.",
)
@Composable
fun SDGCheckOptionLabel(
    size: SDGCheckOptionLabelSize,
    label: String,
    isChecked: Boolean,
    enabled: Boolean = true,
    defaultTextColor: Color = SDGColor.Neutral700,
    checkTextColor: Color = SDGColor.Primary300,
    marginValues: PaddingValues = PaddingValues(),
    onClick: (() -> Unit)? = null,
) {
    val state = SDGCheckOptionLabelState.fromLegacy(
        isChecked = isChecked,
        enabled = enabled,
    )

    SDGCheckOptionLabelContent(
        label = AnnotatedString(label),
        state = state,
        selectedBackgroundColor = SDGCheckOptionSelectedBackgroundColor.NORMAL,
        size = size,
        labelColor = state.legacyLabelColor(
            defaultTextColor = defaultTextColor,
            checkTextColor = checkTextColor,
        ),
        marginValues = marginValues,
        onClick = { onClick?.invoke() },
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGCheckOptionLabel(
    @PreviewParameter(SDGCheckOptionLabelPreviewParameterProvider::class)
    params: SDGCheckOptionLabelPreviewParams,
) {
    SDGCheckOptionLabel(
        label = params.label,
        state = params.state,
        selectedType = params.selectType,
        size = params.size,
        onClick = {},
    )
}
