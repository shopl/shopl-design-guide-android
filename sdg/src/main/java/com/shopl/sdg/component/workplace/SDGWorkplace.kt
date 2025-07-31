package com.shopl.sdg.component.workplace

import androidx.annotation.StringRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R
import kotlin.math.roundToInt

/**
 * SDG - Workplace
 *
 * 근무지명, 근무지 코드, 주소, 거리를 표시하는 컴포넌트
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?m=auto&node-id=7304-16726">Figma</a>
 */

@Composable
fun SDGWorkplace(
    workplaceType: IOWorkplaceType,
    workplaceName: String,
    workplaceAddress: String,
    distance: Double,
    isShowDistance: Boolean,
    isWorkplaceNameMultiLine: Boolean = true,
    isWorkplaceAddressMultiLine: Boolean = true,
    workplaceCode: String? = null,
    workplaceAddressDetail: String? = null
) {
    val displayWorkplaceName = workplaceName.take(100) + if (!workplaceCode.isNullOrBlank()) {
        "(${workplaceCode.take(50)})"
    } else ""
    Column(
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing4)
    ) {
        if (IOWorkplaceType.BasicWorkplace != workplaceType) {
            WorkplaceLabel(workplaceType)
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing2)
        ) {
            SDGText(
                text = displayWorkplaceName,
                typography = SDGTypography.Body1R,
                textColor = SDGColor.Neutral700,
                maxLines = if (isWorkplaceNameMultiLine) Int.MAX_VALUE else 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SDGText(
                    modifier = Modifier.weight(6f),
                    typography = SDGTypography.Body3R,
                    text = "$workplaceAddress ${workplaceAddressDetail.orEmpty()}",
                    textColor = SDGColor.Neutral400,
                    maxLines = if (isWorkplaceAddressMultiLine) Int.MAX_VALUE else 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (isShowDistance) {
                    Spacer(Modifier.width(2.dp))
                    SDGText(
                        modifier = Modifier
                            .weight(0.8f)
                            .align(Alignment.Bottom),
                        typography = SDGTypography.Body3R,
                        text = distance.toDistanceLabel(),
                        textColor = SDGColor.Neutral400,
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}

@Composable
private fun WorkplaceLabel(workplaceType: IOWorkplaceType) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing4),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Canvas(
            modifier = Modifier.size(SDGSpacing.Spacing6)
        ) {
            drawCircle(color = SDGColor.Neutral700)
        }
        SDGText(
            typography = SDGTypography.Body3SB,
            text = workplaceType.labelRes?.let { stringResource(it) }.orEmpty(),
            textColor = SDGColor.Neutral700,
        )
    }
}

enum class IOWorkplaceType(@StringRes val labelRes: Int?) {
    FixWorkplace(R.string.place_my_routine_workplace),
    ManagingWorkplace(R.string.assigned_workplace),
    BasicWorkplace(null)
}

private fun Double.toDistanceLabel(): String {
    return if (this < 1000) {
        // 1km 미만은 미터 단위로 정수만 표시
        "${this.toInt()}m"
    } else {
        val km = this / 1000.0
        // 9999km 이상이면 무조건 "9999km"로 표기
        if (km >= 9999) {
            "9999km"
        } else {
            // km 값을 소수점 첫째 자리까지 반올림
            val kmRounded = (km * 10).roundToInt() / 10.0
            // 소수점 이하가 0이면 정수로, 아니면 소수점 한 자리까지 표기
            if (kmRounded % 1.0 == 0.0) {
                "${kmRounded.toInt()}km"
            } else {
                "${kmRounded}km"
            }
        }
    }
}

@Preview("근무지 이름만 줄바꿈")
@Composable
private fun PrevMultiLineWorkplaceInfo(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier
            .background(color = SDGColor.Neutral0)
            .padding(SDGSpacing.Spacing10)
    ) {
        SDGWorkplace(
            workplaceType = IOWorkplaceType.FixWorkplace,
            workplaceName = "근무지명이 엄청나게 길어진다근무지명이 엄청나게 길어진다근무지명이 엄청나게 길어진다근무지명이 엄청나게 길어진다근무지명이 엄청나게 길어진다근무지명이 엄청나게 길어진다",
            workplaceCode = "근무지 코드",
            workplaceAddress = "근무지 주소 101호근무지 주소 101호근무지 주소 101호근무지 주소 101호근무지 주소 101호근무지 주소 101호근무지 주소 101호근무지 주소 101호근무지 주소 101호근무지 주소 101호근무지 주소 101호근무지 주소 101호근무지 주소 101호근무지 주소 101호근무지 주소 101호근무지 주소 101호근무지 주소 101호",
            distance = 89.0,
            isShowDistance = true,
            isWorkplaceAddressMultiLine = false
        )
    }
}

@Preview("한 줄")
@Composable
private fun PrevSingleLineIOWorkplace(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier
            .background(color = SDGColor.Neutral0)
            .padding(SDGSpacing.Spacing10)
    ) {
        SDGWorkplace(
            workplaceType = IOWorkplaceType.ManagingWorkplace,
            workplaceName = "근무지명이 엄청나게 길어진다근무지명이 엄청나게 길어진다근무지명이 엄청나게 길어진다근무지명이 엄청나게 길어진다근무지명이 엄청나게 길어진다근무지명이 엄청나게 길어진다",
            workplaceCode = "",
            workplaceAddress = "근무지 주소가 엄청나게 길어진다근무지 주소가 엄청나게 길어진다근무지 주소가 엄청나게 길어진다근무지 주소가 엄청나게 길어진다근무지 주소가 엄청나게 길어진다근무지 주소가 엄청나게 길어진다",
            distance = 89.0,
            isShowDistance = true,
            isWorkplaceNameMultiLine = false,
        )
    }
}

@Preview("기본 근무지 - 근무지 코드 O")
@Composable
private fun PrevBasicIOWorkplace(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier
            .background(color = SDGColor.Neutral0)
            .padding(SDGSpacing.Spacing10)
    ) {
        SDGWorkplace(
            workplaceType = IOWorkplaceType.BasicWorkplace,
            workplaceName = "근무지명",
            workplaceCode = "근무지 코드",
            workplaceAddress = "근무지 주소",
            distance = 0.0,
            isShowDistance = true,
            isWorkplaceNameMultiLine = false
        )
    }
}

@Preview("기본 근무지 - 근무지 코드 X")
@Composable
private fun PrevBasicWorkplaceCodeIOWorkplace(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier
            .background(color = SDGColor.Neutral0)
            .padding(SDGSpacing.Spacing10)
    ) {
        SDGWorkplace(
            workplaceType = IOWorkplaceType.BasicWorkplace,
            workplaceName = "근무지명",
            workplaceCode = "",
            workplaceAddress = "근무지 주소",
            distance = 1819.0,
            isShowDistance = true,
            isWorkplaceNameMultiLine = false
        )
    }
}