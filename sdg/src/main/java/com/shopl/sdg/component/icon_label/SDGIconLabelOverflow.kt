package com.shopl.sdg.component.icon_label

import androidx.compose.ui.text.style.TextOverflow

/**
 * Icon Label의 Label 텍스트 노출 방식을 정의합니다.
 */
enum class SDGIconLabelOverflow(
    internal val maxLines: Int,
    internal val textOverflow: TextOverflow,
) {
    /**
     * Label 텍스트를 전체 노출합니다.
     */
    Full(
        maxLines = Int.MAX_VALUE,
        textOverflow = TextOverflow.Clip,
    ),
    /**
     * Label 텍스트를 한 줄로 제한하고 끝 말줄임표를 표시합니다.
     */
    SingleLineEllipsis(
        maxLines = 1,
        textOverflow = TextOverflow.Ellipsis,
    ),
    ;

    companion object {
        /**
         * 레거시 maxLines 값을 신규 Label 노출 방식으로 변환합니다.
         */
        internal fun fromMaxLines(maxLines: Int): SDGIconLabelOverflow =
            if (maxLines == 1) {
                SingleLineEllipsis
            } else {
                Full
            }
    }
}
