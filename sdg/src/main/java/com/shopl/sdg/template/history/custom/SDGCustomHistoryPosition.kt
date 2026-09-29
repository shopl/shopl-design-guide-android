package com.shopl.sdg.template.history.custom

enum class SDGCustomHistoryPosition(
    val showTopLine: Boolean,
    val showBottomLine: Boolean,
) {
    FIRST(showTopLine = false, showBottomLine = false),
    TOP(showTopLine = false, showBottomLine = true),
    MIDDLE(showTopLine = true, showBottomLine = true),
    LAST(showTopLine = true, showBottomLine = false),
}
