package com.shopl.sdg.component.navigation.basic

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.ui.components.SDGImage

/**
 * SDG - Navigation - Basic Navi
 *
 * 화면의 상단에 위치하며, 타이틀과 아이콘으로 조합된 템플릿
 *
 * @param
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19779-9926&t=0IkMpxxx2xWJ4zUm-4">Figma</a>
 */
@Composable
fun SDGBasicNavi(
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {

    }
}

@Composable
private fun SDGBasicNaviIcon(
    icon: SDGBasicNaviIconItem,
    modifier: Modifier = Modifier,
) {
    with(icon) {
        SDGImage(
            resId = resId,
            color = color,
            modifier = modifier
                .size(40.dp)
                .clickable(onClick = onClick)
        )
    }
}