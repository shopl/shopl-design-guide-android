package com.shopl.sdg.template.rich_text.toolbar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.shopl.sdg_resource.R
import kotlinx.collections.immutable.toPersistentMap

/** 툴바에서 편집기로 전달하는 동작입니다. 선언 순서는 디자인 가이드의 버튼 순서입니다. */
enum class SDGRichTextToolbarAction(
    internal val group: ToolbarGroup,
    @DrawableRes internal val icon: Int,
    @StringRes internal val description: Int,
) {
    Undo(ToolbarGroup.History, R.drawable.ic_text_undo, R.string.rich_text_undo),
    Redo(ToolbarGroup.History, R.drawable.ic_text_redo, R.string.rich_text_redo),
    Heading1(ToolbarGroup.Heading, R.drawable.ic_text_h1, R.string.rich_text_h1),
    Heading2(ToolbarGroup.Heading, R.drawable.ic_text_h2, R.string.rich_text_h2),
    Heading3(ToolbarGroup.Heading, R.drawable.ic_text_h3, R.string.rich_text_h3),
    Bold(ToolbarGroup.Mark, R.drawable.ic_text_bold, R.string.rich_text_bold),
    Italic(ToolbarGroup.Mark, R.drawable.ic_text_italic, R.string.rich_text_italic),
    Underline(ToolbarGroup.Mark, R.drawable.ic_text_underline, R.string.rich_text_underline),
    Strikethrough(ToolbarGroup.Mark, R.drawable.ic_text_strikethrough, R.string.rich_text_strikethrough),
    Highlight(ToolbarGroup.Mark, R.drawable.ic_text_highlight, R.string.rich_text_highlight),
    BulletList(ToolbarGroup.List, R.drawable.ic_text_bulletlist, R.string.rich_text_bulletlist),
    NumberList(ToolbarGroup.List, R.drawable.ic_text_numberlist, R.string.rich_text_numberlist),
    AlignLeft(ToolbarGroup.Alignment, R.drawable.ic_text_leftalign, R.string.rich_text_leftalign),
    AlignCenter(ToolbarGroup.Alignment, R.drawable.ic_text_centeralign, R.string.rich_text_centeralign),
    AlignRight(ToolbarGroup.Alignment, R.drawable.ic_text_rightalign, R.string.rich_text_rightalign),
    Justify(ToolbarGroup.Alignment, R.drawable.ic_text_justify, R.string.rich_text_justify),
    Indent(ToolbarGroup.Other, R.drawable.ic_text_indent, R.string.rich_text_indent),
    Link(ToolbarGroup.Other, R.drawable.ic_text_link, R.string.rich_text_link);

    companion object {
        /** 처음에는 실행 취소·다시 실행할 이력이 없습니다. */
        val defaultButtons = entries.associateWith { action ->
            if (action.group == ToolbarGroup.History) SDGRichTextToolbarButtonState.Disabled
            else SDGRichTextToolbarButtonState.Default
        }.toPersistentMap()
    }
}

internal enum class ToolbarGroup { History, Heading, Mark, List, Alignment, Other }
