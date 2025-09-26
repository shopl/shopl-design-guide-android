package com.shopl.sdg.component.empty_icon

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

/**
 * Component - Empty Icon - Basic Empty Icon
 *
 * 일반적인 빈 화면 이미지로 Body 영역에 여러가지 요소가 있거나 강조되지 않는 경우 사용하는 Empty 컴포넌트
 *
 * @param iconResId [Int] 이미지 리소스 ID
 * @param description [String] 이미지 설명 (Drawable ResourceId 전달 가능)
 * @param marginTop [Dp] 상단 마진 (기본 값 : 60.dp)
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=8638-35877&m=dev">Figma</a>
 */
@Composable
fun SDGBasicEmpty(
    @DrawableRes
    iconResId: Int,
    description: String,
    marginTop: Dp = SDGSpacing.Spacing60
) {
    Column(
        modifier = Modifier
            .padding(top = marginTop)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = spacedBy(SDGSpacing.Spacing12)
    ) {
        SDGImage(
            resId = iconResId,
            color = null
        )
        SDGText(
            text = description,
            textColor = SDGColor.Neutral300,
            typography = SDGTypography.Body1R,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SDGBasicEmpty(
    @DrawableRes
    iconResId: Int,
    @StringRes
    descResId: Int,
    marginTop: Dp = SDGSpacing.Spacing60
) {
    SDGBasicEmpty(
        iconResId = iconResId,
        description = stringResource(descResId),
        marginTop = marginTop
    )
}

@Preview
@Composable
private fun PreviewSDGBasicEmpty() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(SDGColor.Neutral0)
    ) {
        SDGBasicEmpty(
            iconResId = R.drawable.empty_member,
            descResId = R.string.no_registered_members
        )
    }
}