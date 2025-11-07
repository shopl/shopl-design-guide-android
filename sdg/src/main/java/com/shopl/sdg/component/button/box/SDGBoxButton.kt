package com.shopl.sdg.component.button.box

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

/**
 * SDG - Button - Box Button
 *
 * 화면의 내용 영역에 배치하여 사용하는 일반적인 사각 형태의 버튼 컴포넌트
 *
 * @param debounceTimeMills 중복 클릭 방지 시간
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=7102-15102&m=dev">Figma</a>
 */
@Composable
fun SDGBoxButton(
    size: SDGBoxButtonSize,
    type: SDGBoxButtonType,
    label: String,
    labelColor: Color,
    backgroundColor: Color,
    onClick: () -> Unit,
    enable: Boolean = true,
    isFillMaxWidth: Boolean = false,
    @DrawableRes leftIcon: Int? = null,
    leftIconTint: Color? = null,
    @DrawableRes rightIcon: Int? = null,
    rightIconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
    debounceTimeMills: Long = 500,
) {
    Box(
        modifier = if (isFillMaxWidth) {
            Modifier
                .padding(marginValues)
                .fillMaxWidth()
        } else {
            Modifier
                .padding(marginValues)
                .width(IntrinsicSize.Max)
        }
    ) {
        Row(
            modifier = Modifier
                .then(
                    if (isFillMaxWidth) {
                        Modifier.fillMaxWidth()
                    } else {
                        Modifier
                    }
                )
                .height(size.height)
                .background(
                    color = if (enable) backgroundColor else backgroundColor.copy(alpha = 0.3f),
                    shape = size.shape
                )
                .then(
                    if (type is SDGBoxButtonType.Line) {
                        Modifier.border(
                            width = 1.dp,
                            color = if (enable) type.lineColor else type.lineColor.copy(alpha = 0.3f),
                            shape = size.shape
                        )
                    } else {
                        Modifier
                    }
                )
                .clip(size.shape)
                .then(
                    if (enable) {
                        Modifier.clickable(
                            hasRipple = true,
                            rippleColor = SDGColor.Neutral350,
                            debounceTimeMills = debounceTimeMills,
                            onClick = onClick,
                        )
                    } else {
                        Modifier
                    }
                )
                .padding(horizontal = size.horizontalSpacing),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (leftIcon != null && leftIconTint != null) {
                Image(
                    modifier = Modifier.size(14.dp),
                    painter = painterResource(id = leftIcon),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(
                        color = if (enable) leftIconTint else leftIconTint.copy(alpha = 0.3f)
                    )
                )
                Spacer(modifier = Modifier.width(size.iconGap))
            }
            SDGText(
                text = label,
                textColor = if (enable) labelColor else labelColor.copy(alpha = 0.3f),
                textAlign = TextAlign.Center,
                typography = size.typography
            )
            if (rightIcon != null && rightIconTint != null) {
                Spacer(modifier = Modifier.width(size.iconGap))
                Image(
                    painter = painterResource(id = rightIcon),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(
                        color = if (enable) rightIconTint else rightIconTint.copy(alpha = 0.3f)
                    )
                )
            }
        }
    }
}

@Composable
fun RowScope.SDGBoxButton(
    weight: Float,
    enable: Boolean = true,
    size: SDGBoxButtonSize,
    type: SDGBoxButtonType,
    label: String,
    labelColor: Color,
    backgroundColor: Color,
    @DrawableRes leftIcon: Int? = null,
    leftIconTint: Color? = null,
    @DrawableRes rightIcon: Int? = null,
    rightIconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
    onClick: () -> Unit,
) {
    Box(Modifier.weight(weight)) {
        SDGBoxButton(
            isFillMaxWidth = true,
            enable = enable,
            size = size,
            type = type,
            label = label,
            labelColor = labelColor,
            backgroundColor = backgroundColor,
            leftIcon = leftIcon,
            leftIconTint = leftIconTint,
            rightIcon = rightIcon,
            rightIconTint = rightIconTint,
            marginValues = marginValues,
            onClick = onClick,
        )
    }
}

@Composable
fun BoxScope.SDGBoxButton(
    alignment: Alignment,
    isFillMaxWidth: Boolean,
    enable: Boolean = true,
    size: SDGBoxButtonSize,
    type: SDGBoxButtonType,
    label: String,
    labelColor: Color,
    backgroundColor: Color,
    @DrawableRes leftIcon: Int? = null,
    leftIconTint: Color? = null,
    @DrawableRes rightIcon: Int? = null,
    rightIconTint: Color? = null,
    marginValues: PaddingValues = PaddingValues(),
    onClick: () -> Unit,
) {
    Box(Modifier.align(alignment)) {
        SDGBoxButton(
            isFillMaxWidth = isFillMaxWidth,
            enable = enable,
            size = size,
            type = type,
            label = label,
            labelColor = labelColor,
            backgroundColor = backgroundColor,
            leftIcon = leftIcon,
            leftIconTint = leftIconTint,
            rightIcon = rightIcon,
            rightIconTint = rightIconTint,
            marginValues = marginValues,
            onClick = onClick,
        )
    }
}

