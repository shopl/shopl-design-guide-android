package com.shopl.sdg.component.select_input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
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
 * SDG - Component - Select Input
 *
 * 특정 타켓을 선택하는 인풋 컴포넌트
 *
 * @param icon Text 왼쪽 아이콘이 필요한 경우 사용되는 Composable
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=18192-9125&m=dev">Figma</a>
 */
@Composable
fun SDGSelectInput(
    text: String? = null,
    placeholder: String = stringResource(id = R.string.select),
    state: SDGSelectInputState = SDGSelectInputState.Default,
    marginValues: PaddingValues = PaddingValues(0.dp),
    icon: @Composable (() -> Unit)? = null,
    backgroundColor: Color = SDGColor.Neutral0,
    onClick: (() -> Unit)? = null,
    overflow: TextOverflow = TextOverflow.Ellipsis,
) {
    Row(
        modifier = Modifier
            .padding(marginValues)
            .height(40.dp)
            .clip(shape = SDGCornerRadius.BoxRadius.Radius12)
            .then(
                when (state) {
                    SDGSelectInputState.Error -> Modifier.background(
                        color = SDGColor.Red300_a10,
                        shape = SDGCornerRadius.BoxRadius.Radius12,
                    )
                    else -> Modifier.background(
                        color = backgroundColor,
                        shape = SDGCornerRadius.BoxRadius.Radius12,
                    )
                }
            )
            .clickable(
                hasRipple = true,
                rippleColor = SDGColor.Neutral350,
                onClick = {
                    if (state != SDGSelectInputState.Disabled) {
                        onClick?.invoke()
                    }
                }
            )
            .padding(
                horizontal = SDGSpacing.Spacing12,
                vertical = SDGSpacing.Spacing4
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing10)
    ) {
        icon?.invoke()
        SDGText(
            modifier = Modifier.weight(1f),
            text = if(!text.isNullOrEmpty()) text else placeholder,
            textColor = when(state) {
                SDGSelectInputState.Disabled -> SDGColor.Neutral300
                else -> {
                    if(!text.isNullOrEmpty()) {
                        SDGColor.Neutral700
                    } else {
                        SDGColor.Neutral300
                    }
                }
            },
            typography = SDGTypography.Body1R,
            overflow = overflow,
            maxLines = 1
        )
        SDGImage(
            modifier = Modifier,
            resId = R.drawable.ic_common_next,
            color = when(state) {
                SDGSelectInputState.Default -> SDGColor.Neutral700
                SDGSelectInputState.Disabled -> SDGColor.Neutral300
                SDGSelectInputState.Error -> SDGColor.Neutral700
            },
        )
    }

}

@Preview
@Composable
private fun PreviewSDGSelectInput() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = SDGColor.Neutral0)
            .padding(SDGSpacing.Spacing20),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing20)
    ) {
        SDGSelectInput(
            text = "Item Selected Item Selected Item Selected",
            state = SDGSelectInputState.Default,
            backgroundColor = SDGColor.Neutral50,
        )
        SDGSelectInput(
            text = "Item Selected Item Selected Item Selected",
            state = SDGSelectInputState.Default,
            backgroundColor = SDGColor.Neutral50,
            overflow = TextOverflow.MiddleEllipsis
        )
        SDGSelectInput(
            text = "Item Selected With Icon",
            state = SDGSelectInputState.Default,
            backgroundColor = SDGColor.Neutral50,
            icon = {
                Spacer(
                    modifier = Modifier
                        .size(30.dp)
                        .background(
                        color = SDGColor.Neutral700,
                        shape = SDGCornerRadius.BoxRadius.Radius12
                    )
                )
            }
        )
        SDGSelectInput(
            text = "Default",
            state = SDGSelectInputState.Default,
            backgroundColor = SDGColor.Neutral50,
        )
        SDGSelectInput(
            text = "Disabled",
            state = SDGSelectInputState.Disabled,
            backgroundColor = SDGColor.Neutral50,
        )
        SDGSelectInput(
            text = "Error",
            state = SDGSelectInputState.Error,
            backgroundColor = SDGColor.Neutral50,
        )
    }
}