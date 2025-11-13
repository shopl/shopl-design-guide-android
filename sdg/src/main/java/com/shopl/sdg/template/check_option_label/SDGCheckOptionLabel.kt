package com.shopl.sdg.template.check_option_label

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.check_option.SDGCheck
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing6
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing8
import com.shopl.sdg_common.ui.components.SDGText

/**
 * SDG - Check Option Label
 *
 * 하나의 옵션을 선택 또는 확인하는 Check Option과 Label이 조합된 템플릿
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19392-9158&m=dev">Figma</a>
 */
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
    Row(
        modifier = Modifier.padding(marginValues),
        horizontalArrangement = Arrangement.spacedBy(
            when (size) {
                SDGCheckOptionLabelSize.SMALL -> Spacing6
                SDGCheckOptionLabelSize.MEDIUM -> Spacing8
            }
        )
    ) {
        SDGCheck(
            isChecked = isChecked,
            onClick = {
                if (enabled) {
                    onClick?.invoke()
                }
            },
            clickPadding = PaddingValues(vertical = SDGSpacing.Spacing2),
        )
        SDGText(
            modifier = Modifier
                .then(
                    if (enabled) {
                        Modifier.clickable(hasRipple = false) {
                            onClick?.invoke()
                        }
                    } else Modifier
                ),
            text = label,
            textColor = if (enabled) {
                if (isChecked) {
                    checkTextColor
                } else {
                    defaultTextColor
                }
            } else {
                SDGColor.Neutral300
            },
            typography = size.typography
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SDGCheckOptionLabelPreview() {
    SDGCheckOptionLabel(
        size = SDGCheckOptionLabelSize.SMALL,
        label = "Label",
        isChecked = false
    )
}

@Preview(showBackground = true)
@Composable
fun SDGCheckOptionLabelCheckedPreview() {
    SDGCheckOptionLabel(
        size = SDGCheckOptionLabelSize.MEDIUM,
        label = "Label",
        isChecked = true
    )
}


