package com.shopl.sdg.template.rich_text.view

import android.view.ViewGroup
import androidx.annotation.RestrictTo
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
 * 서식 있는 본문을 읽기 전용으로 표시합니다. 본문 높이는 내용에 맞춰 늘어나며, 부모가 높이를 제한하면 내부에서 스크롤합니다.
 * 본문의 처음이나 끝에서 같은 방향으로 더 드래그하면 상위 스크롤 영역이 이어서 움직입니다.
 *
 * 본문의 HTTP 또는 HTTPS 링크를 누르면 외부 브라우저에서 엽니다.
 *
 * @param text 표시할 HTML 본문.
 * @version 2.4.61
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=30814-2612&m=dev">Figma</a>
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
@Composable
fun SDGRichTextView(text: String) {
    val viewport = Modifier.fillMaxWidth().heightIn(min = SDGTypography.Body1R.style.lineHeight.value.dp)
    if (LocalInspectionMode.current) {
        SDGRichTextViewInspectionContent(text, viewport)
        return
    }

    AndroidView(
        modifier = viewport,
        factory = { context ->
            RichTextWebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                )
            }
        },
        onRelease = RichTextWebView::release,
        update = { view -> view.render(text) },
    )
}

/** Preview에서 HTML 본문을 Compose 텍스트로 표시합니다. */
@Composable
private fun SDGRichTextViewInspectionContent(
    text: String,
    modifier: Modifier,
) {
    val formattedText = remember(text) {
        AnnotatedString.fromHtml(
            htmlString = text,
            linkStyles = TextLinkStyles(
                style = SpanStyle(
                    color = SDGColor.Primary300,
                    textDecoration = TextDecoration.Underline,
                ),
            ),
        )
    }

    SDGText(
        modifier = modifier.clipToBounds(),
        text = formattedText,
        typography = SDGTypography.Body1R,
        textColor = SDGColor.Neutral700,
    )
}

/** 일반 본문과 서식 있는 본문의 표시 예시입니다. */
@Preview(showBackground = true, widthDp = 279)
@Composable
private fun PreviewSDGRichTextView(
    @PreviewParameter(SDGRichTextViewPreviewParameterProvider::class)
    parameter: SDGRichTextViewPreviewParameter,
) {
    SDGRichTextView(text = parameter.text)
}
