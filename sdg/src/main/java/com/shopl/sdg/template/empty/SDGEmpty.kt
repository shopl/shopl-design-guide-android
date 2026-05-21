package com.shopl.sdg.template.empty

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.button.capsule.SDGCapsuleButton
import com.shopl.sdg.component.button.capsule.SDGCapsuleButtonSize
import com.shopl.sdg.component.button.capsule.SDGCapsuleButtonType
import com.shopl.sdg.template.empty.preview.SDGEmptyPreviewParameter
import com.shopl.sdg.template.empty.preview.SDGEmptyPreviewParameterProvider
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

private val EMPTY_ILLUSTRATION_SIZE = 140.dp

/**
 * SDG - Template - Empty
 *
 * 표시할 컨텐츠가 없는 최초 상태에서 안내 일러스트, 텍스트, 선택 액션을 조합해 보여주는 템플릿
 *
 * @param illustrationResId 원형 배경 위에 표시할 Empty 일러스트 이미지 리소스 ID
 * @param illustrationBackgroundColor 일러스트 뒤에 깔리는 원형 배경 색상
 * @param title Empty 상태를 안내하는 타이틀. null 또는 blank이면 표시하지 않습니다.
 * @param bodyText 타이틀 아래에 표시할 보조 안내 문구. null 또는 blank이면 표시하지 않습니다.
 * @param bodyTextColor 보조 안내 문구 색상
 * @param buttonLabel 하단 Capsule Button 라벨. null 또는 blank이면 버튼을 표시하지 않습니다.
 * @param onClickButton 하단 Capsule Button 클릭 콜백
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=8649-38588&m=dev">Figma</a>
 */
@Composable
fun SDGEmpty(
    @DrawableRes illustrationResId: Int,
    illustrationBackgroundColor: SDGEmptyIllustrationBackgroundColor = SDGEmptyIllustrationBackgroundColor.PRIMARY_300_A10,
    title: String? = null,
    bodyText: String? = null,
    bodyTextColor: SDGEmptyBodyTextColor = SDGEmptyBodyTextColor.NEUTRAL_700,
    buttonLabel: String? = null,
    onClickButton: () -> Unit = {},
) {
    val hasText = !title.isNullOrBlank() || !bodyText.isNullOrBlank()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = SDGSpacing.Spacing20),
    ) {
        SDGImage(
            modifier = Modifier
                .size(size = EMPTY_ILLUSTRATION_SIZE)
                .background(
                    color = illustrationBackgroundColor.color,
                    shape = CircleShape,
                )
                .clip(shape = CircleShape),
            resId = illustrationResId,
            color = null,
        )

        if (hasText || !buttonLabel.isNullOrBlank()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(space = SDGSpacing.Spacing60),
            ) {
                if (hasText) {
                    EmptyText(
                        title = title,
                        bodyText = bodyText,
                        titleTextColor = SDGColor.Neutral700,
                        bodyTextColor = bodyTextColor.color,
                    )
                }

                if (!buttonLabel.isNullOrBlank()) {
                    SDGCapsuleButton(
                        size = SDGCapsuleButtonSize.Medium,
                        type = SDGCapsuleButtonType.Line(lineColor = SDGColor.Neutral600),
                        label = buttonLabel,
                        labelColor = SDGColor.Neutral600,
                        backgroundColor = SDGColor.Neutral0,
                        onClick = onClickButton,
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyText(
    title: String?,
    bodyText: String?,
    titleTextColor: Color,
    bodyTextColor: Color,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(space = SDGSpacing.Spacing10),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (!title.isNullOrBlank()) {
            SDGText(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                typography = SDGTypography.Title2SB,
                textColor = titleTextColor,
                textAlign = TextAlign.Center,
            )
        }

        if (!bodyText.isNullOrBlank()) {
            SDGText(
                modifier = Modifier.fillMaxWidth(),
                text = bodyText,
                typography = SDGTypography.Body1R,
                textColor = bodyTextColor,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGEmpty(
    @PreviewParameter(provider = SDGEmptyPreviewParameterProvider::class)
    parameter: SDGEmptyPreviewParameter
) {
    Box(modifier = Modifier.fillMaxSize()) {
        SDGEmpty(
            illustrationResId = R.drawable.empty_detail_payment,
            illustrationBackgroundColor = parameter.illustrationBackgroundColor,
            title = parameter.title,
            bodyText = parameter.bodyText,
            bodyTextColor = parameter.bodyTextColor,
            buttonLabel = parameter.buttonLabel,
            onClickButton = {},
        )
    }
}
