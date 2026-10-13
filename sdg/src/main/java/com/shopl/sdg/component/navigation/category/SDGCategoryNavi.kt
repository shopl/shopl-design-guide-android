package com.shopl.sdg.component.navigation.category

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.ext.orDefault
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing10
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing2
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing4
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing8
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R
import kotlinx.collections.immutable.PersistentList

private const val MAX_RIGHT_ICON_COUNT = 3
private const val LABEL_MAX_LINES = 1
private const val PREVIEW_WIDTH_DP = 375

private val NAVI_MIN_HEIGHT = 48.dp
private val NAVI_ICON_SIZE = 40.dp
private val DROPDOWN_ICON_SIZE = 20.dp
private val DOT_BADGE_SIZE = 8.dp

/**
 * SDG - Navigation - Category Navi
 *
 * 화면의 상단에 위치하여 현재 위치를 알려주는 유형 변경이 가능한 타이틀과 고 사용자가 필요한 것을 찾도록 도와주는 아이콘으로 조합한 컴포넌트
 *
 * @param label 표시할 텍스트와 색상. 텍스트가 null이면 드롭다운만 표시할 수 있습니다.
 * @param leftIcon 40×40dp의 ic_navi_back_android 아이콘. null이면 표시하지 않습니다.
 * @param hasDropdown 드롭다운 표시 여부와 선택 시 List Popup을 표시하는 콜백
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19797-22031&m=dev">Figma</a>
 */
@Composable
fun SDGCategoryNavi(
    label: SDGCategoryNaviLabel,
    leftIcon: SDGCategoryNaviLeftIconItem?,
    rightIcons: PersistentList<SDGCategoryNaviIconItem>?,
    backgroundColor: Color,
    hasDropdown: SDGCategoryNaviHasDropdown,
    marginValues: PaddingValues = PaddingValues(),
) {
    require((rightIcons?.size.orDefault()) <= MAX_RIGHT_ICON_COUNT) {
        "우측 아이콘은 최대 ${MAX_RIGHT_ICON_COUNT}개까지만 사용 가능합니다."
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(marginValues)
            .heightIn(min = NAVI_MIN_HEIGHT)
            .background(backgroundColor)
            .padding(horizontal = Spacing10, vertical = Spacing4),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        leftIcon?.let {
            SDGImage(
                resId = R.drawable.ic_navi_back_android,
                color = SDGColor.Neutral700,
                contentDescription = it.contentDescription,
                modifier = Modifier
                    .size(NAVI_ICON_SIZE)
                    .clickable(role = Role.Button, onClick = it.onClick),
            )
        }

        Row(
            modifier = Modifier
                .weight(1f, true)
                .padding(start = Spacing2),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            label.text?.let {
                SDGText(
                    text = it,
                    typography = SDGTypography.NaviTitle,
                    textColor = label.color,
                    maxLines = LABEL_MAX_LINES,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = false,
                    modifier = Modifier.weight(1f, false),
                )
            }

            if (hasDropdown is SDGCategoryNaviHasDropdown.True) {
                SDGImage(
                    resId = R.drawable.ic_common_dropdown,
                    color = label.color,
                    modifier = Modifier
                        .size(DROPDOWN_ICON_SIZE)
                        .clickable(
                            role = Role.Button,
                            onClickLabel = stringResource(R.string.select),
                            onClick = hasDropdown.onClick,
                        ),
                )
            }
        }

        if (!rightIcons.isNullOrEmpty()) {
            Spacer(Modifier.width(Spacing8))

            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing2, Alignment.End),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                rightIcons.forEach { SDGCategoryNaviIcon(icon = it) }
            }
        }
    }
}

@Composable
private fun SDGCategoryNaviIcon(icon: SDGCategoryNaviIconItem) {
    Box {
        SDGImage(
            resId = icon.iconResId,
            color = icon.iconColor,
            contentDescription = icon.contentDescription,
            modifier = Modifier
                .size(NAVI_ICON_SIZE)
                .clickable(role = Role.Button, onClick = icon.onClick),
        )

        if (icon.showDot) {
            Canvas(
                modifier = Modifier
                    .padding(Spacing8)
                    .size(DOT_BADGE_SIZE)
                    .align(Alignment.TopEnd),
            ) {
                drawCircle(color = SDGColor.Red350)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = PREVIEW_WIDTH_DP)
@Composable
private fun PreviewSDGCategoryNavi(
    @PreviewParameter(SDGCategoryNaviPreviewParameterProvider::class)
    parameter: SDGCategoryNaviPreviewParameter,
) {
    with(parameter) {
        SDGCategoryNavi(
            label = label,
            leftIcon = leftIcon,
            rightIcons = rightIcons,
            backgroundColor = backgroundColor,
            hasDropdown = hasDropdown,
        )
    }
}
