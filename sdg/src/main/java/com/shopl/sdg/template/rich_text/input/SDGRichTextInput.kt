package com.shopl.sdg.template.rich_text.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.shopl.sdg.template.rich_text.input.preview.SDGRichTextInputPreviewParameter
import com.shopl.sdg.template.rich_text.input.preview.SDGRichTextInputPreviewParameterProvider
import com.shopl.sdg.template.rich_text.style.RICH_TEXT_INPUT_HEIGHT
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

/**
 * Rich Text 입력 화면에 배치하여 사용하는, 서식을 적용해 텍스트를 입력하는 입력창 형태의 컴포넌트
 *
 * 본문을 편집하면 키보드 위에 서식 툴바가 표시됩니다. 링크 입력을 확인하거나 취소하면 본문의 선택 영역으로 돌아갑니다.
 *
 * @param state HTML 본문과 글자 수를 보관하는 상태.
 * @param maxCount 화면에서 허용하는 글자 수. 초과하면 오류를 표시하며 계속 입력할 수 있습니다.
 * @param placeholder 비어 있고 포커스가 없을 때 표시할 안내 문구.
 * @param enabled 본문 편집 가능 여부.
 * @param isError 글자 수와 별개로 화면에서 지정하는 오류 여부.
 * @param marginValues 입력창 바깥 여백.
 * @version 2.4.61
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=30814-2246&m=dev">Figma</a>
 */
@Composable
fun SDGRichTextInput(
    state: SDGRichTextInputState,
    maxCount: Int,
    placeholder: String = stringResource(R.string.rich_text_input_placeholder),
    enabled: Boolean = true,
    isError: Boolean = false,
    marginValues: PaddingValues = PaddingValues(),
) {
    val preview = LocalInspectionMode.current
    val previewText = if (preview) remember(state.html) { AnnotatedString.fromHtml(state.html) } else null
    val count = previewText?.let { it.text.trimEnd('\n').let { text -> text.codePointCount(0, text.length) } }
        ?: state.characterCount
    val error = enabled && (isError || count > maxCount)
    val countColor = when {
        !enabled -> SDGColor.Neutral300
        error -> SDGColor.Red300
        else -> SDGColor.Neutral500
    }

    Column(
        modifier = Modifier.padding(marginValues).fillMaxWidth().clip(SDGCornerRadius.BoxRadius.Radius12)
            .background(if (error) SDGColor.Red300_a10 else SDGColor.Neutral50).padding(SDGSpacing.Spacing12),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing4),
    ) {
        Box(Modifier.fillMaxWidth().height(RICH_TEXT_INPUT_HEIGHT.dp).clipToBounds()) {
            if (previewText != null) {
                SDGRichTextInputInspectionContent(previewText, placeholder, enabled, state.isFocused)
            } else key(state) {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = ::RichTextInputWebView,
                    onRelease = { view ->
                        state.detach(view)
                        view.release()
                    },
                    update = { it.bind(state, enabled, placeholder) },
                )
            }
        }
        Box(
            modifier = Modifier.align(Alignment.End).heightIn(min = SDGTypography.Body3R.style.lineHeight.value.dp),
            contentAlignment = Alignment.CenterEnd,
        ) {
            SDGText(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = countColor)) { append(count.toString()) }
                    append("/$maxCount")
                },
                typography = SDGTypography.Body3R,
                textColor = if (enabled) SDGColor.Neutral350 else SDGColor.Neutral300,
            )
        }
    }
    if (enabled && !preview) SDGRichTextInputToolbar(state)
}

/** Preview에서 HTML 본문과 포커스 상태를 표시합니다. */
@Composable
private fun SDGRichTextInputInspectionContent(
    text: AnnotatedString,
    placeholder: String,
    enabled: Boolean,
    focused: Boolean,
) {
    val showHint = text.isEmpty() && enabled && !focused
    Row(horizontalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing2)) {
        SDGText(
            text = if (showHint) AnnotatedString(placeholder) else text,
            typography = SDGTypography.Body1R,
            textColor = when {
                !enabled -> SDGColor.Neutral300
                showHint -> SDGColor.Neutral350
                else -> SDGColor.Neutral700
            },
        )
        if (focused) Box(Modifier.size(2.dp, 20.dp).background(SDGColor.Neutral700))
    }
}

@Preview(showBackground = true, widthDp = 303)
@Composable
private fun PreviewSDGRichTextInput(
    @PreviewParameter(SDGRichTextInputPreviewParameterProvider::class) parameter: SDGRichTextInputPreviewParameter,
) {
    val state = remember(parameter) {
        SDGRichTextInputState(parameter.html).apply { isFocused = parameter.focused }
    }
    SDGRichTextInput(state = state, maxCount = parameter.maxCount, enabled = parameter.enabled)
}
