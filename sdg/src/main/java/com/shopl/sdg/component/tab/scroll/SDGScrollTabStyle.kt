package com.shopl.sdg.component.tab.scroll

/** Scroll Tab의 라벨 하단에 인디케이터와 구분선을 표시할지 지정합니다. */
enum class SDGScrollTabStyle(internal val showUnderline: Boolean) {
    WithUnderline(showUnderline = true),
    OnlyText(showUnderline = false),
}
