package com.shopl.sdg.template.rich_text.toolbar.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbarAction
import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbarBackgroundColor
import com.shopl.sdg.template.rich_text.toolbar.SDGRichTextToolbarButtonState

internal class SDGRichTextToolbarPreviewParameterProvider :
    PreviewParameterProvider<SDGRichTextToolbarPreviewParameter> {
    override val values = sequenceOf(기본(), 흰색_서식선택(), 투명_구분선없음(), 링크_미입력(), 링크_입력완료())

    private fun 기본() = SDGRichTextToolbarPreviewParameter()

    private fun 흰색_서식선택() = SDGRichTextToolbarPreviewParameter(
        backgroundColor = SDGRichTextToolbarBackgroundColor.White,
        buttons = SDGRichTextToolbarAction.defaultButtons.put(
            SDGRichTextToolbarAction.Bold,
            SDGRichTextToolbarButtonState.Selected,
        ),
    )

    private fun 투명_구분선없음() = SDGRichTextToolbarPreviewParameter(
        backgroundColor = SDGRichTextToolbarBackgroundColor.Transparent,
        showTopDivider = false,
    )

    private fun 링크_미입력() = SDGRichTextToolbarPreviewParameter(showLinkInput = true)

    private fun 링크_입력완료() = SDGRichTextToolbarPreviewParameter(
        showLinkInput = true,
        url = "https://www.shoplworks.com",
    )
}
