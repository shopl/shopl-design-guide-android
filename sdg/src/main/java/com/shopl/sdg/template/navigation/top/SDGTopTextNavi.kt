package com.shopl.sdg.template.navigation.top

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.navigation.text.SDGTextNavi
import com.shopl.sdg.component.navigation.text.SDGTextNaviBackButton
import com.shopl.sdg.component.navigation.text.SDGTextNaviLabel
import com.shopl.sdg.component.navigation.text.SDGTextNaviSubItem
import com.shopl.sdg.template.navigation.SDGTopNaviMenu
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_resource.R

/**
 * SDG - Top Navigation - Top Text Navi
 *
 * 타이틀과 특정한 동작 또는 정보를 전달하는 텍스트 조합
 *
 * 뒤로가기 아이콘의 리소스·색상, 메뉴 아이콘 크기와 클릭 제한은 신규 SDGTextNavi 사양을 따릅니다.
 * menu가 null이면 빈 보조 라벨을 전달하며, 보조 영역의 간격은 유지됩니다.
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=6900-15491&t=dgvDUBUeWoMF9Oo1-4">Figma</a>
 * @see com.shopl.sdg.component.navigation.text.SDGTextNavi
 */
@Deprecated(message = "제거 예정. SDGTextNavi를 사용하세요.")
@Composable
fun SDGTopTextNavi(
    title: String = "",
    backgroundColor: Color = SDGColor.Neutral100,
    menu: SDGTopNaviMenu.TextMenu? = null,
    backBtn: SDGTopNaviMenu.CommonMenu? = null,
) {
    SDGTextNavi(
        label = SDGTextNaviLabel.True(text = title, textColor = SDGColor.Neutral900),
        backgroundColor = backgroundColor,
        backButton = if (backBtn != null) {
            SDGTextNaviBackButton.True(onClick = backBtn.onMenuClick)
        } else {
            SDGTextNaviBackButton.False
        },
        subIcon = menu?.let { item ->
            item.iconResId?.let { iconResId ->
                SDGTextNaviSubItem.Icon(
                    iconResId = iconResId,
                    iconColor = item.iconTint,
                    onClick = item.onMenuClick,
                    showDot = false,
                )
            }
        },
        subLabel = SDGTextNaviSubItem.Label(
            text = menu?.textMenuTitle.orEmpty(),
            onClick = { menu?.onMenuClick?.invoke() },
        ),
    )
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

