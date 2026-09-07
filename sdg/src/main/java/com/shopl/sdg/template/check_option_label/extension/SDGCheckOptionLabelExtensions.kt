package com.shopl.sdg.template.check_option_label.extension

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import com.shopl.sdg.component.check_option.model.SDGCheckOptionSelectedBackgroundColor
import com.shopl.sdg.template.check_option_label.SDGCheckOptionLabelContent
import com.shopl.sdg.template.check_option_label.SDGCheckOptionLabelSize
import com.shopl.sdg.template.check_option_label.SDGCheckOptionLabelState
import com.shopl.sdg_common.foundation.SDGColor

/**
 * 신규 Check Option Label API와의 하위 호환성을 위한 AnnotatedString 레거시 API입니다.
 */
@Deprecated(
    message = "String label과 state/selectType 기반 SDGCheckOptionLabel API를 사용하세요.",
)
@Composable
fun SDGCheckOptionLabel(
    size: SDGCheckOptionLabelSize,
    label: AnnotatedString,
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
        label = label,
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
