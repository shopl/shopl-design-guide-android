package com.shopl.sdg.component.search_bar.capsule

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.enums.Keyboard
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing10
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing12
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_common.util.keyboardAsState
import com.shopl.sdg_resource.R

/**
 * SDG - Search Bar - Capsule Search
 *
 * 일반적인 캡슐 형태의 검색 컴포넌트
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=7069-15110&m=dev">Figma</a>
 */
@Composable
fun SDGCapsuleSearch(
    type: SDGCapsuleSearchType,
    input: String,
    hint: String,
    enabled: Boolean,
    onInputChange: (String) -> Unit,
    onDeleteClick: () -> Unit,
    marginValues: PaddingValues = PaddingValues(),
    useStartRequester: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()
    var skipFirstEvent by remember { mutableStateOf(useStartRequester) }

    LaunchedEffect(key1 = Unit) {
        if (useStartRequester) {
            focusRequester.requestFocus()
        }
    }

    LaunchedEffect(key1 = isKeyboardOpen) {
        if (isKeyboardOpen == Keyboard.Closed && !skipFirstEvent) {
            focusManager.clearFocus()
        }
        skipFirstEvent = false
    }

    BasicTextField(
        modifier = Modifier,
        value = input,
        onValueChange = onInputChange,
        enabled = enabled,
        textStyle = SDGTypography.Body2R.style,
        singleLine = true,
        maxLines = 1,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        cursorBrush = SolidColor(SDGColor.Neutral700),
        decorationBox = { innerTextField ->
            Row(
                Modifier
                    .padding(marginValues)
                    .fillMaxWidth()
                    .height(40.dp)
                    .clip(RoundedCornerShape(SDGCornerRadius.Radius20))
                    .background(
                        color = if (enabled) SDGColor.Neutral0 else SDGColor.Neutral50,
                    )
                    .border(
                        width = 1.dp,
                        brush = if (type == SDGCapsuleSearchType.Line) {
                            SolidColor(SDGColor.Neutral200)
                        } else {
                            SolidColor(SDGColor.Transparent)
                        },
                        shape = RoundedCornerShape(SDGCornerRadius.Radius20)
                    )
                    .padding(vertical = Spacing10, horizontal = Spacing12),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SDGImage(
                    modifier = Modifier.size(14.dp),
                    resId = R.drawable.ic_common_search,
                    color = SDGColor.Neutral300,
                )

                Spacer(modifier = Modifier.width(4.dp))

                Box(Modifier.weight(1f)) {
                    if (input.isEmpty()) {
                        SDGText(
                            text = hint,
                            typography = SDGTypography.Body2R,
                            textColor = SDGColor.Neutral300,
                        )
                    }
                    innerTextField()
                }

                Spacer(modifier = Modifier.width(12.dp))

                if (input.isNotEmpty()) {
                    SDGImage(
                        modifier = Modifier
                            .size(18.dp)
                            .clickable(false) { onDeleteClick() },
                        resId = R.drawable.ic_input_delete,
                        color = null
                    )
                }
            }
        },
    )
}

@Preview
@Composable
private fun PreviewSDGCapsuleSearch(
    @PreviewParameter(SDGCapsulePreviewParameterProvider::class) parameter: SDGCapsulePreviewParameter
) {
    var text by remember { mutableStateOf(parameter.input) }

        SDGCapsuleSearch(
            type = parameter.type,
            input = text,
            hint = "Search",
            enabled = parameter.enabled,
            onInputChange = { text = it },
            onDeleteClick = { text = "" }
        )
}

