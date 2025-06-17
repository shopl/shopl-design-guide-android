package com.shopl.sdg.template.form

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.text_input.InputState
import com.shopl.sdg.component.text_input.SDGSimpleInput
import com.shopl.sdg.component.text_input.SDGSimpleInputType
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.ext.withColor
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

/**
 * SDG - Template - Form - Simple Input
 *
 * 타이틀과 Simple input으로 구성된 템플릿
 *
 * @param inputState SimpeInput 에서 사용되는 state, Error인 경우 현재 message 별도 출력하지 않음
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=9965-10563&m=dev">Figma</a>
 */
@Composable
fun SDGSimpleInputForm(
    type: SDGFormType,
    title: String,
    value: String?,
    onValueChange: (String) -> Unit,
    essential:Boolean = false,
    hint: String? = null,
    @DrawableRes iconResId: Int? = null,
    iconTint: Color? = null,
    onClickIcon: (() -> Unit)? = null,
    marginValues: PaddingValues = PaddingValues(),
    inputState: InputState = InputState.Enable,
) {
    val titleAnnotatedString = if(essential) {
        title.plus("*").withColor(SDGColor.Red300, "*")
    } else {
        AnnotatedString(title)
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(marginValues)
                .fillMaxWidth()
                .heightIn(min = 28.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                when(type) {
                    SDGFormType.EMPHA -> {
                        SDGText(
                            text = titleAnnotatedString,
                            textColor = SDGColor.Neutral700,
                            typography = SDGTypography.Body1SB
                        )
                    }
                    SDGFormType.NORMAL -> {
                        SDGText(
                            text = titleAnnotatedString,
                            textColor = SDGColor.Neutral700,
                            typography = SDGTypography.Body1R
                        )
                    }
                }
                iconResId?.let {
                    Box(
                        modifier = Modifier
                            .clickable(hasRipple = false) {
                                onClickIcon?.invoke()
                            }
                            .width(26.dp)
                            .height(20.dp)
                            .padding(
                                start = 4.dp,
                                top = 3.dp,
                                end = 8.dp,
                                bottom = 3.dp
                            )
                    ) {
                        SDGImage(
                            resId = it,
                            color = iconTint,
                        )
                    }
                }
            }
        }
        SDGSimpleInput(
            type = SDGSimpleInputType.BASIC,
            input = value.orEmpty(),
            hint = hint ?: stringResource(id = R.string.text_hint_study_place),
            state = inputState,
            backgroundColor = SDGColor.Neutral50,
            onInputChange = onValueChange,
        )
    }
}

@Preview
@Composable
fun PreviewSDGSimpleInputForm() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = SDGColor.Neutral0
            )
            .padding(
                vertical = 20.dp
            )
        ,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SDGSimpleInputForm(
            type = SDGFormType.EMPHA,
            title = "SDGSimpleInputForm",
            value = "This is EMPHA type",
            essential = true,
            iconResId = R.drawable.ic_clip,
            iconTint = SDGColor.Neutral700,
            onValueChange = {}
        )
        SDGSimpleInputForm(
            type = SDGFormType.EMPHA,
            title = "SDGSimpleInputForm",
            value = "This is EMPHA type",
            essential = true,
            iconResId = R.drawable.ic_clip,
            iconTint = SDGColor.Neutral700,
            inputState = InputState.Error(""),
            onValueChange = {}
        )
        SDGSimpleInputForm(
            type = SDGFormType.NORMAL,
            title = "SDGSimpleInputForm",
            value = "",
            hint = "This is NORMAL type",
            onValueChange = {}
        )
    }
}