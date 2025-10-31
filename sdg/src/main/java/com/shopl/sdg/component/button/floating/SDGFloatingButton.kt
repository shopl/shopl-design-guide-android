package com.shopl.sdg.component.button.floating

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.ext.dropShadow
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_resource.R

/**
 * SDG - Button - Floating Button
 *
 * 화면 우측 하단에 고정으로 위치하며, 생성&추가 등의 동작을 위한 버튼 컴포넌트
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19481-3839&m=dev">Figma</a>
 */
@Composable
fun SDGFloatingButton(
    @DrawableRes icon: Int,
    onClick: () -> Unit,
    enabled: Boolean = true,
    marginValues: PaddingValues = PaddingValues(),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .dropShadow(
                offsetX = 3.dp,
                offsetY = 6.dp,
                shape = CircleShape,
                color = SDGColor.Neutral900_a10,
                blur = 10.dp,
            )
            .background(
                color = if (enabled) SDGColor.Primary300 else SDGColor.Primary50,
                shape = CircleShape,
            )
            .size(size = 60.dp)
            .clickable(onClick = onClick, enabled = enabled)
            .padding(paddingValues = marginValues),
    ) {
        SDGImage(
            resId = icon,
            color = SDGColor.Neutral0,
            modifier = Modifier.size(size = 40.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGFloatingButtonEnabled() {
    SDGFloatingButton(
        onClick = {},
        enabled = true,
        icon = R.drawable.ic_common_edit,
    )
}

@Preview
@Composable
private fun PreviewSDGFloatingButtonDisabled() {
    SDGFloatingButton(
        onClick = {},
        enabled = false,
        icon = R.drawable.ic_common_edit,
    )
}
