package com.shopl.sdg.component.button.box

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.button.box.preview.SDGBoxButtonPreviewParameter
import com.shopl.sdg.component.button.box.preview.SDGBoxButtonPreviewParameterProvider
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

private val SDGBoxButtonBorderWidth = 1.dp
private const val SDGBoxButtonDisabledAlpha = 0.3f
private const val SDGBoxButtonDebounceTimeMills = 500L

/**
 * SDG - Button - Box Button
 *
 * 화면 내에 배치하여 사용하는 일반적인 사각 형태의 버튼 컴포넌트
 *
 * @version 2.3.34
 *
 * @param leftIc 왼쪽 아이콘 표시 여부와 리소스·색상·크기. 버튼 크기와 무관하게 지정합니다.
 * @param rightIc 오른쪽 아이콘 표시 여부와 리소스·색상·크기. 버튼 크기와 무관하게 지정합니다.
 * @param enable 활성 여부. 비활성 상태는 30% 불투명도를 적용하며 Neutral0 라벨은 유지합니다.
 * @param debounceTimeMills 중복 클릭 방지 시간
 * @param maxWidth 외부 여백을 제외한 버튼의 최대 너비. 지정하면 아이콘 공간을 확보하고 남은 너비에서 라벨을 말줄임합니다.
 * null이면 기존 너비 동작을 유지하며, 부모의 최소 너비 제약은 우선합니다.
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=7102-15096&m=dev">Figma</a>
 */
@Composable
fun SDGBoxButton(
    size: SDGBoxButtonSize,
    type: SDGBoxButtonType,
    label: String,
    labelColor: Color,
    backgroundColor: Color,
    onClick: () -> Unit,
    leftIc: SDGBoxButtonShowIcon,
    rightIc: SDGBoxButtonShowIcon,
    enable: Boolean = true,
    isFillMaxWidth: Boolean = false,
    marginValues: PaddingValues = PaddingValues(),
    debounceTimeMills: Long = SDGBoxButtonDebounceTimeMills,
    maxWidth: Dp? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState()
    val scope = rememberCoroutineScope()
    var isClickable by remember { mutableStateOf(true) }
    val preserveWhiteLabel = !enable && labelColor == SDGColor.Neutral0
    var labelLayout by remember { mutableStateOf<TextLayoutResult?>(null) }
    var labelPosition by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = Modifier
            .padding(marginValues)
            .then(if (maxWidth != null) Modifier.widthIn(max = maxWidth) else Modifier)
            .then(
                if (isFillMaxWidth) {
                    Modifier.fillMaxWidth()
                } else {
                    Modifier.width(IntrinsicSize.Max)
                }
            )
    ) {
        Row(
            modifier = Modifier
                .then(if (isFillMaxWidth) Modifier.fillMaxWidth() else Modifier)
                .height(size.height)
                .drawWithContent {
                    drawContent()
                    if (preserveWhiteLabel) {
                        labelLayout?.let { layout ->
                            drawText(
                                textLayoutResult = layout,
                                topLeft = labelPosition + Offset(size.horizontalSpacing.toPx(), 0f),
                            )
                        }
                    }
                }
                .alpha(if (enable) 1f else SDGBoxButtonDisabledAlpha)
                .background(
                    color = backgroundColor,
                    shape = size.shape
                )
                .then(
                    if (type is SDGBoxButtonType.Line) {
                        Modifier.border(
                            width = SDGBoxButtonBorderWidth,
                            color = type.lineColor,
                            shape = size.shape
                        )
                    } else {
                        Modifier
                    }
                )
                .clip(size.shape)
                .drawWithContent {
                    drawContent()
                    if (enable && isPressed.value) {
                        drawRect(SDGColor.Neutral900_a10)
                    }
                }
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enable,
                    onClick = {
                        if (isClickable) {
                            onClick()
                            isClickable = false
                            scope.launch {
                                delay(debounceTimeMills.milliseconds)
                                isClickable = true
                            }
                        }
                    },
                )
                .padding(horizontal = size.horizontalSpacing),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (leftIc is SDGBoxButtonShowIcon.True) {
                SDGBoxButtonIcon(icon = leftIc)
                Spacer(modifier = Modifier.width(size.iconGap))
            }

            SDGText(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .onPlaced { labelPosition = it.positionInParent() }
                    .drawWithContent {
                        if (!preserveWhiteLabel) drawContent()
                    },
                text = label,
                textColor = labelColor,
                textAlign = TextAlign.Center,
                typography = size.typography,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = { labelLayout = it },
            )

            if (rightIc is SDGBoxButtonShowIcon.True) {
                Spacer(modifier = Modifier.width(size.iconGap))
                SDGBoxButtonIcon(icon = rightIc)
            }
        }
    }
}

@Deprecated("leftIc/rightIc에 SDGBoxButtonShowIcon과 SDGBoxButtonIconSize를 지정하는 API를 사용하세요.")
@Composable
fun SDGBoxButton(
    size: SDGBoxButtonSize,
    type: SDGBoxButtonType,
    label: String,
    labelColor: Color,
    backgroundColor: Color,
    onClick: () -> Unit,
    iconDownSize: Boolean = false,
    enable: Boolean = true,
    isFillMaxWidth: Boolean = false,
    @DrawableRes leftIcon: Int? = null,
    leftIconTint: Color? = null,
    @DrawableRes rightIcon: Int? = null,
    rightIconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
    debounceTimeMills: Long = 500,
    maxWidth: Dp? = null,
) {
    val iconSize = size.toLegacyIconSize(iconDownSize)

    SDGBoxButton(
        size = size,
        type = type,
        label = label,
        labelColor = labelColor,
        backgroundColor = backgroundColor,
        onClick = onClick,
        leftIc = leftIcon.toBoxButtonShowIcon(leftIconTint, iconSize),
        rightIc = rightIcon.toBoxButtonShowIcon(rightIconTint, iconSize),
        enable = enable,
        isFillMaxWidth = isFillMaxWidth,
        marginValues = marginValues,
        debounceTimeMills = debounceTimeMills,
        maxWidth = maxWidth,
    )
}

@Composable
private fun SDGBoxButtonIcon(
    icon: SDGBoxButtonShowIcon.True,
) {
    SDGImage(
        modifier = Modifier.size(icon.iconSize.size),
        resId = icon.iconResId,
        color = icon.iconColor,
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGBoxButton(
    @PreviewParameter(SDGBoxButtonPreviewParameterProvider::class)
    parameter: SDGBoxButtonPreviewParameter
) {
    with(parameter) {
        SDGBoxButton(
            size = size,
            type = type,
            label = label,
            labelColor = labelColor,
            backgroundColor = backgroundColor,
            onClick = {},
            leftIc = leftIc,
            rightIc = rightIc,
            enable = enable,
            isFillMaxWidth = isFillMaxWidth,
            marginValues = marginValues,
            maxWidth = maxWidth,
        )
    }
}
