package com.shopl.sdg.component.button.box

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

sealed interface SDGBoxButtonShowIcon {
    data class True(
        @param:DrawableRes val iconResId: Int,
        val iconColor: Color?,
        val iconSize: SDGBoxButtonIconSize,
    ) : SDGBoxButtonShowIcon

    data object False : SDGBoxButtonShowIcon
}

/**
 * Deprecated SDGBoxButton 래퍼 전용으로, 기존 nullable 아이콘 리소스를 표시 모델로 변환합니다.
 * 신규 코드에서는 이 함수를 사용하지 않고 [SDGBoxButtonShowIcon.True] 또는 [SDGBoxButtonShowIcon.False]를 직접 지정합니다.
 *
 * @param iconColor 아이콘에 적용할 색상. null이면 리소스 색상을 유지합니다.
 * @param iconSize 버튼 크기와 무관한 아이콘 크기
 */
internal fun Int?.toBoxButtonShowIcon(
    iconColor: Color?,
    iconSize: SDGBoxButtonIconSize,
): SDGBoxButtonShowIcon = if (this != null) {
    SDGBoxButtonShowIcon.True(
        iconResId = this,
        iconColor = iconColor,
        iconSize = iconSize,
    )
} else {
    SDGBoxButtonShowIcon.False
}
