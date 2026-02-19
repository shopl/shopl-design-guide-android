package com.shopl.sdg.template.popup.mini_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing12
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 * SDG - Popup - Mini List Popup [2.0.0]
 *
 * 앱 콘텐츠 앞 특정 위치에 표시되며, 단순 동작 선택을 위한 콘텐츠를 포함하는 모달형 템플릿
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19220-4063&m=dev">Figma</a>
 */
@Composable
fun SDGMiniListPopup(
    items: ImmutableList<SDGMiniListPopupBodyItemText>,
    onClick: (itemText: SDGMiniListPopupBodyItemText) -> Unit
) {
    Column(
        modifier = Modifier.width(200.dp)
    ) {
        items.forEachIndexed { index, item ->
            SDGMiniListPopupBodyItem(
                itemText = item,
                onClick = onClick,
                modifier = Modifier.fillMaxWidth()
            )

            if (index != items.lastIndex) {
                HorizontalDivider(color = SDGColor.Neutral200)
            }
        }
    }
}

@Composable
private fun SDGMiniListPopupBodyItem(
    itemText: SDGMiniListPopupBodyItemText,
    onClick: (itemText: SDGMiniListPopupBodyItemText) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.height(48.dp)) {
        SDGText(
            text = itemText.title,
            textColor = itemText.textColor,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            typography = SDGTypography.Body1R,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick(itemText) }
                .padding(horizontal = Spacing12)
                .align(Alignment.Center),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGMiniListPopup() {
    SDGMiniListPopup(
        items = persistentListOf(
            SDGMiniListPopupBodyItemText.Default("수정"),
            SDGMiniListPopupBodyItemText.Default("복사"),
            SDGMiniListPopupBodyItemText.Delete("삭제"),
        ),
        onClick = {}
    )
}