package com.shopl.sdg.template.selected_input

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.IOText
import com.shopl.sdg_resource.R

@Stable
sealed interface SDGSelectedInputState {
    data object Default : SDGSelectedInputState
    data object Error : SDGSelectedInputState
}

@Composable
fun SDGSelectedInputSingle(
    text: String? = null,
    state: SDGSelectedInputState = SDGSelectedInputState.Default,
    hasSelectedItem: Boolean = false,
    marginValues: PaddingValues = PaddingValues(0.dp),
    icon: @Composable (() -> Unit)? = null,
    enable: Boolean = true,
    backgroundColor: Color = SDGColor.Neutral0,
    onClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .padding(marginValues)
            .height(40.dp)
            .clip(shape = RoundedCornerShape(12.dp))
            .then(
                when (state) {
                    SDGSelectedInputState.Default -> Modifier.background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(12.dp),
                    )

                    SDGSelectedInputState.Error -> Modifier.background(
                        color = SDGColor.Red300_a10,
                        shape = RoundedCornerShape(12.dp),
                    )
                }
            )
            .clickable(
                hasRipple = true,
                rippleColor = SDGColor.Neutral350,
                onClick = {
                    if (enable) {
                        onClick?.invoke()
                    }
                }
            )
            .padding(horizontal = 12.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        icon?.invoke()
        IOText(
            text = text ?: "",
            modifier = Modifier
                .padding(
                    start = if (icon != null) 10.dp else 0.dp,
                )
                .weight(1f),
            textColor = if (hasSelectedItem) SDGColor.Neutral700 else SDGColor.Neutral300,
            fontSize = 16.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Spacer(modifier = Modifier.width(10.dp))
        Image(
            modifier = Modifier,
            painter = painterResource(
                id = R.drawable.ic_common_next
            ),
            contentDescription = "",
            colorFilter = ColorFilter.tint(
                color = if (!enable) SDGColor.Neutral300 else SDGColor.Neutral700
            )
        )
    }

}

@Composable
fun SDGSelectedInputTime(
    startTime: String?,
    endTime: String?,
    marginValues: PaddingValues = PaddingValues(),
    isError: Boolean = false,
    backgroundColor: Color = SDGColor.Neutral50,
    onClick: ((isStart: Boolean) -> Unit),
) {
    Row(
        modifier = Modifier
            .padding(marginValues)
            .fillMaxWidth()
            .background(
                color = if (isError) SDGColor.Red300_a10 else backgroundColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 10.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IOText(
            modifier = Modifier
                .weight(1f)
                .clickable(false) { onClick(true) },
            text = startTime ?: stringResource(id = R.string.dialog_date_picker_start),
            textColor = if (startTime != null) SDGColor.Neutral700 else SDGColor.Neutral300,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        IOText(
            text = "~",
            textColor = SDGColor.Neutral700,
            fontSize = 16.sp,

            )

        IOText(
            modifier = Modifier
                .weight(1f)
                .clickable(false) { onClick(false) },
            text = endTime ?: stringResource(id = R.string.dialog_date_picker_end),
            textColor = if (endTime != null) SDGColor.Neutral700 else SDGColor.Neutral300,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}


@Preview
@Composable
private fun SDGSelectedInputPreview() {
    Surface {
        Box(
            modifier = Modifier
                .background(color = SDGColor.Neutral0)
        ) {

            var test by remember { mutableStateOf("힌트입니다. 누르면 값이 변경되면서 색상도 변경됩니다.") }
            var isSelected by remember {
                mutableStateOf(false)
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SDGSelectedInputSingle(
                    text = test,
                    enable = true,
                    backgroundColor = SDGColor.Neutral100,
                    marginValues = PaddingValues(
                        horizontal = 20.dp,
                        vertical = 20.dp,
                    ),
                    onClick = {
                        test = "TEST"
                        isSelected = true
                    },
                )

                SDGSelectedInputTime(startTime = null, endTime = null, marginValues = PaddingValues(20.dp)) {

                }
            }
        }
    }
}