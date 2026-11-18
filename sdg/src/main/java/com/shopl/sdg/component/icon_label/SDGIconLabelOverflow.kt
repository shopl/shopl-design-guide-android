package com.shopl.sdg.component.icon_label

import androidx.compose.ui.text.style.TextOverflow

/**
 * Icon Label의 Label 텍스트 노출 방식(Text Mode)을 정의합니다.
 *
 * 좌우 아이콘을 모두 노출하면 지정한 값과 관계없이 [SingleLineEllipsis]가 적용됩니다.
 */
enum class SDGIconLabelOverflow(
    internal val maxLines: Int,
    internal val textOverflow: TextOverflow,
) {
    /**
     * Wrap(기본값): Label 텍스트를 여러 줄로 줄바꿈하여 전체 노출합니다.
     */
    Full(
        maxLines = Int.MAX_VALUE,
        textOverflow = TextOverflow.Clip,
    ),
    /**
     * Truncate: Label 텍스트를 한 줄로 제한하고 끝 말줄임표를 표시합니다.
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

/**
 * 아이콘 노출 조합에 따라 실제 적용할 Label 노출 방식을 계산합니다.
 *
 * Usage Guidelines: 아이콘을 둘 다 노출할 경우 Text Mode는 Truncate 타입으로 사용합니다.
 *
 * @param hasLeftIc 좌측 아이콘 노출 여부
 * @param hasRightIc 우측 아이콘 노출 여부
 */
internal fun SDGIconLabelOverflow.resolve(
    hasLeftIc: Boolean,
    hasRightIc: Boolean,
): SDGIconLabelOverflow =
    if (hasLeftIc && hasRightIc) {
        SDGIconLabelOverflow.SingleLineEllipsis
    } else {
        this
    }
