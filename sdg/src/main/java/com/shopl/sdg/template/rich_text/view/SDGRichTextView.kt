package com.shopl.sdg.template.rich_text.view

import android.view.ViewGroup
import androidx.annotation.RestrictTo
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.shopl.sdg.template.rich_text.common.RichTextWebView
import com.shopl.sdg.template.rich_text.view.preview.SDGRichTextViewPreviewParameter
import com.shopl.sdg.template.rich_text.view.preview.SDGRichTextViewPreviewParameterProvider
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

/**
 * Rich Text Input 안에 배치하여 사용하는, 서식이 적용된 텍스트를 보여주는 텍스트 영역 형태의 컴포넌트
 *
 * 본문 높이는 내용에 맞춰 늘어나며, Rich Text Input이 본문 영역의 높이를 제한합니다.
 *
 * @param text HTML 본문. 화면에서 새로운 본문을 전달하면 에디터에 반영합니다.
 * @param placeholder Default 상태에서 본문이 비어 있을 때 표시할 문구.
 * @param state 표시 상태. 실제 입력 포커스는 사용자가 본문을 눌러 이동합니다.
 * @param onEditorStateChange 초기화, 입력 또는 포커스 변경 후의 편집기 상태를 전달합니다.
 * @version 2.4.61
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=30814-2612&m=dev">Figma</a>
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
@Composable
fun SDGRichTextView(
    text: String,
    placeholder: String,
    state: SDGRichTextViewState,
    onEditorStateChange: (SDGRichTextEditorState) -> Unit,
) {
    val viewport = Modifier.fillMaxWidth().heightIn(min = SDGTypography.Body1R.style.lineHeight.value.dp)
    if (LocalInspectionMode.current) {
        SDGRichTextViewInspectionContent(text, placeholder, state, viewport)
        return
    }

    val currentOnEditorStateChange by rememberUpdatedState(onEditorStateChange)

    AndroidView(
        modifier = viewport,
        factory = { context ->
            RichTextWebView(context) { currentOnEditorStateChange(it) }.apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                )
            }
        },
        onRelease = RichTextWebView::release,
        update = { view -> view.render(text, placeholder, state) },
    )
}

/** Preview에서 HTML 예시와 표시 상태를 Compose 텍스트로 그립니다. */
@Composable
private fun SDGRichTextViewInspectionContent(
    text: String,
    placeholder: String,
    state: SDGRichTextViewState,
    modifier: Modifier,
) {
    val formattedText = remember(text, state) {
        AnnotatedString.fromHtml(
            htmlString = text,
            linkStyles = TextLinkStyles(
                style = SpanStyle(
                    color = if (state.isEditable) SDGColor.Primary300 else state.textColor,
                    textDecoration = TextDecoration.Underline,
                ),
            ),
        )
    }
    val showPlaceholder = state.showsPlaceholder && formattedText.isBlank()

    Row(
        modifier = modifier.clipToBounds(),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        SDGText(
            text = if (showPlaceholder) AnnotatedString(placeholder) else formattedText,
            typography = SDGTypography.Body1R,
            textColor = if (showPlaceholder) SDGColor.Neutral350 else state.textColor,
        )
        if (state == SDGRichTextViewState.Focused) {
            Box(Modifier.size(width = 2.dp, height = 20.dp).background(state.textColor))
        }
    }
}

/** Figma의 네 가지 표시 상태를 나란히 확인할 수 있는 Preview입니다. */
@Preview(showBackground = true, widthDp = 279)
@Composable
private fun PreviewSDGRichTextView(
    @PreviewParameter(SDGRichTextViewPreviewParameterProvider::class)
    parameter: SDGRichTextViewPreviewParameter,
) {
    SDGRichTextView(
        text = parameter.text,
        placeholder = parameter.placeholder,
        state = parameter.state,
        onEditorStateChange = {},
    )
}
