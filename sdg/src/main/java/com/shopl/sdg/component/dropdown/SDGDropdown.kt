package com.shopl.sdg.component.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

/**
 * SDG - Component - Dropdown
 *
 * 여러 개의 리스트 옵션 중 하나의 옵션을 선택하기 위한 컴포넌트
 *
 * @param hasSelectedItem 값 선택 여부
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=18223-7263&m=dev">Figma</a>
 */
@Composable
fun SDGBasicDropdown(
    text: String? = null,
    state: SDGDropdownState = SDGDropdownState.Default,
    hasSelectedItem: Boolean = false,
    enable: Boolean = false,
    backgroundColor: Color = SDGColor.Neutral0,
    width: Dp? = null,
    marginValues: PaddingValues = PaddingValues(),
    onClick: (() -> Unit)? = null,
) {

    Row(
        modifier = Modifier
            .padding(marginValues)
            .height(40.dp)
            .then(
                if (width != null) {
                    Modifier.width(width)
                } else {
                    Modifier
                }
            )
            .clip(shape = SDGCornerRadius.BoxRadius.Radius12)
            .then(
                when (state) {
                    SDGDropdownState.Default -> Modifier.background(
                        color = backgroundColor,
                        shape = SDGCornerRadius.BoxRadius.Radius12,
                    )

                    SDGDropdownState.Error -> Modifier.background(
                        color = SDGColor.Red300_a10,
                        shape = SDGCornerRadius.BoxRadius.Radius12,
                    )
                }
            )
            .then(
                if (enable) {
                    Modifier.clickable(
                        hasRipple = true,
                        rippleColor = SDGColor.Neutral350,
                        onClick = { onClick?.invoke() }
                    )
                } else {
                    Modifier
                }
            )
            .padding(
                horizontal = SDGSpacing.Spacing12,
                vertical = SDGSpacing.Spacing10
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing10)
    ) {
        SDGText(
            modifier = Modifier.weight(1f),
            text = text ?: "",
            textColor = when {
                !enable -> SDGColor.Neutral300
                hasSelectedItem -> SDGColor.Neutral700
                else -> SDGColor.Neutral300
            },
            typography = SDGTypography.Body1R,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        SDGImage(
            modifier = Modifier,
            resId = R.drawable.ic_common_dropdown,
            color = if (enable) SDGColor.Neutral700 else SDGColor.Neutral300,
        )
    }

}

@Composable
fun RowScope.SDGBasicDropdown(
    weight: Float,
    text: String? = null,
    hasSelectedItem: Boolean = false,
    enable: Boolean = false,
    backgroundColor: Color = SDGColor.Neutral0,
    marginValues: PaddingValues = PaddingValues(),
    onClick: (() -> Unit)? = null,
) {
    Box(Modifier.weight(weight)) {
        SDGBasicDropdown(
            text = text,
            hasSelectedItem = hasSelectedItem,
            enable = enable,
            backgroundColor = backgroundColor,
            marginValues = marginValues,
            onClick = onClick
        )
    }
}

@Preview
@Composable
private fun PreviewSDGBasicDropdown() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = SDGColor.Neutral0)
            .padding(SDGSpacing.Spacing20),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing20)
    ) {
        SDGBasicDropdown(
            text = "Item Selected",
            hasSelectedItem = true,
            enable = true,
            backgroundColor = SDGColor.Neutral50,
        )
        SDGBasicDropdown(
            text = "Hint",
            hasSelectedItem = false,
            enable = true,
            backgroundColor = SDGColor.Neutral50,
        )
        SDGBasicDropdown(
            text = "Disabled",
            hasSelectedItem = false,
            enable = false,
            backgroundColor = SDGColor.Neutral50,
        )
        Row {
            SDGBasicDropdown(
                weight = 1F,
                text = "Weight 1F",
                hasSelectedItem = true,
                enable = true,
                backgroundColor = SDGColor.Neutral50,
            )
            SDGBasicDropdown(
                weight = 1F,
                text = "Weight 1F",
                hasSelectedItem = false,
                enable = true,
                backgroundColor = SDGColor.Neutral50,
            )
        }
    }
}