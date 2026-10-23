package com.shopl.sdg.component.empty_icon

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.shopl.sdg_common.foundation.spacing.SDGSpacing

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