@Composable
@Preview
private fun PrevTest() {
    Surface(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SDGBoxButton(
                size = SDGBoxButtonSize.Medium,
                type = SDGBoxButtonType.Solid,
                enable = false,
                leftIcon = R.drawable.ic_common_edit,
                leftIconTint = SDGColor.Neutral0,
                label = "작성하기",
                labelColor = SDGColor.Neutral0,
                backgroundColor = SDGColor.Neutral600,
                onClick = {}
            )

            SDGBoxButton(
                size = SDGBoxButtonSize.Medium,
                type = SDGBoxButtonType.Line(SDGColor.Neutral700),
                leftIcon = R.drawable.ic_common_edit,
                leftIconTint = SDGColor.Neutral700,
                label = "작성하기",
                labelColor = SDGColor.Neutral700,
                backgroundColor = SDGColor.Neutral0,
                onClick = {}
            )

            SDGBoxButton(
                isFillMaxWidth = true,
                size = SDGBoxButtonSize.Medium,
                type = SDGBoxButtonType.Line(SDGColor.Neutral700),
                leftIcon = R.drawable.ic_common_edit,
                leftIconTint = SDGColor.Neutral700,
                label = "작성하기",
                labelColor = SDGColor.Neutral700,
                backgroundColor = SDGColor.Neutral0,
                onClick = {}
            )

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                SDGBoxButton(
                    weight = 1f,
                    size = SDGBoxButtonSize.Medium,
                    type = SDGBoxButtonType.Line(SDGColor.Neutral700),
                    leftIcon = R.drawable.ic_common_edit,
                    leftIconTint = SDGColor.Neutral700,
                    label = "작성하기",
                    labelColor = SDGColor.Neutral700,
                    backgroundColor = SDGColor.Neutral0
                ) {}

                SDGBoxButton(
                    weight = 1f,
                    enable = false,
                    size = SDGBoxButtonSize.Medium,
                    type = SDGBoxButtonType.Line(SDGColor.Neutral700),
                    leftIcon = R.drawable.ic_common_edit,
                    leftIconTint = SDGColor.Neutral700,
                    label = "작성하기",
                    labelColor = SDGColor.Neutral700,
                    backgroundColor = SDGColor.Neutral0
                ) {}

                SDGBoxButton(
                    weight = 1f,
                    size = SDGBoxButtonSize.Medium,
                    type = SDGBoxButtonType.Solid,
                    leftIcon = R.drawable.ic_common_edit,
                    leftIconTint = SDGColor.Neutral700,
                    label = "작성하기",
                    labelColor = SDGColor.Neutral700,
                    backgroundColor = SDGColor.Neutral200
                ) {}
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                SDGBoxButton(
                    weight = 1f,
                    size = SDGBoxButtonSize.Medium,
                    type = SDGBoxButtonType.Solid,
                    label = "작성하기",
                    labelColor = SDGColor.Neutral0,
                    backgroundColor = SDGColor.Neutral700
                ) {}

                SDGBoxButton(
                    weight = 1f,
                    size = SDGBoxButtonSize.Medium,
                    type = SDGBoxButtonType.Solid,
                    label = "작성하기",
                    labelColor = SDGColor.Neutral0,
                    backgroundColor = SDGColor.Primary300
                ) {}

                SDGBoxButton(
                    weight = 1f,
                    size = SDGBoxButtonSize.Medium,
                    type = SDGBoxButtonType.Solid,
                    label = "작성하기",
                    labelColor = SDGColor.Neutral600,
                    backgroundColor = SDGColor.Neutral200
                ) {}

            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                SDGBoxButton(
                    enable = false,
                    weight = 1f,
                    size = SDGBoxButtonSize.Medium,
                    type = SDGBoxButtonType.Solid,
                    label = "작성하기",
                    labelColor = SDGColor.Neutral0,
                    backgroundColor = SDGColor.Neutral700
                ) {}

                SDGBoxButton(
                    enable = false,
                    weight = 1f,
                    size = SDGBoxButtonSize.Medium,
                    type = SDGBoxButtonType.Solid,
                    label = "작성하기",
                    labelColor = SDGColor.Neutral0,
                    backgroundColor = SDGColor.Primary300
                ) {}

                SDGBoxButton(
                    enable = false,
                    weight = 1f,
                    size = SDGBoxButtonSize.Medium,
                    type = SDGBoxButtonType.Solid,
                    label = "작성하기",
                    labelColor = SDGColor.Neutral600,
                    backgroundColor = SDGColor.Neutral200
                ) {}

            }

            SDGBoxButton(
                enable = false,
                size = SDGBoxButtonSize.Medium,
                type = SDGBoxButtonType.Solid,
                label = "작성하기",
                labelColor = SDGColor.Neutral0,
                backgroundColor = SDGColor.Neutral700,
                onClick = {}
            )

            SDGBoxButton(
                enable = false,
                size = SDGBoxButtonSize.XSmall,
                type = SDGBoxButtonType.Solid,
                leftIcon = R.drawable.ic_common_mail,
                leftIconTint = SDGColor.Neutral700,
                label = "작성하기",
                labelColor = SDGColor.Neutral0,
                backgroundColor = SDGColor.Neutral700,
                onClick = {}
            )

            SDGBoxButton(
                enable = false,
                size = SDGBoxButtonSize.Small,
                type = SDGBoxButtonType.Line(SDGColor.Neutral250),
                label = "불러오기",
                labelColor = SDGColor.Neutral600,
                backgroundColor = SDGColor.Neutral0,
                onClick = {},
            )
        }
    }
}