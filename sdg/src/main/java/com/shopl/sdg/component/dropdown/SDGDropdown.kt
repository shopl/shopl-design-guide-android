package com.shopl.sdg.component.dropdown

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.IOText
import com.shopl.sdg_resource.R

@Stable
sealed interface SDGBasicDropdownState {
    data object Default : SDGBasicDropdownState
    data object Error : SDGBasicDropdownState
}

@Composable
fun SDGBasicDropdown(
    text: String? = null,
    state: SDGBasicDropdownState = SDGBasicDropdownState.Default,
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
            .clip(shape = RoundedCornerShape(12.dp))
            .then(
                when (state) {
                    SDGBasicDropdownState.Default -> Modifier.background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(12.dp),
                    )

                    SDGBasicDropdownState.Error -> Modifier.background(
                        color = SDGColor.Red300_a10,
                        shape = RoundedCornerShape(12.dp),
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
                start = 12.dp,
                end = 10.dp,
                top = 10.dp,
                bottom = 10.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        IOText(
            text = text ?: "",
            modifier = Modifier
                .weight(1f),
            textColor = if (hasSelectedItem) SDGColor.Neutral700 else SDGColor.Neutral300,
            fontSize = 16.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Image(
            modifier = Modifier,
            painter = painterResource(
                id = R.drawable.ic_common_dropdown
            ),
            contentDescription = "",
            colorFilter = ColorFilter.tint(
                color = if (!enable) SDGColor.Neutral300 else SDGColor.Neutral700
            )
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
fun SDGDropdownPreview() {
    Surface {
        Box(
            modifier = Modifier
                .background(color = SDGColor.Neutral100)
        ) {

            var test by remember { mutableStateOf("힌트입니다. 누르면 값이 변경되면서 색상도 변경됩니다.") }
            var isSelected by remember {
                mutableStateOf(false)
            }
            Column {
                SDGBasicDropdown(
                    text = test,
                    hasSelectedItem = isSelected,
                    enable = true,
                    backgroundColor = SDGColor.Neutral0,
                    marginValues = PaddingValues(
                        horizontal = 20.dp,
                        vertical = 20.dp,
                    ),
                    onClick = {
                        test = "TEST"
                        isSelected = true
                    },
                )

                Row {
                    SDGBasicDropdown(
                        weight = 1F,
                        text = test,
                        hasSelectedItem = isSelected,
                        enable = true,
                        backgroundColor = SDGColor.Neutral0,
                        marginValues = PaddingValues(
                            horizontal = 20.dp,
                            vertical = 20.dp,
                        ),
                        onClick = {
                            test = "TEST"
                            isSelected = true
                        },
                    )

                    SDGBasicDropdown(
                        width = 112.dp,
                        text = test,
                        hasSelectedItem = isSelected,
                        enable = true,
                        backgroundColor = SDGColor.Neutral0,
                        marginValues = PaddingValues(
                            horizontal = 20.dp,
                            vertical = 20.dp,
                        ),
                        onClick = {
                            test = "TEST"
                            isSelected = true
                        },
                    )
                }
            }
        }
    }
}