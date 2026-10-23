package com.shopl.sdg.component.empty_icon

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.shopl.sdg_common.foundation.spacing.SDGSpacing

/**
 * Component - Empty Icon - Basic Empty Icon
 *
 * 일반적인 빈 화면 이미지로 Body 영역에 여러가지 요소가 있거나 강조되지 않는 경우 사용하는 Empty 컴포넌트
 *
 * [SDGEmptyIcon]에 위임하며 아이콘은 76×76dp, Neutral300으로 표시하고 라벨과의 간격은 10dp로 적용합니다.
 *
 * @param iconResId [Int] 이미지 리소스 ID
 * @param description [String] 이미지 설명 (Drawable ResourceId 전달 가능)
 * @param marginTop [Dp] 상단 마진 (기본 값 : 60.dp)
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=8638-35877&m=dev">Figma</a>
 */
@Deprecated(
    message = "SDGEmptyIcon을 사용하세요.",
    replaceWith = ReplaceWith(
        expression = "SDGEmptyIcon(iconResId = iconResId, label = description, marginValues = PaddingValues(top = marginTop))",
        imports = [
            "com.shopl.sdg.component.empty_icon.SDGEmptyIcon",
            "androidx.compose.foundation.layout.PaddingValues",
        ],
    ),
)
@Composable
fun SDGBasicEmpty(
    @DrawableRes
    iconResId: Int,
    description: String,
    marginTop: Dp = SDGSpacing.Spacing60
) {
    SDGEmptyIcon(
        iconResId = iconResId,
        label = description,
        marginValues = PaddingValues(top = marginTop),
    )
}

/**
 * 문자열 리소스를 사용하는 [SDGBasicEmpty] 호환 API입니다.
 * 아이콘은 76×76dp, Neutral300으로 표시하고 라벨과의 간격은 10dp로 적용합니다.
 */
@Deprecated(
    message = "SDGEmptyIcon을 사용하세요.",
    replaceWith = ReplaceWith(
        expression = "SDGEmptyIcon(iconResId = iconResId, label = stringResource(descResId), marginValues = PaddingValues(top = marginTop))",
        imports = [
            "com.shopl.sdg.component.empty_icon.SDGEmptyIcon",
            "androidx.compose.ui.res.stringResource",
            "androidx.compose.foundation.layout.PaddingValues",
        ],
    ),
)
@Composable
fun SDGBasicEmpty(
    @DrawableRes
    iconResId: Int,
    @StringRes
    descResId: Int,
    marginTop: Dp = SDGSpacing.Spacing60
) {
    SDGEmptyIcon(
        iconResId = iconResId,
        label = stringResource(descResId),
        marginValues = PaddingValues(top = marginTop),
    )
}
