package com.shopl.sdg.template.navigation.top

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.template.navigation.SDGTopNaviMenu
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

/**
 * SDG - Top Navigation - Top Text Navi
 *
 * 타이틀과 특정한 동작 또는 정보를 전달하는 텍스트 조합
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=6900-15491&t=dgvDUBUeWoMF9Oo1-4">Figma</a>
 */
@Composable
fun SDGTopTextNavi(
    title: String = "",
    backgroundColor: Color = SDGColor.Neutral100,
    menu: SDGTopNaviMenu.TextMenu? = null,
    backBtn: SDGTopNaviMenu.CommonMenu? = null,
) {
    Row(
        modifier = Modifier
            .background(backgroundColor)
            .height(48.dp)
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (backBtn != null) {
            SDGImage(
                modifier = Modifier
                    .size(40.dp)
                    .clickable(false, debounceTimeMills = 1000) { backBtn.onMenuClick.invoke() },
                resId = backBtn.iconResId,
                color = backBtn.iconTint,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.width(if (backBtn != null) 2.dp else 6.dp))
        Row(
            modifier = Modifier
                .weight(1F)
                .animateContentSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SDGText(
                modifier = Modifier.weight(1f, false),
                text = title,
                textColor = SDGColor.Neutral900,
                typography = SDGTypography.NaviTitle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Spacer(modifier = Modifier.width(if (menu != null) 10.dp else 6.dp))
        if (menu != null) {
            Row(
                modifier = Modifier.clickable(false) { menu.onMenuClick() },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (menu.iconResId != null) {
                    SDGImage(
                        modifier = Modifier
                            .size(20.dp),
                        resId = menu.iconResId,
                        color = menu.iconTint,
                    )
                }
                SDGText(
                    text = menu.textMenuTitle,
                    textColor = SDGColor.Neutral700,
                    typography = SDGTypography.Body1R,
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
        }
    }
}

@Preview
@Composable
fun PreviewSDGTopTextNavi() {
    SDGTopTextNavi(
        title = "타이틀",
        menu = SDGTopNaviMenu.TextMenu(
            textMenuTitle = "메뉴",
            onMenuClick = {}
        ),
        backBtn = SDGTopNaviMenu.CommonMenu(
            iconResId = R.drawable.ic_navi_back_android,
            onMenuClick = {}
        )
    )
}

